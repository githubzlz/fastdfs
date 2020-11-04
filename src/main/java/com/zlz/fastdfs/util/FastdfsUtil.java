package com.zlz.fastdfs.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.zlz.fastdfs.common.FileBasicInfo;
import com.zlz.fastdfs.config.FastdfsConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FastdfsUtil {
    private static Logger logger = LogManager.getLogger(FastdfsUtil.class);
    @Autowired
    private FastdfsConfig config;

    public FastdfsUtil() {
    }

    public String uploadFile(byte[] fileByte, String fileName) throws IOException, MyException {
        return this.uploadFile(fileByte, fileName, (String)null, (Map)null);
    }

    public String uploadFile(byte[] fileByte, String fileName, Map<String, String> metaInfos) throws IOException, MyException {
        return this.uploadFile(fileByte, fileName, (String)null, metaInfos);
    }

    private String uploadFile(byte[] fileByte, String fileName, String extName, Map<String, String> metaInfos) throws IOException, MyException {
        if (extName == null) {
            int nPos = fileName.lastIndexOf(46);
            if (nPos > 0 && fileName.length() - nPos <= 7) {
                extName = fileName.substring(nPos + 1);
            }
        }

        NameValuePair[] nvp = this.getMetaInfos(fileName, extName, metaInfos);
        ClientGlobal.init(this.config.getConfigFile());
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;
        StorageClient1 storageClient = new StorageClient1(trackerServer, (StorageServer)storageServer);
        String fileIds = storageClient.upload_file1(fileByte, extName, nvp);
        return fileIds;
    }

    public String uploadFile2Group(String groupName, byte[] fileByte, String fileName) throws IOException, MyException {
        return this.uploadFile2Group(groupName, fileByte, fileName, (String)null, (Map)null);
    }

    public String uploadFile2Group(String groupName, byte[] fileByte, String fileName, Map<String, String> metaInfos) throws IOException, MyException {
        return this.uploadFile2Group(groupName, fileByte, fileName, (String)null, metaInfos);
    }

    private String uploadFile2Group(String groupName, byte[] fileByte, String fileName, String extName, Map<String, String> metaInfos) throws IOException, MyException {
        if (extName == null) {
            int nPos = fileName.lastIndexOf(46);
            if (nPos > 0 && fileName.length() - nPos <= 7) {
                extName = fileName.substring(nPos + 1);
            }
        }

        NameValuePair[] nvp = this.getMetaInfos(fileName, extName, metaInfos);
        ClientGlobal.init(this.config.getConfigFile());
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;
        StorageClient1 storageClient = new StorageClient1(trackerServer, (StorageServer)storageServer);
        String fileIds = storageClient.upload_file1(groupName, fileByte, extName, nvp);
        return fileIds;
    }

    private NameValuePair[] getMetaInfos(String fileName, String extName, Map<String, String> metaInfos) {
        int nvpSize = 2;
        if (metaInfos != null) {
            nvpSize += metaInfos.size();
        }

        NameValuePair[] nvp = new NameValuePair[nvpSize];
        nvp[0] = new NameValuePair("OriginFileName");
        nvp[0].setValue(fileName);
        nvp[1] = new NameValuePair("OriginFileExtName");
        nvp[1].setValue(extName);
        if (metaInfos != null) {
            Iterator<String> metaKeys = metaInfos.keySet().iterator();

            for(int index = 2; metaKeys.hasNext(); ++index) {
                String key = (String)metaKeys.next();
                String value = (String)metaInfos.get(key);
                nvp[index] = new NameValuePair(key);
                nvp[index].setValue(value);
            }
        }

        return nvp;
    }

    public byte[] downLoadFile(String fileId) throws IOException, MyException {
        ClientGlobal.init(this.config.getConfigFile());
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;
        StorageClient1 storageClient = new StorageClient1(trackerServer, (StorageServer)storageServer);
        byte[] fileBytes = storageClient.download_file1(fileId);
        return fileBytes;
    }

    public boolean deleteFile(String fileId) throws IOException, MyException {
        ClientGlobal.init(this.config.getConfigFile());
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;
        StorageClient1 storageClient = new StorageClient1(trackerServer, (StorageServer)storageServer);
        int i = storageClient.delete_file1(fileId);
        if (logger.isDebugEnabled()) {
            logger.debug(i == 0 ? "删除成功" : "删除失败:" + i);
        }

        return i == 0;
    }

    public FileBasicInfo getFileBasicInfo(String fileId) throws IOException, MyException {
        ClientGlobal.init(this.config.getConfigFile());
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;
        StorageClient1 storageClient = new StorageClient1(trackerServer, (StorageServer)storageServer);
        FileInfo fi = storageClient.get_file_info1(fileId);
        FileBasicInfo fbi = new FileBasicInfo(fi.getSourceIpAddr(), fi.getFileSize(), fi.getCreateTimestamp());
        return fbi;
    }

    public Map<String, String> getFileMetaInfo(String fileId) throws IOException, MyException {
        ClientGlobal.init(this.config.getConfigFile());
        Map<String, String> mapMetaInfos = new HashMap();
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;
        StorageClient1 storageClient = new StorageClient1(trackerServer, (StorageServer)storageServer);
        NameValuePair[] nvps = storageClient.get_metadata1(fileId);
        if (nvps != null && nvps.length > 0) {
            NameValuePair[] var8 = nvps;
            int var9 = nvps.length;

            for(int var10 = 0; var10 < var9; ++var10) {
                NameValuePair nvp = var8[var10];
                mapMetaInfos.put(nvp.getName(), nvp.getValue());
            }
        }

        return mapMetaInfos;
    }
}
