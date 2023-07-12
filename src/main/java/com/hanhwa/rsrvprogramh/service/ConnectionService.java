package com.hanhwa.rsrvprogramh.service;

import com.google.gson.JsonObject;
import com.hanhwa.rsrvprogramh.model.ReserveRequest;
import com.hanhwa.rsrvprogramh.model.ReserveResponse;
import org.springframework.http.HttpEntity;

public interface ConnectionService {
    // HTTP 통신
    HttpEntity<JsonObject> httpConnection(JsonObject request);
    // 응답 JSON 생성
    JsonObject createResponse(ReserveRequest reserveRequest);
    // 요청 JSON 처리
    ReserveRequest handleRequest(JsonObject request);
}
