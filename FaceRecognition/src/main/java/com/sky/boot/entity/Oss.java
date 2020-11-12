package com.sky.boot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Component(value = "MyOss")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Oss {
    //地域节点
    @Value(value = "${oss.endpoint}")
    private String endpoint;
    // oss 公钥
    @Value(value = "${oss.accessKeyId}")
    private String accessKeyId;
    // oss 私钥
    @Value(value = "${oss.accessKeySecret}")
    private String accessKeySecret;
    // oss bucket 仓库名
    @Value(value = "${oss.bucketName}")
    private String bucketName;
    @Value(value = "${oss.bucket.domain}")
    private String bucketDomain;
}
