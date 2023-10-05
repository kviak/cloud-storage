package ru.kviak.cloudstorage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserFileDto {
    private String fileName;
    private String linkFile;
    private Long fileSize;
}
