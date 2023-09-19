package ru.kviak.cloudstorage.exception;

import lombok.Data;

@Data
public class UserNotFoundException extends RuntimeException{
    String message;
}
