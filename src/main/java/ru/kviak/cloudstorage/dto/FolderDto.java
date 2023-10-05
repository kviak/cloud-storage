package ru.kviak.cloudstorage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FolderDto {
    private String packageName;
    private String packageLink;
    private int size;
}
