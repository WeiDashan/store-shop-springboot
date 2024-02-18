package com.weidashan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.weidashan.pojo.Img;
import com.weidashan.mapper.ImgMapper;
import com.weidashan.service.IImgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.minio.DownloadObjectArgs;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * 图片表 服务实现类
 * </p>
 *
 * @author weidashan
 * @since 2023-10-25
 */
@Service
public class ImgServiceImpl extends ServiceImpl<ImgMapper, Img> implements IImgService {
    @Value("${minio.endpoint}")
    String endpoint;
    @Value("${minio.username}")
    String username;
    @Value("${minio.password}")
    String password;
    @Override
    public String upload(MultipartFile file) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        // 上传之前先看看 以前传没传过
        String md5 = DigestUtils.md5Hex(file.getInputStream());
        long size = file.getSize();
        String suffix = FilenameUtils.getExtension(file.getOriginalFilename());
        QueryWrapper<Img> wrapper = new QueryWrapper<>();
        wrapper.eq("md5",md5)
                .eq("size",size)
                .eq("suffix",suffix);
        Img img = this.getOne(wrapper);
        if(img != null) {
            return img.getUrl();
        } else {
            // 定义新文件名
            StringBuilder builder = new StringBuilder();
            LocalDateTime now = LocalDateTime.now();
            builder.append(now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
            builder.append(RandomStringUtils.random(6,false,true));
            builder.append(".").append(suffix);
            // 构建 MinioClient对象
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(endpoint)
                    .credentials(username,password).build();
            // 上传文件
            PutObjectArgs args = PutObjectArgs.builder()
                    .object(builder.toString())
                    .bucket("images")
                    .contentType(file.getContentType())
                    .stream(file.getInputStream(),file.getSize(),0)
                    .build();
            minioClient.putObject(args);
            img = new Img(md5,size,suffix,builder.toString());
            this.save(img);
            return builder.toString();
        }
    }

//    public void getFile(String filename) throws ServerException, InvalidBucketNameException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
//        // 构建 MinioClient对象
//        MinioClient minioClient = MinioClient.builder()
//                .endpoint(endpoint)
//                .credentials(username,password).build();
//        GetObjectArgs args = GetObjectArgs.builder()
//                .object(filename)
//                .bucket("images")
//                .build();
//        InputStream inputStream = minioClient.getObject(args);
//
//    }
}
