package com.hanhwa.rsrvprogramh.service;

import com.google.gson.JsonObject;
import org.springframework.http.HttpEntity;

public interface ConnectionService {
    // HTTP 통신
    HttpEntity<JsonObject> httpConnection();
    // 응답 JSON 생성
    JsonObject createResponse();
    // 요청 JSON 처리
    String handleRequest(String responseJsonContent);
}
