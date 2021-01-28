package com.musicapp.web.handler;

import com.musicapp.dto.SimpleResponse;
import com.musicapp.exception.AbstractRuntimeExceptionWithFieldName;
import com.musicapp.exception.FriendInviteException;
import com.musicapp.exception.NotFoundException;
import com.musicapp.exception.WrongFileTypeException;
import com.musicapp.util.ExceptionUtils;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.engine.path.NodeImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Обработчик ошибок.
 *
 * @author evgeniycheban
 */
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionInfoHandler {

    private static final Map<String, String> CONSTRAINT_CODE_MAP;

    static {
        CONSTRAINT_CODE_MAP = new HashMap<>();
        CONSTRAINT_CODE_MAP.put("email", "user.email.duplicate");
        CONSTRAINT_CODE_MAP.put("phone", "user.phone.duplicate");
        CONSTRAINT_CODE_MAP.put("username", "user.username.duplicate");
    }

    private final MessageSource messageSource;

    /**
     * Обрабатывает ошибки валидации.
     *
     * @param e ошибка валидации
     * @return ошибки валидации в формате название поля - сообщение об ошибке
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationError(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> errors = bindingResult.getFieldErrors();

        Map<String, String> map = errors.stream()
                .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage));

        return ResponseEntity.badRequest()
                .body(map);
    }

    /**
     * Обрабатывает ошибки валидации.
     *
     * @param e ошибка валидации
     * @return ошибки валидации в формате название поля - сообщение об ошибке
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleValidationError(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();

        Map<String, String> map = constraintViolations.stream()
                .collect(Collectors.toMap(this::getFieldName, ConstraintViolation::getMessage));

        return ResponseEntity.badRequest()
                .body(map);
    }

    @ExceptionHandler({WrongFileTypeException.class, FriendInviteException.class})
    public ResponseEntity<SimpleResponse> handleWrongFileType(AbstractRuntimeExceptionWithFieldName e) {
        return ResponseEntity.badRequest()
                .body(SimpleResponse.builder()
                        .withPropertyName(e.getFieldName())
                        .withPropertyValue(getMessage(e.getMessage()))
                        .build());
    }

    /**
     * Обрабатывает ошибку конфликта.
     *
     * @param e ошибка конфликта
     * @return ошибка конликта в формате название поля - сообщение об ошибке
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleConflict(DataIntegrityViolationException e) {
        String rootMessage = ExceptionUtils.getRootCause(e).getMessage();

        Map<String, String> map = CONSTRAINT_CODE_MAP.entrySet().stream()
                .filter(entry -> rootMessage.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> getMessage(entry.getValue())));

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(map);
    }

    /**
     * Обрабатывает ошибку при отсутствии сущности в бд.
     *
     * @param e ошибка при отсутствии сущности в бд.
     * @return ошибка при отсутствии сущности в бд, в формате название поля - сообщение об ошибке
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<SimpleResponse> handleNotFound(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(SimpleResponse.builder()
                        .withPropertyName(e.getFieldName())
                        .withPropertyValue(getMessage(e.getMessage()))
                        .build());
    }

    @ExceptionHandler({FileNotFoundException.class, EmptyResultDataAccessException.class})
    public ResponseEntity<Void> handle() {
        return ResponseEntity.notFound().build();
    }

    private String getMessage(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    private String getFieldName(ConstraintViolation<?> violation) {
        PathImpl path = (PathImpl) violation.getPropertyPath();
        NodeImpl node = path.getLeafNode();

        return node.getName();
    }
}
