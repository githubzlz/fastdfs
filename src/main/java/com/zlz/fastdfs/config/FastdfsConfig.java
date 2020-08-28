package com.zlz.fastdfs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020/1/11 23:41
 */
@ConfigurationProperties("fastdfs.config")
@Component
public class FastdfsConfig {
    private String configFile = "fdfs_client.conf";

    public FastdfsConfig() {
    }

    public String getConfigFile() {
        System.out.println("获取了文件名"+this.configFile);
        return this.configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }
}
