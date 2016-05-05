package bigbang.tair;

import com.taobao.tair3.client.TairClient;
import com.taobao.tair3.client.impl.DefaultTairClient;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by bysocket on 16/5/5.
 */
public class TestBase {

    protected String master = "173.26.122.19:5198"; // tair master cs address, for example, 10.232.4.14:5008;
    protected String slave = null; // tair slave cs address
    protected String group = "group_1"; // tair group name
    protected DefaultTairClient tair = null;
    protected short ns = 120; //namespace
    protected TairClient.TairOption opt = new TairClient.TairOption(500/*timeout*/);

    @Before
    public void setUp() throws Exception {
        tair = new DefaultTairClient();
        tair.setMaster(master);
        tair.setSlave(slave);
        tair.setGroup(group);
        tair.init();
    }

    @After
    public void tearDown() throws Exception {
        tair.close();
    }

}
