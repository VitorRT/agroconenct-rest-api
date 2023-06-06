package br.com.smashcode.api.agroconnect.exception.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RestSwearWordException extends Exception{
    public RestSwearWordException(String message) {
        super(message);
    }
}
