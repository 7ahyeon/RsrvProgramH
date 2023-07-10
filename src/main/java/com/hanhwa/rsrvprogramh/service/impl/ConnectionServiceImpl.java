package com.hanhwa.rsrvprogramh.service.impl;

import com.google.gson.JsonObject;
import com.hanhwa.rsrvprogramh.model.RsrvRequest;
import com.hanhwa.rsrvprogramh.model.RsrvResponse;
import com.hanhwa.rsrvprogramh.service.ConnectionService;
import com.hanhwa.rsrvprogramh.service.RsrvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConnectionServiceImpl implements ConnectionService {
    @Autowired
    private final RsrvService rsrvService;

    public ConnectionServiceImpl(RsrvService rsrvService) {
        this.rsrvService = rsrvService;
    }

    @Override
    public JsonObject createResponse() {
        // 응답 파일 읽기
        String jsonFileContent = rsrvService.getResponseFile();
        // 응답 Json 전문 Object 바인딩
        RsrvResponse rsrvRequest = (RsrvResponse) rsrvService.bindingObject(jsonFileContent);
        // 응답 Json 전문 생성
        JsonObject responseJson = rsrvService.parsingJson(rsrvRequest);

        return responseJson;
    }

    @Override
    public String handleRequest(String responseJsonContent) {
        // 요청 Json 전문 Object 바인딩
        RsrvRequest rsrvRequest = (RsrvRequest) rsrvService.bindingObject(responseJsonContent);
        return rsrvRequest.toString();
    }
}
