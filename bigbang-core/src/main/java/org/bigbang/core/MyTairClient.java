package org.bigbang.core;


import com.taobao.tair3.client.Result;
import com.taobao.tair3.client.error.TairFlowLimit;
import com.taobao.tair3.client.error.TairRpcError;
import com.taobao.tair3.client.error.TairTimeout;
import com.taobao.tair3.client.impl.DefaultTairClient;
import org.bigbang.core.app.App;
import org.bigbang.core.utils.ByteUtil;
import org.bigbang.core.utils.SerializationUtil;

import java.io.Serializable;


/**
 * @Title MyTairClient.java
 * @Description TODO(用一句话描述该文件做什么)
 * @Author yzh yingzh@getui.com
 * @Date 05.09.2016
 */
public class MyTairClient extends DefaultTairClient {


    /**
     * Get result.
     *
     *
     * @param t
     * @param ns  the ns
     * @param key the key
     * @return the result
     * @throws TairRpcError         the tair rpc error
     * @throws TairFlowLimit        the tair flow limit
     * @throws TairTimeout          the tair timeout
     * @throws InterruptedException the interrupted exception
     */
    public <T> T get(Class<T> t,short ns, String key) throws TairRpcError, TairFlowLimit, TairTimeout, InterruptedException {

        Result<byte[]> result= this.get(ns, ByteUtil.getBytes(key),null);

        return SerializationUtil.deserialize(result.getResult(),t);
    }

    /**
     * Put result.
     *
     * @param ns    the ns
     * @param key   the key
     * @param value the value
     * @param opt   the opt
     * @return the result
     * @throws TairRpcError         the tair rpc error
     * @throws TairFlowLimit        the tair flow limit
     * @throws TairTimeout          the tair timeout
     * @throws InterruptedException the interrupted exception
     */
    public Result<Void> put(short ns, String key,Object value, TairOption opt)
            throws TairRpcError, TairFlowLimit, TairTimeout, InterruptedException {
        return this.put(ns,ByteUtil.getBytes(key),SerializationUtil.serialize(value),opt);
    }

}
