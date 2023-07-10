package com.hanhwa.rsrvprogramh.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hanhwa.rsrvprogramh.config.exception.CloseException;
import com.hanhwa.rsrvprogramh.config.exception.FileNotReadException;
import com.hanhwa.rsrvprogramh.model.RsrvRequest;
import com.hanhwa.rsrvprogramh.model.RsrvResponse;
import com.hanhwa.rsrvprogramh.service.RsrvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.util.Collections;

@Service
public class RsrvServiceImpl implements RsrvService {
    @Autowired
    private final Gson gson;

    public RsrvServiceImpl(Gson gson) {
        this.gson = gson;
    }

    @Override
    public String getResponseFile() { // 예약 신청 응답 JSON 파일 읽기
        String fileName = "RsrvReqRs.json";
        URL resource = getClass().getClassLoader().getResource("static/file/" + fileName);
        String jsonFilePath = resource.getFile();

        FileReader fr = null;
        BufferedReader br = null;

        try {
            fr = new FileReader(jsonFilePath);
            br = new BufferedReader(fr);

            String jsonFileContentLine;
            // StringBuilder : 동기화 비지원 (싱글 스레드 환경 적합)
            // StringBuffer : 동기화 지원 (멀티 스레드 환경 적합)
            StringBuffer sb = new StringBuffer();

            while((jsonFileContentLine = br.readLine()) != null){ // 빈 문자열이 없도록 주의(NPE)
                sb.append(jsonFileContentLine);
            }
            return sb.toString();
            // 체크 예외
        } catch (IOException e) {
            // 파일을 읽지 못했을 때
            throw new FileNotReadException(e);
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    throw new CloseException("FileReader is not closed.", e);
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new CloseException("BufferedReader is not closed.", e);
                }
            }
        }
    }

    @Override
    public Object bindingObject(String jsonFileContent) { // JSON 전문 Object 바인딩
        if (jsonFileContent.contains("ds_rsrvInfo")) {
            // 요청
            System.out.println("call");
            RsrvRequest rsrvRequest = gson.fromJson(jsonFileContent, RsrvRequest.class);
            return rsrvRequest;
        } else if (jsonFileContent.contains("ds_prcsResult")) {
            // 응답
            RsrvResponse rsrvResponse = gson.fromJson(jsonFileContent, RsrvResponse.class);
            return rsrvResponse;
        } else {
            // 예외 처리 패턴 getOrElse : 예외 대신 기본 값을 리턴함(null이 아닌 기본 값)
            return Collections.emptyList();
        }
    }

    @Override
    public JsonObject parsingJson(RsrvResponse rsrvResponse) { // 요청 JSON 전문 생성
        String jsonContent = gson.toJson(rsrvResponse);
        JsonObject requestJson = gson.fromJson(jsonContent, JsonObject.class);
        return requestJson;
    }
}
