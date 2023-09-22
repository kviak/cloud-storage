package ru.kviak.cloudstorage.exception;

import lombok.experimental.StandardException;

@StandardException
public class UserAlreadyActivatedException extends RuntimeException{
    private String message;
}
