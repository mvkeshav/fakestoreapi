package org.keshava.scalerecommerce.controllers;

import org.keshava.scalerecommerce.models.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler()
    public ResponseEntity<String> processProductNotFoundException(ProductNotFoundException e) {

        var response = new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        return response;
    }

    @ExceptionHandler
    public ResponseEntity<String> processNoResourseFoundException(NoResourceFoundException e) {
        var response = new ResponseEntity<String>("The specified url does not exist.\nDetails: " + e.getMessage(), HttpStatus.NOT_FOUND);
        return response;
    }
}
