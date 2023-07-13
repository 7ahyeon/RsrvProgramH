package com.hanhwa.rsrvprogramh.service;

import com.google.gson.JsonObject;
import com.hanhwa.rsrvprogramh.model.ReserveRequest;
import com.hanhwa.rsrvprogramh.model.ReserveResponse;

public interface RsrvService {
    // 예약 신청 응답 JSON 파일 읽기
    String getResponseFile();
    ReserveResponse completeResponse(ReserveRequest reserveRequest);
    // JSON 전문 Object 바인딩
    Object bindingObject(String jsonFileContent);
    // 응답 JSON 전문 생성
    JsonObject parsingJson(ReserveResponse reserveResponse);
}
