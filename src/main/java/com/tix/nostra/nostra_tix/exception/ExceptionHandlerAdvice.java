package com.tix.nostra.nostra_tix.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.tix.nostra.nostra_tix.dto.ErrorMessageDTO;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessageDTO> handleResourceNotFoundException(ResourceNotFoundException ex,
            WebRequest request) {
        List<String> details = new ArrayList<>();

        details.add(ex.getMessage());

        ErrorMessageDTO dto = new ErrorMessageDTO(101, details);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
    }

    @ExceptionHandler(DuplicateUserDataException.class)
    public ResponseEntity<ErrorMessageDTO> handleDuplicateUserDataException(DuplicateUserDataException ex,
            WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ErrorMessageDTO dto = new ErrorMessageDTO(102, details);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(dto);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getParameterName() + " parameter is required");
        ErrorMessageDTO dto = new ErrorMessageDTO(403, details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<String> details = new ArrayList<>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }

        ErrorMessageDTO dto = new ErrorMessageDTO(101, details);

        return ResponseEntity.badRequest().body(dto);
    }
}
