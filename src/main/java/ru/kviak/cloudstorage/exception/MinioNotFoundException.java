package ru.kviak.cloudstorage.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MinioNotFoundException extends RuntimeException{
    String message;
}
