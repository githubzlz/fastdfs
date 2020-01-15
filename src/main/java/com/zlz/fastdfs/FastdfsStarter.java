package com.zlz.fastdfs;

import com.zlz.fastdfs.config.FastdfsConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020/1/11 23:40
 */
@EnableConfigurationProperties({FastdfsConfig.class})
@ComponentScan(
        basePackages = {"com.zlz.fastdfs"}
)
public class FastdfsStarter {
    public FastdfsStarter() {
    }
}
