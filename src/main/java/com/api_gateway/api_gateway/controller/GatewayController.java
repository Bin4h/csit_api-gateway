package com.api_gateway.api_gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class GatewayController {
    private final RestTemplate restTemplate;
    private static final String CORE_SERVICE_URL = "http://localhost:8082";

    @Autowired
    public GatewayController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping(value = "/**", method = RequestMethod.GET)
    public ResponseEntity<String> handleGetRequest(HttpServletRequest request) {
        String path = request.getRequestURI().replaceFirst("/api/get", "");
        String url = CORE_SERVICE_URL + path;

        return restTemplate.getForEntity(url, String.class);
    }

    @RequestMapping(value = "/**", method = RequestMethod.POST)
    public ResponseEntity<String> handlePostRequest(HttpServletRequest request, @RequestBody String body) {
        String path = request.getRequestURI().replaceFirst("/api/post", "");
        String url = CORE_SERVICE_URL + path;

        return restTemplate.postForEntity(url, body, String.class);
    }

    @RequestMapping(value = "/**", method = RequestMethod.PUT)
    public ResponseEntity<String> handlePutRequest(HttpServletRequest request, @RequestBody String body) {
        String path = request.getRequestURI().replaceFirst("/api/put", "");
        String url = CORE_SERVICE_URL + path;

        restTemplate.put(url, body);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/**", method = RequestMethod.DELETE)
    public ResponseEntity<String> handleDeleteRequest(HttpServletRequest request) {
        String path = request.getRequestURI().replaceFirst("/api/delete", "");
        String url = CORE_SERVICE_URL + path;

        restTemplate.delete(url);
        return ResponseEntity.ok().build();
    }
}
