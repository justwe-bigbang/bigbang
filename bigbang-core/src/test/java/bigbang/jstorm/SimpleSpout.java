package bigbang.jstorm;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.util.Map;
import java.util.Random;

/**
 * Created by redevelvet on 16/5/18.
 */
public class SimpleSpout extends BaseRichSpout {

    private SpoutOutputCollector collector;
    private static String[] info = new String[]{
            "comaple\t,12424,44w46,654,12424,44w46,654,",
            "lisi\t,435435,6537,12424,44w46,654,",
            "lipeng\t,45735,6757,12424,44w46,654,",
            "hujintao\t,45735,6757,12424,44w46,654,",
            "jiangmin\t,23545,6457,2455,7576,qr44453",
            "beijing\t,435435,6537,12424,44w46,654,",
            "xiaoming\t,46654,8579,w3675,85877,077998,",
            "xiaozhang\t,9789,788,97978,656,345235,09889,",
            "ceo\t,46654,8579,w3675,85877,077998,",
            "cto\t,46654,8579,w3675,85877,077998,",
            "zhansan\t,46654,8579,w3675,85877,077998,"};

    Random random=new Random();


    /**
     * 定义spout发送数据，每个字段的含义
     * @param outputFieldsDeclarer
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("source")); //collector.emit(new Values(msg));参数要对应
    }

    /**
     * open是当task起来后执行的初始化动作
     * @param map
     * @param topologyContext
     * @param spoutOutputCollector
     */
    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector=spoutOutputCollector;
    }

    /**
     * nextTuple 是spout实现核心，
     * nextuple完成自己的逻辑，即每一次取消息后，
     * 用collector 将消息emit出去。
     */
    @Override
    public void nextTuple() {
        try {
            String msg = info[random.nextInt(11)];
            // 调用发射方法
            collector.emit(new Values(msg));
            // 模拟等待100ms
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
