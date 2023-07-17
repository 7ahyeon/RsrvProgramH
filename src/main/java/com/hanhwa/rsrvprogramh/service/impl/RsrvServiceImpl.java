package com.hanhwa.rsrvprogramh.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hanhwa.rsrvprogramh.config.exception.CloseException;
import com.hanhwa.rsrvprogramh.config.exception.FileNotReadException;
import com.hanhwa.rsrvprogramh.dao.ReserveRepository;
import com.hanhwa.rsrvprogramh.model.ReserveRequest;
import com.hanhwa.rsrvprogramh.model.ReserveRequestInfo;
import com.hanhwa.rsrvprogramh.model.ReserveResponse;
import com.hanhwa.rsrvprogramh.model.ReserveResponseInfo;
import com.hanhwa.rsrvprogramh.service.RsrvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class RsrvServiceImpl implements RsrvService {
    @Autowired
    private final Gson gson;
    @Autowired
    private final ReserveRepository reserveRepository;

    public RsrvServiceImpl(Gson gson, ReserveRepository reserveRepository) {
        this.gson = gson;
        this.reserveRepository = reserveRepository;
    }

    @Override
    public ReserveResponse completeReserve(ReserveRequest reserveRequest) {
        int rsrvNo = reserveRepository.insertReserve(reserveRequest);
        ReserveResponse response = reserveRepository.selectReserve(reserveRequest);
        System.out.println("한화 예약 완료 정보 DB 확인");
        System.out.println(response.toString());
        return response;
    }

    @Override
    public Object bindingObject(String jsonFileContent) { // JSON 전문 Object 바인딩
        if (jsonFileContent.contains("ds_rsrvInfo")) {
            // 요청
            ReserveRequest reserveRequest = gson.fromJson(jsonFileContent, ReserveRequest.class);
            return reserveRequest;
        } else if (jsonFileContent.contains("ds_prcsResult")) {
            // 응답
            ReserveResponse reserveResponse = gson.fromJson(jsonFileContent, ReserveResponse.class);
            return reserveResponse;
        } else {
            // 예외 처리 패턴 getOrElse : 예외 대신 기본 값을 리턴함(null이 아닌 기본 값)
            return Collections.emptyList();
        }
    }

    @Override
    public JsonObject parsingJson(ReserveResponse reserveResponse) { // 요청 JSON 전문 생성
        String jsonContent = gson.toJson(reserveResponse);
        JsonObject requestJson = gson.fromJson(jsonContent, JsonObject.class);
        return requestJson;
    }
}
