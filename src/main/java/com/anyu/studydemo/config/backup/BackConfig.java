package com.anyu.studydemo.config.backup;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 文件备份信息
 *
 * @author Anyu
 * @since 2021/2/3 下午3:59
 */
@EnableConfigurationProperties(BackupProperties.class)
@Configuration
public class BackConfig {
}
