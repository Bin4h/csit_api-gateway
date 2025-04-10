package com.api_gateway.api_gateway.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentDto {
    public int id;
    public String name = "";
    public String email = "";
}
