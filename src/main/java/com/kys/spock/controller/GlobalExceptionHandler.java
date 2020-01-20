package com.kys.spock.controller;

import com.kys.spock.common.constants.ErrorCode;
import com.kys.spock.common.result.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author kody.kim
 * @since 20/01/2020
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> error(Exception e){
        return ResponseEntity.status(500).body(Response.error(ErrorCode.CD_0000));
    }
}
