package com.microservice.OrderService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.microservice.OrderService.model.ErrorMessage;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorMessage> customException(CustomException customException){
        return new ResponseEntity(new ErrorMessage().builder()
                .message(customException.getMessage())
                .error(customException.getError())
                .build(),
                HttpStatus.valueOf(customException.getStatus())
        );
    }

}
