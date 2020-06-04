package net.wwang.blog.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertiesUtil {
    private static final Logger logger = Logger.getLogger(String.valueOf(PropertiesUtil.class));
    private static final String DEFAULT_PROPERTIES = "application.properties";

    /**
     * 获取properties属性值
     *
     * @param str
     * @return
     */
    public static String getProp(String str) {
        try {
            Resource resource = new ClassPathResource(DEFAULT_PROPERTIES);
            Properties props = PropertiesLoaderUtils.loadProperties(resource);
            return props.getProperty(str);
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        return null;
    }
}
