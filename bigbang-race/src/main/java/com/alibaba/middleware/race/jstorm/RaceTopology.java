package com.alibaba.middleware.race.jstorm;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.Utils;
import com.alibaba.middleware.race.RaceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RaceTopology {

    private static Logger LOG = LoggerFactory.getLogger(RaceTopology.class);


    public static void main(String[] args) throws Exception {

        Config conf = new Config();
        int spout_Parallelism_hint = 1;
        int split_Parallelism_hint = 2;
        int count_Parallelism_hint = 2;

        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("spout", new RaceSentenceSpout(), spout_Parallelism_hint);
        builder.setBolt("split", new SplitSentence(), split_Parallelism_hint).shuffleGrouping("spout");
        builder.setBolt("count", new WordCount(), count_Parallelism_hint).fieldsGrouping("split", new Fields("word"));
        String topologyName = RaceConfig.JstormTopologyName;

        //分布式模式
        /*try {
            StormSubmitter.submitTopology(topologyName, conf, builder.createTopology());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/


        //本地虚拟集群对象
        LocalCluster cluster = new LocalCluster();
        //提交测试 （非阻塞）
        cluster.submitTopology(topologyName, conf, builder.createTopology());
        //运行十秒
        Utils.sleep(10000);
        //杀死进程
        cluster.killTopology(topologyName);


    }
}