package com.hanhwa.rsrvprogramh.service.impl;

import com.google.gson.JsonObject;
import com.hanhwa.rsrvprogramh.model.ReserveRequest;
import com.hanhwa.rsrvprogramh.model.ReserveResponse;
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
    public HttpEntity<JsonObject> httpConnection(JsonObject request) {
        ReserveRequest reserveRequest = handleRequest(request);
        // Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");

        // Body 생성
        JsonObject responseJson = createResponse(reserveRequest);

        // HttpEntity 생성
        // HttpEntity<T> : HTTP 요청/응답에 해당하는 HttpHeader와 HttpBody를 포함하는 클래스
        // RequestEntity / ResponseEntity : HttpEntity 클래스를 상속받아 구현한 클래스
        HttpEntity<JsonObject> response = new HttpEntity<>(responseJson, headers);
        return response;
    }
    @Override
    public JsonObject createResponse(ReserveRequest reserveRequest) {
        // 예약 신청 요청 저장 및 응답 조회
        ReserveResponse response = rsrvService.completeReserve(reserveRequest);
        // 응답 Json 전문 생성
        JsonObject responseJson = rsrvService.parsingJson(response);

        return responseJson;
    }

    @Override
    public ReserveRequest handleRequest(JsonObject request) {
        // 요청 Json 전문 Object 바인딩
        ReserveRequest reserveRequest = (ReserveRequest) rsrvService.bindingObject(request.toString());
        return reserveRequest;
    }
}
