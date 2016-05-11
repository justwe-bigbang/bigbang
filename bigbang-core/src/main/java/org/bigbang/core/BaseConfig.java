package org.bigbang.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * @Title BaseConfig.java
 * @Description TODO(用一句话描述该文件做什么)
 * @Author yzh yingzh@getui.com
 * @Date 04.30.2016
 */
public class BaseConfig implements IConfig {

	private static Logger logger = LoggerFactory.getLogger(BaseConfig.class);

    //用户工作目录
    private Properties properties;
    private String userdir;
    private static BaseConfig instance = new BaseConfig();

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static BaseConfig getInstance() {
        return instance;
    }

    private BaseConfig() {
        this.userdir = System.getProperty("user.dir");
        load();
    }
  
    private void load() {
        FileInputStream fis = null;
        try {
            File configfile = getConfigFile();
            fis = new FileInputStream(configfile);
            properties = new Properties();
            properties.load(fis);
        } catch (Exception e) {
        	logger.warn("BaseConfig load error : {}",e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                }
            }
            if (properties==null) {
            	properties=new Properties();
            	logger.warn("BaseConfig load error : properties is　null");
			}
        }
    }
    
    @Override
	public String getHomeDir() {
		return userdir;
	}
    
    @Override
    public String getConfigDir() {
        return getHomeDir() + File.separator + "config";
    }

    private File getConfigFile() {
        return new File(getConfigDir() + File.separator + "cf.properties");
    }


    /**
     * Gets property.
     *
     * @param name         the name
     * @param defaultValue the default value
     * @return the property
     */
    public String getProperty(String name, String defaultValue) {
        return properties.getProperty(name, defaultValue);
    }

    /**
     * Gets property.
     *
     * @param name         the name
     * @param defaultValue the default value
     * @return the property
     */
    public Boolean getProperty(String name, Boolean defaultValue) {
        String value = properties.getProperty(name, String.valueOf(defaultValue));
        if (value != null) {
            return Boolean.parseBoolean(value);
        }
        return null;
    }

    /**
     * Gets property.
     *
     * @param name         the name
     * @param defaultValue the default value
     * @return the property
     */
    public Integer getProperty(String name, Integer defaultValue) {
        String value = properties.getProperty(name, String.valueOf(defaultValue));
        if (value != null) {
            return Integer.parseInt(value);
        }
        return null;
    }

    /**
     * Gets property.
     *
     * @param name         the name
     * @param defaultValue the default value
     * @return the property
     */
    public Long getProperty(String name, Long defaultValue) {
        String value = properties.getProperty(name, String.valueOf(defaultValue));
        if (value != null) {
            return Long.parseLong(value);
        }
        return null;
    }

    /**
     * Gets property.
     *
     * @param key the key
     * @return the property
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Gets property.
     *
     * @param key          the key
     * @param defaultValue the default value
     * @return the property
     */
    public float getProperty(String key, Float defaultValue) {
        String value = properties.getProperty(key, String.valueOf(defaultValue));
        if (value != null) {
            return Float.parseFloat(value);
        }
        return 0.0f;
    }
}