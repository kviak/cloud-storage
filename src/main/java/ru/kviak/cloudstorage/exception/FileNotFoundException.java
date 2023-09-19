package ru.kviak.cloudstorage.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FileNotFoundException extends RuntimeException{
    String message;
}
