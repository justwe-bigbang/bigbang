package bigbang.jstorm;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

/**
 * Created by redevelvet on 16/5/18.
 */
public class SimpleBolt extends BaseBasicBolt {

    public void execute(Tuple input, BasicOutputCollector collector) {
        try {
            String msg = input.getString(0);
            if (msg != null){
                //System.out.println("msg="+msg);
                collector.emit(new Values(msg + "msg is processed!"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("info"));
    }

}