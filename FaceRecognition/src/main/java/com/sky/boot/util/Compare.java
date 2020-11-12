package com.sky.boot.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.facebody.model.v20191230.CompareFaceRequest;
import com.aliyuncs.facebody.model.v20191230.CompareFaceResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import com.sky.boot.entity.Oss;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * 对比的工具类
 */
@Component
public class Compare {
    @Autowired
    @Qualifier(value = "MyOss")
    private Oss oss;

    public String compare(String nowPic) {

        //上面提到报保存用户的   AccessKey键值----
        DefaultProfile profile = DefaultProfile.getProfile("cn-shanghai", oss.getAccessKeyId(), oss.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);

        String urlA = "https://facerecognation.oss-cn-shanghai.aliyuncs.com/331E18B6306C43798FD954DCB9360948.png";//这个是你 需要比对数据库的图片地址
        String urlB = nowPic;//人脸识别拍的照片上传后的图片地址
        CompareFaceRequest request = new CompareFaceRequest();
        request.setRegionId("cn-shanghai");
        request.setImageURLA(urlA);
        request.setImageURLB(urlB);
        try {
            CompareFaceResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
            //返回的 对比相似读的 值
            System.out.println(response.getData().getConfidence());
            if (response.getData().getConfidence() > 59.999999) {
                return "success";
            } else {
                return "fail";
            }
            // translate
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.err.println("ErrCode:" + e.getErrCode());
            System.err.println("ErrMsg:" + e.getErrMsg());
            System.err.println("RequestId:" + e.getRequestId());
        }
        return "fail";
    }
}
