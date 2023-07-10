package com.hanhwa.rsrvprogramh.service.impl;

import com.google.gson.JsonObject;
import com.hanhwa.rsrvprogramh.model.RsrvRequest;
import com.hanhwa.rsrvprogramh.model.RsrvResponse;
import com.hanhwa.rsrvprogramh.service.ConnectionService;
import com.hanhwa.rsrvprogramh.service.RsrvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ConnectionServiceImpl implements ConnectionService {
    private final RestTemplate restTemplate;
    @Autowired
    private final RsrvService rsrvService;

    public ConnectionServiceImpl(RestTemplate restTemplate, RsrvService rsrvService) {
        this.restTemplate = restTemplate;
        this.rsrvService = rsrvService;
    }
    @Override
    public HttpEntity<JsonObject> httpConnection() {
        // Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");

        // Body 생성
        JsonObject responseJson = createResponse();

        // HttpEntity 생성
        // HttpEntity<T> : HTTP 요청/응답에 해당하는 HttpHeader와 HttpBody를 포함하는 클래스
        // RequestEntity / ResponseEntity : HttpEntity 클래스를 상속받아 구현한 클래스
        HttpEntity<JsonObject> response = new HttpEntity<>(responseJson, headers);

        return response;
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
