package com.hanhwa.rsrvprogramh.controller;

import com.google.gson.JsonObject;
import com.hanhwa.rsrvprogramh.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

// Representative State Transfer(REST) : 웹 상의 자료를 HTTP위에서 별도의 전송 계층 없이 전송하기 위한 간단한 인터페이스(분산 시스템을 위한 아키텍처)
// RestTemplate : Blocking I/O기반 멀티 스레드 방식의 동기식(Synchronous) HTTP 통신 API
// Webclient : Non-Blocking I/O기반 싱글 스레드 방식의 비동기식(Asynchronous) HTTP 통신 API(Blocking 사용 가능)
// 동시 사용자 1000명까지 처리 속도 비슷하나 그 이상은 Webclient 사용 권장됨
// @RestController : JSON 형태로 객체 데이터를 반환하는 것이 주 목적
// @Controller + @ResponseBody
@RestController
public class RestTemplateController {
    private final RestTemplate restTemplate;
    @Autowired
    private final ConnectionService connectionService;

    public RestTemplateController(RestTemplate restTemplate, ConnectionService connectionService) {
        this.restTemplate = restTemplate;
        this.connectionService = connectionService;
    }
    @PostMapping("/rsrvResponse")
    public HttpEntity<JsonObject> rsrvResponse() {
        // Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");

        // Body 생성
        JsonObject responseJson = connectionService.createResponse();

        // HttpEntity 생성
        // HttpEntity<T> : HTTP 요청/응답에 해당하는 HttpHeader와 HttpBody를 포함하는 클래스
        // RequestEntity / ResponseEntity : HttpEntity 클래스를 상속받아 구현한 클래스
        HttpEntity<JsonObject> response = new HttpEntity<>(responseJson, headers);

        return response;
    }
}
