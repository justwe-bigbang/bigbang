package com.alibaba.middleware.race.Tair;

import com.alibaba.middleware.race.RaceConfig;
import java.io.Serializable;


/**
 * ��дtair����Ҫ�ļ�Ⱥ��Ϣ����masterConfigServer/slaveConfigServer��ַ/
 * group ��namespace���Ƕ�������ʽ�ύ����ǰ��֪ѡ��
 */
public class TairOperatorImpl {

    public TairOperatorImpl(String masterConfigServer,
                            String slaveConfigServer,
                            String groupName,
                            int namespace) {
    }

    public boolean write(Serializable key, Serializable value) {
        return false;
    }

    public Object get(Serializable key) {
        return null;
    }

    public boolean remove(Serializable key) {
        return false;
    }

    public void close(){
    }

    //��è�ķ��ӽ��׶�д��tair
    public static void main(String [] args) throws Exception {
        TairOperatorImpl tairOperator = new TairOperatorImpl(RaceConfig.TairConfigServer, RaceConfig.TairSalveConfigServer,
                RaceConfig.TairGroup, RaceConfig.TairNamespace);
        //�������Ǹ���ʱ��
        Long millisTime = System.currentTimeMillis();
        //��������ʱ�����10λ����������Ҫת��������ʱ���
        Long minuteTime = (millisTime / 1000 / 60) * 60;
        //������һ���ӵĽ��׶���100;
        Double money = 100.0;
        //д��tair
        tairOperator.write(RaceConfig.prex_tmall + minuteTime, money);
    }
}
