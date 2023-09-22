package ru.kviak.cloudstorage.exception;

import lombok.experimental.StandardException;

@StandardException
public class FileSizeExceedException extends RuntimeException{
    private String message;
}
