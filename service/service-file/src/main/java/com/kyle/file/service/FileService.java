package com.kyle.file.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @auther kyle
 * @creat 2022-11-19:56
 */
public interface FileService {
    String uploadAvatar(MultipartFile file);

    String uploadResume(MultipartFile file);

    boolean delAvatar(String url);
}
