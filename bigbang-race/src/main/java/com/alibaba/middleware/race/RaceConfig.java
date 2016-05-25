package com.alibaba.middleware.race;

import java.io.Serializable;

public class RaceConfig implements Serializable {

    //??Щ??дtair key????
    public static String prex_tmall = "platformTmall_";
    public static String prex_taobao = "platformTaobao_";
    public static String prex_ratio = "ratio_";


    //??Щjstorm/rocketMq/tair ???????????????Щ?????????????????????????
    public static String JstormTopologyName = "xxx";
    public static String MetaConsumerGroup = "xxx";
    public static String MqPayTopic = "MiddlewareRaceTestData_Pay";
    public static String MqTmallTradeTopic = "MiddlewareRaceTestData_TMOrder";
    public static String MqTaobaoTradeTopic = "MiddlewareRaceTestData_TBOrder";
    public static String TairConfigServer = "xxx";
    public static String TairSalveConfigServer = "xxx";
    public static String TairGroup = "xxx";
    public static Integer TairNamespace = 1;
}
