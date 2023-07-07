package com.hanhwa.rsrvprogramh.model.service;

import com.hanhwa.rsrvprogramh.model.dto.RsrvResponse;
import org.springframework.stereotype.Service;

@Service
public interface RsrvService {
    // 예약 신청 응답 JSON 파일 읽기
    String getResponseFile();
    // JSON 전문 Object 바인딩
    Object bindingObject(String jsonFileContent);
    // 응답 JSON 전문 생성
    String parsingJson(RsrvResponse rsrvResponse);
}
