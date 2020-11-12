package com.sky.boot.SmsTool;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Component(value = "Sms")
@ConfigurationProperties(prefix = "sms")
@Data
public class Sms {
    private String regionId;
    private String accessKeyId;
    private String secret;
    private String doMain;
    private String version;
    private String action;
    private String rid;
    private String singName;
    private String templateCode;
}
