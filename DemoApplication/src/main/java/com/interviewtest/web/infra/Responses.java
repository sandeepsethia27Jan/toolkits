package com.interviewtest.web.infra;

import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.TEXT_PLAIN;

/**
 * Created by ssethia on 05/26/2018.
 */
public final class Responses {

    private Responses() {
    }

    public static <T> ResponseEntity<T> ok(T body) {
        return ResponseEntity.ok(body);
    }

    public static <T> ResponseEntity<T> created(T body, String newlyCreatedResource) {
        return ResponseEntity.created(URI.create(newlyCreatedResource))
                .body(body);
    }

    public static ResponseEntity<?> notFound() {
        return ResponseEntity.notFound().build();
    }

    public static ResponseEntity<?> noContent() {
        return ResponseEntity.noContent().build();
    }

    public static ResponseEntity<?> badRequest() {
        return ResponseEntity.badRequest().build();
    }

    public static ResponseEntity<String> badRequest(String message) {
        return ResponseEntity.badRequest()
                .contentType(TEXT_PLAIN)
                .body(message);
    }

    public static <T> ResponseEntity<T> badRequest(T message) {
        return ResponseEntity.badRequest()
                .contentType(TEXT_PLAIN)
                .body(message);
    }

    public static ResponseEntity<String> internalServerError(String message) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .contentType(TEXT_PLAIN)
                .body(message);
    }
}
