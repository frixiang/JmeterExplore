package Test;
import com.google.common.base.Objects;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by WIN7 on 2017/11/7.
 *
 * @author Norris
 * @since  concurrentHashMap的并发测试
 */
public class ConcurrentHashMapTest implements JavaSamplerClient {

    private static Logger log = LoggerFactory.getLogger(ConcurrentHashMapTest.class);

    private static final ConcurrentHashMap<String,Object> lockMap = new ConcurrentHashMap<String, Object>();

    @Override
    public void setupTest(JavaSamplerContext javaSamplerContext) {
        log.debug("setUpTest");
    }

    @Override
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {

        SampleResult sampleResult = new SampleResult();
        sampleResult.setSuccessful(true);

        Map<String, Object> params = new HashMap<String, Object>(16);
        /**
         * 获取参数名称
         */
        Iterator<String> iterator = javaSamplerContext.getParameterNamesIterator();

        /**
         * 遍历获取传参
         */
        while (iterator.hasNext()){
            String str = iterator.next();
            params.put(str,javaSamplerContext.getParameter(str));
        }

        log.debug(params.get("orderId") + "----" + params.get("operator"));

        if (params.get("orderId").equals(lockMap.putIfAbsent(String.valueOf(params.get("orderId")),params.get("orderId")))) {
            log.debug("orderId is deal now,please wait!");
            sampleResult.setSuccessful(false);
        }
        return sampleResult;
    }

    @Override
    public void teardownTest(JavaSamplerContext javaSamplerContext) {
        log.debug("Test down");
    }

    @Override
    public Arguments getDefaultParameters() {
        log.debug("get Default Params");
        Arguments arguments = new Arguments();
        arguments.setProperty("operator","Norris");
        arguments.setProperty("orderId",123456);
        return arguments;
    }
}
