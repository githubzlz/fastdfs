package com.zlz.fastdfs.config;

import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020/1/11 23:41
 */
public class FastdfsConfig {
    private String configFile;

    public FastdfsConfig() throws FileNotFoundException, UnsupportedEncodingException {
        this.configFile = URLDecoder.decode(ResourceUtils.getURL("classpath:").getPath(),"utf-8")
                + "fdfs_client.conf";
        this.configFile = this.configFile.substring(1);
    }

    public String getConfigFile() {
        System.out.println("获取了文件名"+this.configFile);
        return this.configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }
}
