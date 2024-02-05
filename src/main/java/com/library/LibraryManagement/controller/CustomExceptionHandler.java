package com.library.LibraryManagement.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.library.LibraryManagement.response.Response;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {


  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, 
           HttpHeaders headers, HttpStatusCode status, WebRequest request) {

    List<String> details = new ArrayList<>();
    String message = null;
    for (ObjectError error : ex.getBindingResult().getAllErrors()) {
    	message=error.getDefaultMessage();
    }
    Response error = new Response(HttpStatus.BAD_REQUEST,message);
    return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
  }
}