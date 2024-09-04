package com.VueTestWithSpring.exception;

import com.VueTestWithSpring.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class AppExceptionHandler {


    @ExceptionHandler(value = {AppException.class})
    public ResponseEntity<ErrorDto> exceptionHandler(AppException appException) {
        return ResponseEntity.status(appException.getStatus())
                .body(ErrorDto.builder().message(appException.getMessage()).build());
    }


}
