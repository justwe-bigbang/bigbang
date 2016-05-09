package org.bigbang.core.app;

import com.taobao.tair3.client.Result;
import com.taobao.tair3.client.TairClient;
import com.taobao.tair3.client.error.TairException;
import com.taobao.tair3.client.impl.DefaultTairClient;
import org.bigbang.core.MyTairClient;
import org.bigbang.core.utils.ByteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created by redevelvet on 16/5/9.
 */
public class App {


    private static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws TairException {


        String master = "173.26.122.19:5198"; // tair master cs address, for example, 10.232.4.14:5008;
        String slave = null; // tair slave cs address
        String group = "group_1"; // tair group name
        short ns = 120; //namespace
        //超时时间
        TairClient.TairOption opt = new TairClient.TairOption(500);

        MyTairClient tair = new MyTairClient();
        tair.setMaster(master);
        tair.setSlave(slave);
        tair.setGroup(group);
        tair.init();

        try {
            tair.put(ns,"test", new Man("BBBBBB"),opt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Man g = null;
        try {
              g = tair.get(Man.class,ns, "test");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        logger.debug(String.valueOf(g));

    }

    public static class Man implements Serializable {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public Man(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Man{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
