package com.zlz.fastdfs.common;

import java.util.Date;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020/1/11 23:41
 */
public class FileBasicInfo {
    private String sourceIpAddr;
    private long fileSize;
    private Date createTimestamp;

    public FileBasicInfo() {
    }

    public FileBasicInfo(String sourceIpAddr, long fileSize, Date createTimestamp) {
        this.sourceIpAddr = sourceIpAddr;
        this.fileSize = fileSize;
        this.createTimestamp = createTimestamp;
    }

    public Date getCreateTimestamp() {
        return this.createTimestamp;
    }

    public void setCreateTimestamp(Date createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public long getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getSourceIpAddr() {
        return this.sourceIpAddr;
    }

    public void setSourceIpAddr(String sourceIpAddr) {
        this.sourceIpAddr = sourceIpAddr;
    }
}

