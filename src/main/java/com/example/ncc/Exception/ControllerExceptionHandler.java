package com.example.ncc.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(BranchNotFoundException.class)
    public ResponseEntity<String> branchNotFoundException(BranchNotFoundException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(OpenTalkNotFoundException.class)
    public ResponseEntity<String> openTalkNotFoundException(OpenTalkNotFoundException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> userNotFoundException(UserNotFoundException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<String> roleNotFoundException(RoleNotFoundException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(AuthenticateFailedException.class)
    public ResponseEntity<String> authenticateFailedException(AuthenticateFailedException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
