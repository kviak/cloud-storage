package ru.kviak.cloudstorage.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FileSizeExceedException extends RuntimeException{
    String message;
}
