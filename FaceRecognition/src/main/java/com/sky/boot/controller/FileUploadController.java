package com.sky.boot.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.sky.boot.entity.Oss;
import com.sky.boot.util.Compare;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Administrator
 */
@Controller
public class FileUploadController {

    @Autowired
    Compare compare;

    @Autowired
    @Qualifier(value = "MyOss")
    private Oss oss;

    @RequestMapping("/uploadFile")
    @ResponseBody
    public String uploadFile(@RequestParam(value = "file") MultipartFile file) {
        String result = null;
        if (file != null) {
            String newFileName = null;
            try {
                // Endpoint以杭州为例，其它Region请按实际情况填写。
                String endpoint = oss.getEndpoint();
// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
                String accessKeyId = oss.getAccessKeyId();
                String accessKeySecret = oss.getAccessKeySecret();

                // 创建OSSClient实例
                OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

                byte[] bytes = file.getBytes();
                // 重新创建一个文件名
                newFileName = UUID.randomUUID().toString().replace("-", "").toUpperCase() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
                ossClient.putObject(oss.getBucketName(), newFileName, new ByteArrayInputStream(bytes));

                // 关闭OSSClient。
                ossClient.shutdown();
                String uploadFilePath = "https://" + oss.getBucketDomain() + "/" + newFileName;
                result = compare.compare(uploadFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
