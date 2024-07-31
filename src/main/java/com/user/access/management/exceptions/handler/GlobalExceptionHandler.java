package com.user.access.management.exceptions.handler;

import com.user.access.management.exceptions.ResourceAlreadyExist;
import com.user.access.management.exceptions.ResourceNotExist;
import com.user.access.management.web.ApiError;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Access denied");
        body.put("message", "You don't have permission to access this resource");
        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(ResourceAlreadyExist.class)
    public ResponseEntity<Object> handleResourceAlreadyExistException(ResourceAlreadyExist ex, WebRequest request) {
        log.error("ResourceAlreadyExist: " + ex.getMessage(), ex);

        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());
        ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Resource already exists.", details);
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ResourceNotExist.class)
    public ResponseEntity<Object> handleResourceNotExistException(ResourceNotExist ex, WebRequest request) {
        log.error("ResourceNotExist: " + ex.getMessage(), ex);

        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());
        ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Resource Not exists.", details);
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }


}
