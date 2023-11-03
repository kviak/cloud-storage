package ru.kviak.cloudstorage.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class TestUserFileDto {

    @Test
    public void testNoArgsConstruct() {
        UserFileDto userFileDto = new UserFileDto();

        assertNull(userFileDto.getFileName());
        assertNull(userFileDto.getShareLink());
        assertNull(userFileDto.getFileSize());
    }
    @Test
    public void testAllArgsConstruct() {
        UserFileDto userFileDto = new UserFileDto("file name", "link", 1L);

        assertEquals("file name", userFileDto.getFileName());
        assertEquals("link", userFileDto.getShareLink());
        assertEquals( 1L, userFileDto.getFileSize());
    }
    @Test
    public void testSetterAndGetter() {
        UserFileDto userFileDto = new UserFileDto();

        userFileDto.setFileName("file name");
        userFileDto.setShareLink("link");
        userFileDto.setFileSize(1L);

        assertEquals("file name", userFileDto.getFileName());
        assertEquals("link", userFileDto.getShareLink());
        assertEquals( 1L, userFileDto.getFileSize());
    }

}
