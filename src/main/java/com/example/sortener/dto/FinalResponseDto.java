package com.example.sortener.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class FinalResponseDto {

    Map<String, Integer> data = new HashMap<>();

}
