package ru.kviak.cloudstorage.exception;

import lombok.experimental.StandardException;

@StandardException
public class UserNotFoundException extends RuntimeException{
    private String message;
}
