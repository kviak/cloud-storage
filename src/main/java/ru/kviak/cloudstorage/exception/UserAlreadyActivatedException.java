package ru.kviak.cloudstorage.exception;

import lombok.Data;

@Data
public class UserAlreadyActivatedException extends RuntimeException{
    String message;
}
