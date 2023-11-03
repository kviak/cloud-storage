package ru.kviak.cloudstorage.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TestFolderDto {
    @Test
    public void testNoArgsConstructor() {
        FolderDto folderDto = new FolderDto();
        assertNull(folderDto.getPackageName());
        assertNull(folderDto.getPackageLink());
        assertEquals(0, folderDto.getSize());
    }

    @Test
    public void testAllArgsConstructor() {
        FolderDto folderDto = new FolderDto("TestFolder", "/test-folder", 100);
        assertEquals("TestFolder", folderDto.getPackageName());
        assertEquals("/test-folder", folderDto.getPackageLink());
        assertEquals(100, folderDto.getSize());
    }

    @Test
    public void testGettersAndSetters() {
        FolderDto folderDto = new FolderDto();

        folderDto.setPackageName("NewFolder");
        folderDto.setPackageLink("/new-folder");
        folderDto.setSize(50);

        assertEquals("NewFolder", folderDto.getPackageName());
        assertEquals("/new-folder", folderDto.getPackageLink());
        assertEquals(50, folderDto.getSize());
    }
}

