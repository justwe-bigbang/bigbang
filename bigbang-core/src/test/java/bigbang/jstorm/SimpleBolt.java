package bigbang.jstorm;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;


/**
 * @Title SimpleBolt.java
 * @Description
 * @Author yzh yingzh@getui.com
 * @Date 05.22.2016
 */
public class SimpleBolt extends BaseBasicBolt {

    /**
     * 获取 元组数据.处理后 再次发射 至拓扑的下一个 bolt
     * @param input 元组数据
     * @param collector 理解为 rpc的代理
     */
    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        try {

            String msg = input.getString(0);
            if (msg != null){
                System.out.println(input.getFields());
                System.out.println("msg="+msg);
                //接受到的数据,经过处理后 喷射 出去.
                collector.emit(new Values(msg + "msg is processed!"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 申明该bolt 发射的元组 字段名称
     * @param declarer
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("info"));
    }

}