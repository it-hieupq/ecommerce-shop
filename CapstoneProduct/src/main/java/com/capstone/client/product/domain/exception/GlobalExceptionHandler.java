//package com.capstone.client.product.domain.exception;
//
//import com.capstone.client.product.domain.common.ResponseDTO;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> getException(Exception e) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("error", e.getMessage());
//        return ResponseDTO.getResponse(HttpStatus.EXPECTATION_FAILED, map);
//    }
//
//}
