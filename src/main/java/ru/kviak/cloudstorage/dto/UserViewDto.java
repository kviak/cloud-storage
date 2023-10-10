package ru.kviak.cloudstorage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class UserViewDto {
    private String username;
    private String roles;
}
