package com.hanhwa.rsrvprogramh.model.service;

import com.google.gson.JsonObject;

public interface ConnectionService {
    // 응답 JSON 생성
    JsonObject createResponse();
    // 요청 JSON 처리
    String handleRequest(String responseJsonContent);
}
