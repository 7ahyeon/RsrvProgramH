package com.hanhwa.rsrvprogramh.model.service;

import com.hanhwa.rsrvprogramh.model.dto.RsrvRequest;
import com.hanhwa.rsrvprogramh.model.dto.RsrvResponse;
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
    public String createResponse() {
        // 응답 파일 읽기
        String jsonFileContent = rsrvService.getResponseFile();
        // 응답 Json 전문 Object 바인딩
        RsrvResponse rsrvRequest = (RsrvResponse) rsrvService.bindingObject(jsonFileContent);
        // 응답 Json 전문 생성
        String responseJson = rsrvService.parsingJson(rsrvRequest);

        return responseJson;
    }

    @Override
    public String handleRequest(String responseJsonContent) {
        // 요청 Json 전문 Object 바인딩
        RsrvRequest rsrvRequest = (RsrvRequest) rsrvService.bindingObject(responseJsonContent);
        return rsrvRequest.toString();
    }
}
