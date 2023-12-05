package br.com.rsanme.controlegastos.exceptions.handlers;

import br.com.rsanme.controlegastos.exceptions.BusinessException;
import br.com.rsanme.controlegastos.exceptions.CustomEntityAlreadyExistsException;
import br.com.rsanme.controlegastos.exceptions.CustomEntityNotFoundException;
import br.com.rsanme.controlegastos.exceptions.CustomUsernameNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 04/12/2023
 * Hora: 23:17
 */
@ControllerAdvice
public class CustomApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<CustomErrorResponse.FieldWithError> fieldsWithErrors = new ArrayList<>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            fieldsWithErrors.add(new CustomErrorResponse.FieldWithError(fieldName, message));
        }

        CustomErrorResponse error = CustomErrorResponse.builder()
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .title("Um ou mais campos inválidos! Preencha corretamente")
                .fields(fieldsWithErrors).build();

        return super.handleExceptionInternal(ex, error, headers, status, request);
    }

    @ExceptionHandler(CustomEntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<Object> handleCustomEntityNotFound(
            CustomEntityNotFoundException ex, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        CustomErrorResponse error = CustomErrorResponse.builder()
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .title(ex.getMessage())
                .build();

        return super.handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(CustomEntityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ResponseEntity<Object> handleCustomEntityAlreadyExists(
            CustomEntityAlreadyExistsException ex, WebRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;

        CustomErrorResponse error = CustomErrorResponse.builder()
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .title(ex.getMessage())
                .build();

        return super.handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(CustomUsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<Object> handleCustomUsernameNotFound(
            CustomUsernameNotFoundException ex, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        CustomErrorResponse error = CustomErrorResponse.builder()
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .title(ex.getMessage())
                .build();

        return super.handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleBusinessException(
            BusinessException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        CustomErrorResponse error = CustomErrorResponse.builder()
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .title(ex.getMessage())
                .build();

        return super.handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

}
