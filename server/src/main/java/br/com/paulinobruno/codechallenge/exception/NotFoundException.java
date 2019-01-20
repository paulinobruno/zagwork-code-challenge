package br.com.paulinobruno.codechallenge.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class NotFoundException extends BaseException implements Supplier<NotFoundException> {

    public NotFoundException() {
        this(NOT_FOUND.getReasonPhrase());
    }

    public NotFoundException(String message) {
        super(message);
        super.logref = "NOT_FOUND";
    }


    @Override
    public NotFoundException get() {
        return this;
    }
}
