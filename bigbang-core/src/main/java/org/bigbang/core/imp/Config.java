package org.bigbang.core.imp;


import org.bigbang.core.BaseConfig;
import java.util.Arrays;
import java.util.List;

public abstract class Config{

    private static BaseConfig CONFIG = BaseConfig.getInstance();

    public static List<String>  ConfigServerList;
    public static String Group;
    public static int TimeOut;
    public static int NameSpace;

    static {

        ConfigServerList=Arrays.asList(CONFIG.getProperty("tair.servers").trim().split(","));
        Group=CONFIG.getProperty("tair.group").trim();
        TimeOut=CONFIG.getProperty("tair.timeout",3000);
        NameSpace=CONFIG.getProperty("tair.namespace",0);
    }

}
