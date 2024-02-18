package com.weidashan.controller;


import com.weidashan.service.IImgService;
import io.minio.errors.*;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * 图片表 前端控制器
 * </p>
 *
 * @author weidashan
 * @since 2023-10-25
 */
@RestController
@RequestMapping("/img")
public class ImgController {
    @Resource
    IImgService imgService;
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    String upload(@RequestPart("file") MultipartFile file) throws IOException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, ServerException, ErrorResponseException, XmlParserException, InvalidBucketNameException, InsufficientDataException, InternalException {
        return imgService.upload(file);
    }
}
