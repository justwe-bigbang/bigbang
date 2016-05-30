package org.bigbang.core;

import com.taobao.tair.DataEntry;
import com.taobao.tair.Result;
import com.taobao.tair.ResultCode;
import com.taobao.tair.impl.DefaultTairManager;
import org.bigbang.core.imp.Config;
import org.bigbang.core.utils.ByteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;


public class MyTairClient extends DefaultTairManager {

    //命名空间
    private final static short NaneSpace = (short) (Config.NameSpace);

    private final static Logger Logger = LoggerFactory.getLogger(MyTairClient.class);


    public boolean put(String key, Serializable value) {

        ResultCode result = this.put(NaneSpace, ByteUtil.getBytes(key), value);

        if (result.isSuccess() && result.getCode() == 0) {
            return true;
        }
        // request fail;
        Logger.error("tair Put Failed.");
        return false;
    }


    public Object get(String key) {

        Result<DataEntry> result = this.get(NaneSpace, ByteUtil.getBytes(key));

        if (result == null)
            return null;

        if (result.isSuccess() && result.getRc().getCode() == 0) {
            return result.getValue().getValue();
        }
        // request fail;
        Logger.error("tair Get Failed.");
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
                    instance.setConfigServerList(Config.ConfigServerList);
                    instance.setGroupName(Config.Group);
                    instance.setTimeout(Config.TimeOut);
                    instance.init();

                    return instance;
                }
            }
        }
        return instance;
    }

}
