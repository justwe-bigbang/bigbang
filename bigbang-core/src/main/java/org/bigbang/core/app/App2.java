package org.bigbang.core.app;

import com.taobao.tair3.client.Result;
import com.taobao.tair3.client.TairClient;
import com.taobao.tair3.client.error.TairException;
import com.taobao.tair3.client.error.TairFlowLimit;
import com.taobao.tair3.client.error.TairRpcError;
import com.taobao.tair3.client.error.TairTimeout;
import com.taobao.tair3.client.impl.DefaultTairClient;
import org.bigbang.core.MyTairClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by redevelvet on 16/5/10.
 */
public class App2 {


    private static Logger logger = LoggerFactory.getLogger(App2.class);

    public static void main(String[] args) throws TairException {

        String master = "173.26.122.19:5198"; // tair master cs address, for example, 10.232.4.14:5008;
        String slave = null; // tair slave cs address
        String group = "group_1"; // tair group name
        short ns = 0; //namespace
        //超时时间
        TairClient.TairOption opt = new TairClient.TairOption(500);

        DefaultTairClient tair = new DefaultTairClient();
        tair.setMaster(master);
        tair.setSlave(slave);
        tair.setGroup(group);
        tair.init();


        for (int i = 0; i < 10; i++) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Result<byte[]> g = tair.get(ns, "test".getBytes(), opt);

                        logger.debug(g.toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (TairRpcError tairRpcError) {
                        tairRpcError.printStackTrace();
                    } catch (TairFlowLimit tairFlowLimit) {
                        tairFlowLimit.printStackTrace();
                    } catch (TairTimeout tairTimeout) {
                        tairTimeout.printStackTrace();
                    }

                }
            }).start();
        }
    }
}
