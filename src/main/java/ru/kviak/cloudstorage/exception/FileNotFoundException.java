package ru.kviak.cloudstorage.exception;

import lombok.experimental.StandardException;

@StandardException
public class FileNotFoundException extends RuntimeException{
    private String message;
}
