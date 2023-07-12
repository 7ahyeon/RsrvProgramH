package com.hanhwa.rsrvprogramh.controller;

import com.google.gson.JsonObject;
import com.hanhwa.rsrvprogramh.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

// @RestController : JSON 형태로 객체 데이터를 반환하는 것이 주 목적
// @Controller + @ResponseBody
@RestController
public class RestTemplateController {
    @Autowired
    private final ConnectionService connectionService;

    public RestTemplateController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }
    @PostMapping("/rsrvResponse")
    public HttpEntity<JsonObject> rsrvResponse(@RequestBody JsonObject request) {
        HttpEntity<JsonObject> response = connectionService.httpConnection(request);
        System.out.println("요청");
        System.out.println(request.toString());
        return response;
    }
}
