package com.weidashan.service;

import com.weidashan.pojo.Img;
import com.baomidou.mybatisplus.extension.service.IService;
import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * 图片表 服务类
 * </p>
 *
 * @author weidashan
 * @since 2023-10-25
 */
public interface IImgService extends IService<Img> {
    String upload(MultipartFile file) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException;
}
