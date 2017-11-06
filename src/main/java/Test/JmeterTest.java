package Test;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

/**
 * Created by WIN7 on 2017/11/6.
 * JMeter测试java类（需继承AbstractJavaSamplerClient类，并实现runTest方法；或实现JavaSamplerClient接口）
 */
public class JmeterTest extends AbstractJavaSamplerClient {

    private static final Logger log = LoggingManager.getLoggerForClass();

    /**
     * 开始测试
     * @param javaSamplerContext
     * @return
     */
    @Override
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {
        SampleResult sampleResult = new SampleResult();
        String operator = javaSamplerContext.getParameter("operator");
        Integer timeOut = javaSamplerContext.getIntParameter("timeOut");

        //开始测试
        sampleResult.sampleStart();

        try{
            System.out.println("操作人："+operator);
            Thread.sleep(timeOut);

            sampleResult.setSuccessful(true);
        }catch (Exception e){
            sampleResult.setSuccessful(false);
            log.debug(e.getMessage());
        }finally {
            sampleResult.sampleEnd();//结束测试
        }
        return sampleResult;
    }

    /**
     * 每个线程测试前执行一次，做一些初始化工作
     * @param context
     */
    @Override
    public void setupTest(JavaSamplerContext context) {
        log.debug(this.getClass().getName() + ": setupTest ---> 测试初始化");
    }

    /**
     * 测试结束时调用
     * @param context
     */
    @Override
    public void teardownTest(JavaSamplerContext context) {
        log.debug(this.getClass().getName() + ": teardownTest -- 调用结束");
    }

    /**
     * 设置可用参数及其默认值
     * @return
     */
    @Override
    public Arguments getDefaultParameters() {
        Arguments arguments = new Arguments();
        arguments.addArgument("operator","xiang");
        arguments.addArgument("timeOut","500");
        return arguments;
    }
}
