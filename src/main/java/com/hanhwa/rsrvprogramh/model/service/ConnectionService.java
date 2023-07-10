package com.hanhwa.rsrvprogramh.model.service;

public interface ConnectionService {
    // 응답 JSON 생성
    String createResponse();
    // 요청 JSON 처리
    String handleRequest(String responseJsonContent);
}
