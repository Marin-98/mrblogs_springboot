package com.example.demo.service;

import org.hibernate.service.spi.ServiceException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface FileUpAndDownService {

    Map<String, Object> uploadPicture(MultipartFile file) throws ServiceException;

}