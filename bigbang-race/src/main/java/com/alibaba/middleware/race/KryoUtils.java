package com.alibaba.middleware.race;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.Serializable;

/**
 * @Title KryoUtils.java
 * @Description 基于kryo 实现序列化和反序列化.
 * @Author yzh yingzh@getui.com
 * @Date 05.22.2016
 */
public class KryoUtils {

    /**
     * Serialize byte [ ]. 序列化
     *
     * @param object the object
     * @return the byte [ ]
     */
    public static byte[] serialize(Object object) {
        Output output = new Output(1024);
        Kryo kryo = new Kryo();
        kryo.writeObject(output, object);
        output.flush();
        output.close();
        byte [] ret = output.toBytes();
        output.clear();
        return ret;
    }

    /**
     * Deserialize t. 反序列化
     *
     * @param <T>    the type parameter
     * @param tClass the t class
     * @param bytes  the bytes
     * @return the t
     */
    public static <T> T deserialize(Class<T> tClass, byte[] bytes) {
        Kryo kryo = new Kryo();
        Input input = new Input(bytes);
        input.close();
        T ret = kryo.readObject(input, tClass);
        return ret;
    }


    public static void main(String[] args) {
        Man man=new Man(new Info("Redvelvet"));
        byte[] r= KryoUtils.serialize(man);
        System.out.println(r.length);

        Man re=KryoUtils.deserialize(Man.class,r);

        System.out.println(re.getInfo().getName());

    }

    public static class Man {
        private Info info;

        public Info getInfo() {
            return info;
        }

        public void setInfo(Info info) {
            this.info = info;
        }
        public Man(Info info){
            this.info=info;
        }
        public Man(){}
    }
    public static class Info {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
        public Info(String name){
            this.name=name;
        }
        public Info(){}
    }

}
