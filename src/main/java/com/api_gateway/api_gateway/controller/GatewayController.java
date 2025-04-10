package com.api_gateway.api_gateway.controller;

import com.api_gateway.api_gateway.dtos.StudentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GatewayController {
    private final RestTemplate restTemplate;

    public GatewayController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/get")
    public final List<StudentDto> getIStudents (){
        String url = "http://core:8082/get";
        ResponseEntity<List<StudentDto>> response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<StudentDto>>() {} );
        return response.getBody();
    }

    @PostMapping("/post")
    public StudentDto createUser(@RequestBody StudentDto student) {
        // Отправляем запрос POST в Core сервис
        HttpHeaders headers = new HttpHeaders();
        String url = "http://core:8082/post";
        HttpEntity<StudentDto> entity = new HttpEntity<>(student, headers);
        ResponseEntity<StudentDto> response = restTemplate.exchange(url, HttpMethod.POST, entity, StudentDto.class);
        return response.getBody();
    }

    @PutMapping("/pur/{id}")
    public void handlePutRequest(@PathVariable String id, @RequestBody String body) {
        String url = "http://core:8082/put/{id}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        restTemplate.exchange(url, HttpMethod.PATCH, entity, String.class);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable int id) {
        String url = "http://core:8082/delete/{id}";
        restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
    }
}
