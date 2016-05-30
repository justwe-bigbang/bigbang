package com.alibaba.middleware.race;

import java.io.Serializable;

public class RaceConfig implements Serializable {

    //这些是写tair key的前缀
    public final static String prex_tmall = "platformTmall_";
    public final static String prex_taobao = "platformTaobao_";
    public final static String prex_ratio = "ratio_";


    //这些jstorm/rocketMq/tair 的集群配置信息，这些配置信息在正式提交代码前会被公布
    public final static String JstormTopologyName = "bg-Topology";
    public final static String MetaConsumerGroup = "xxx";
    public final static String MqPayTopic = "MiddlewareRaceTestData_Pay";
    public final static String MqTmallTradeTopic = "MiddlewareRaceTestData_TMOrder";
    public final static String MqTaobaoTradeTopic = "MiddlewareRaceTestData_TBOrder";
    public final static String TairConfigServer = "173.26.122.19:5198";
    public final static String TairSalveConfigServer = "173.26.122.19:5198";
    public final static String TairGroup = "group_1";
    public final static Integer TairNamespace = 1;

    public static class RocketMQ{
        public final static String NamesrvAddr = "173.26.122.19:9876";
        public final static String ProducerGroup = "ProducerGroupName";
    }


}
