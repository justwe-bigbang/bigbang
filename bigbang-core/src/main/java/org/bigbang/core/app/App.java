package org.bigbang.core.app;

import org.bigbang.core.MyTairClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created by redevelvet on 16/5/10.
 */
public class App {


    private static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        MyTairClient tair = MyTairClient.getInstance();

        /*tair.put("test",new Man("rrrrrrr"));*/

        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Object g = tair.get("test");
                    logger.info(g.toString());
                }
            }).start();
        }
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
