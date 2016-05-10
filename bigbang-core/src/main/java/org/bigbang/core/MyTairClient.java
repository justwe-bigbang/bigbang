package org.bigbang.core;


import com.taobao.tair3.client.Result;
import com.taobao.tair3.client.error.TairFlowLimit;
import com.taobao.tair3.client.error.TairRpcError;
import com.taobao.tair3.client.error.TairTimeout;
import com.taobao.tair3.client.impl.DefaultTairClient;
import org.bigbang.core.utils.ByteUtil;
import org.bigbang.core.utils.SerializationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Title MyTairClient.java
 * @Description TODO(用一句话描述该文件做什么)
 * @Author yzh yingzh@getui.com
 * @Date 05.09.2016
 */
public class MyTairClient extends DefaultTairClient {

    //命名空间
    private final static short NaneSpace=0;
    //默认参数
    private final static TairOption Opt=new TairOption(3000); //3s 超时

    private final static Logger Logger= LoggerFactory.getLogger(MyTairClient.class);

    /**
     * Get t.
     *
     * @param <T> the type parameter
     * @param t   the t
     * @param key the key
     * @return the t
     * @throws TairRpcError         the tair rpc error
     * @throws TairFlowLimit        the tair flow limit
     * @throws TairTimeout          the tair timeout
     * @throws InterruptedException the interrupted exception
     */
    public <T> T get(Class<T> t,String key) throws TairRpcError, TairFlowLimit, TairTimeout, InterruptedException {

        Result<byte[]> result= this.get(NaneSpace, ByteUtil.getBytes(key),Opt);

        if (result.isSuccess() && result.getCode().errno()==0) {
          return  SerializationUtil.deserialize(result.getResult(),t);
        }
        // request fail;
        Logger.error("Tair Get Failed.");
        return null;
    }


    /**
     * Put result.
     *
     * @param ns    the ns
     * @param key   the key
     * @param value the value
     * @return the result
     * @throws TairRpcError         the tair rpc error
     * @throws TairFlowLimit        the tair flow limit
     * @throws TairTimeout          the tair timeout
     * @throws InterruptedException the interrupted exception
     */
    public boolean put(String key,Object value)
            throws TairRpcError, TairFlowLimit, TairTimeout, InterruptedException {

        Result<Void> result =this.put(NaneSpace,ByteUtil.getBytes(key),SerializationUtil.serialize(value),Opt);

        if (result.isSuccess() && result.getCode().errno()==0) {
            return true;
        }
        // request fail;
        Logger.error("Tair Put Failed.");
        return false;
    }

}
