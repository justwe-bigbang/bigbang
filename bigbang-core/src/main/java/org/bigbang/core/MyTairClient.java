package org.bigbang.core;

import com.taobao.tair.DataEntry;
import com.taobao.tair.Result;
import com.taobao.tair.ResultCode;
import com.taobao.tair.impl.DefaultTairManager;
import org.bigbang.core.utils.ByteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MyTairClient extends DefaultTairManager {

    //命名空间
    private final static short NaneSpace = 0;
    //超时参数
     private final static int timeout = 3000; //3s 超时

    private final static Logger Logger = LoggerFactory.getLogger(MyTairClient.class);


    public boolean put(String key, Serializable value) {

        ResultCode result = this.put(NaneSpace, ByteUtil.getBytes(key), value);

        if (result.isSuccess() && result.getCode() == 0) {
            return true;
        }
        // request fail;
        Logger.error("Tair Put Failed.");
        return false;
    }


    public Object get(String key) {

        Result<DataEntry> result = this.get(NaneSpace, ByteUtil.getBytes(key));

        if (null !=result&&result.isSuccess() && result.getRc().getCode() == 0) {
            return result.getValue().getValue();
        }
        // request fail;
        Logger.error("Tair Get Failed.");
        return null;
    }


    //
    private static MyTairClient instance;

    //SingleInstance
    public static MyTairClient getInstance() {

        if (null == instance) {
            synchronized (MyTairClient.class) {
                if (null == instance) {
                    instance = new MyTairClient();

                    String master = "173.26.122.19:5198"; //tair master cs address, for example, 10.232.4.14:5008;
                    String slave = null; // tair slave cs address
                    String group = "group_1"; // tair group name

                    List<String> config = new ArrayList<>();
                    config.add(master);

                    instance.setConfigServerList(config);
                    instance.setGroupName(group);
                    instance.setTimeout(timeout);
                    instance.init();

                    return instance;
                }
            }
        }
        return instance;
    }

}
