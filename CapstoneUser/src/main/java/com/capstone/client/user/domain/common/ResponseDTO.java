package com.capstone.client.user.domain.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ResponseDTO {
    public static ResponseEntity<Object> getResponse(String message, HttpStatus status, Map<String, Object> map){
        Map<String, Object> data = new HashMap<>();

        if ( map != null && !map.isEmpty()){
            data.put("data", map);
        } else {
            data.put("data", Collections.EMPTY_MAP);
        }

        data.put("message", message);
        data.put("status", status.value());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-cache");
        return new ResponseEntity<>(data, headers, status);
    }

}