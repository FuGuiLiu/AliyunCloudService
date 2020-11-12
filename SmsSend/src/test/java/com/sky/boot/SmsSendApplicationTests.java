package com.sky.boot;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.sky.boot.SmsTool.Sms;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SmsSendApplicationTests {
    @Autowired
    @Qualifier(value = "Sms")
    private Sms sms;

    @Test
    void contextLoads() {
        //短信发送
        DefaultProfile profile = DefaultProfile.getProfile(sms.getRegionId(), sms.getAccessKeyId(), sms.getSecret());
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(sms.getDoMain());
        request.setSysVersion(sms.getVersion());
        request.setSysAction(sms.getAction());
        try {
            request.putQueryParameter("RegionId", sms.getRegionId());
            //确定发送的电话号码和 验证码
            request.putQueryParameter("PhoneNumbers", "199****8118");
            //随机的验证码
            // String code = UUID.randomUUID().toString().substring(0, 4);
            request.putQueryParameter("TemplateParam", "{\"code\":\"" + 12138 + "\"}");
            // 确定是的模版和签名
            request.putQueryParameter("SignName", sms.getSingName());
            request.putQueryParameter("TemplateCode", sms.getTemplateCode());
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (
                ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
