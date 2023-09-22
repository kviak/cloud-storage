package ru.kviak.cloudstorage.exception;

import lombok.experimental.StandardException;

@StandardException
public class MinioNotFoundException extends RuntimeException{
    private String message;
}
