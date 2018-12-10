package com.fotron.draw.utils;

import com.alibaba.fastjson.JSON;
import com.fotron.draw.bean.resp.task.QiniuTokenResp;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.xiaoleilu.hutool.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Date;

/**
 * @author: yutong
 * @createDate: 2018/5/25
 * @company: (C) Copyright tianshen
 * @since: JDK 1.8
 * @Description:
 */
@Component
@Slf4j
public class QiniuUtil {
    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Value("${qiniu.bucket}")
    private String bucket;

    @Value("${qiniu.path}")
    private String path;

    private final Object lock = new Object[0];

    private static QiniuTokenResp token;

    private static final String HEAD_DIR = "game_draw/head/";

    private static final String WXACODE_DIR = "game_draw/wxacode/";

    /**
     * 将图片上传到七牛云
     *
     * @param file
     * @param key  保存在空间中的名字，如果为空会使用文件的hash值为文件名
     * @return
     */
    public String uploadImg(InputStream file, String key) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone1());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        try {

            String upToken = getToken();
            try {
                key = HEAD_DIR + key;
                Response response = uploadManager.put(file, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
                String returnPath = path + "/" + putRet.key;
                log.info("保存地址={}", returnPath);
                return returnPath;
            } catch (QiniuException ex) {
                Response r = ex.response;
                log.error("上传图片失败." + r.toString());
                try {
                    log.error(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getToken() {
        synchronized (this.lock) {
            if (token == null || token.getExpireTime() == null || token.getExpireTime().compareTo(DateUtil.offsetMinute(new Date(), 5)) < 0) {
                Auth auth = Auth.create(accessKey, secretKey);
                String upToken = auth.uploadToken(bucket);
                token = new QiniuTokenResp();
                token.setToken(upToken);
                token.setExpireTime(DateUtil.offsetSecond(new Date(), 3600));
            }
            return token.getToken();
        }
    }

    /**
     * 将图片上传到七牛云
     *
     * @param sz
     * @param key 保存在空间中的名字，如果为空会使用文件的hash值为文件名
     * @return
     */
    public String uploadWxaCodeImg(byte[] sz, String key) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone1());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        try {
            String upToken = getToken();
            try {
                key = WXACODE_DIR + key;
                Response response = uploadManager.put(sz, key, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
                String returnPath = path + "/" + putRet.key;
                log.info("保存地址={}", returnPath);
                return returnPath;
            } catch (QiniuException ex) {
                Response r = ex.response;
                log.error("上传图片失败." + r.toString());
                try {
                    log.error(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
