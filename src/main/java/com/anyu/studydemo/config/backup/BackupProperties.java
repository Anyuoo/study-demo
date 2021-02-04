package com.anyu.studydemo.config.backup;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 文件备份信息所需属性
 *
 * @author Anyu
 * @since 2021/2/3 下午4:00
 */
@ConfigurationProperties(prefix = "app.backup")
//@Component
public class BackupProperties {
    private String path = "./src/main/resources/";
    private String fileName = "weathers_backup_data";
    private String fileSuffix = "txt";

    public String getDefaultFullPath() {
        return path + fileName + "." + fileSuffix;
    }

    public String getFullPath(String fileName) {
        return path + fileName + "." + fileSuffix;
    }

    public  String getFullFileName(String fileName) {
        return fileName + "." + fileSuffix;
    }
    public  String getDefaultFullFileName() {
        return fileName + "." + fileSuffix;
    }
    public String getPath() {
        return path;
    }

    public BackupProperties setPath(String path) {
        this.path = path;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public BackupProperties setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public BackupProperties setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
        return this;
    }
}
