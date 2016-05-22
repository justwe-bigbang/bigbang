package bigbang.jstorm;

import backtype.storm.topology.TopologyBuilder;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.utils.Utils;

/**
 *
 */
public class SimpleTopology {
    public static void main(String[] args) {
        try {
            // 实例化TopologyBuilder类。
            TopologyBuilder topologyBuilder = new TopologyBuilder();
            // 设置 Spout 唯一标识ID
            // 设置喷发节点并分配并发数，该并发数将会控制该对象在集群中的线程数。
            topologyBuilder.setSpout("SimpleSpout", new SimpleSpout(), 10);
            // 设置数据处理节点并分配并发数。t
            // 指定该节点接收喷发节点的策略为随机方式。?
            topologyBuilder.setBolt("SimpleBolt", new SimpleBolt(), 1)
                           .shuffleGrouping("SimpleSpout");

            //创建配置文件(结构为hashmap)
            Config config = new Config();
            config.setDebug(true);

            //分布式运行
            if (args != null && args.length > 0) {
                config.setNumWorkers(1);
                StormSubmitter.submitTopology(args[0], config, topologyBuilder.createTopology());
            } else {
                // 这里是本地模式下运行的启动代码。
                // 设置最大的任务并行数
                config.setMaxTaskParallelism(1);

                //本地虚拟集群对象
                LocalCluster cluster = new LocalCluster();
                //提交测试 （非阻塞）
                cluster.submitTopology("simpleTopology", config, topologyBuilder.createTopology());

                //运行十秒
                Utils.sleep(10000);
                //杀死进程
                cluster.killTopology("simpleTopology");
            }







        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}