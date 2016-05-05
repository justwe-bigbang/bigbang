package bigbang.tair;

import java.util.UUID;

import org.junit.Test;

import com.taobao.tair3.client.Result;
import com.taobao.tair3.client.Result.ResultCode;
import com.taobao.tair3.client.TairClient.TairOption;
import com.taobao.tair3.client.error.TairFlowLimit;
import com.taobao.tair3.client.error.TairRpcError;
import com.taobao.tair3.client.error.TairTimeout;
import com.taobao.tair3.client.util.ByteArray;

import static org.junit.Assert.assertEquals;

/**
 * Created by bysocket on 16/5/5.
 */
public class PutGetTest extends TestBase {
    public TairOption opt = new TairOption(50000000, (short) 0, 0);

    @Test
    public void simpleGet() {
        ns = 120;
        for (int i = 0; i < 10; ++i) {
            byte[] key = UUID.randomUUID().toString().getBytes();
            byte[] val = UUID.randomUUID().toString().getBytes();
            try {
                System.out.println(key);
                System.out.println(val);
                Result<Void> r = tair.put(ns, key, val, opt);
                assertEquals(ResultCode.OK, r.getCode());

                Result<byte[]> g = tair.get(ns, key, opt);
                System.out.println(new ByteArray(g.getResult()));
                assertEquals(ResultCode.OK, g.getCode());

                //assertEquals(new ByteArray(key), new ByteArray(g.getKey()));
                //assertEquals(new ByteArray(val), new ByteArray(g.getResult()));
                //assertEquals(1, g.getVersion());
                // assertEquals(0, g.getFlag());

                //System.out.println("DONE = " + i);
            } catch (TairRpcError e) {
                //assertEquals(false, true);
                e.printStackTrace();
            } catch (TairFlowLimit e) {
                //assertEquals(false, true);
                e.printStackTrace();
            } catch (TairTimeout e) {
                //assertEquals(false, true);
                e.printStackTrace();
            } catch (InterruptedException e) {
                //assertEquals(false, true);
                e.printStackTrace();
            }
        }
    }
}
