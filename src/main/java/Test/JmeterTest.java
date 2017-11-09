package Test;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;


/**
 * Created by WIN7 on 2017/11/6.
 * JMeter测试java类（需继承AbstractJavaSamplerClient类，并实现runTest方法；或实现JavaSamplerClient接口）
 */
public class JmeterTest extends AbstractJavaSamplerClient {

    /**
     *
     * 注：必须重写getLogger方法才能打印出日志
     * @return
     */
    @Override
    public org.apache.log.Logger getLogger()
    {
        return super.getLogger();
    }

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
            this.getLogger().debug("--------操作人："+operator+"--------");
            Thread.sleep(timeOut);

            sampleResult.setSuccessful(true);
        }catch (Exception e){
            sampleResult.setSuccessful(false);
            this.getLogger().debug(e.getMessage());
        }finally {
            sampleResult.sampleEnd();//结束测试
            sampleResult.setResponseMessage("调用成功");
            sampleResult.setResponseCode("OK");
        }
        return sampleResult;
    }

    /**
     * 每个线程测试前执行一次，做一些初始化工作
     * @param context
     */
    @Override
    public void setupTest(JavaSamplerContext context) {
        this.getLogger().debug("--------测试前的初始化工作--------");
        this.getLogger().debug(this.getClass().getName() + ": setupTest");
    }

    /**
     * 测试结束时调用
     * @param context
     */
    @Override
    public void teardownTest(JavaSamplerContext context) {
        this.getLogger().debug("--------测试结束--------");
        this.getLogger().debug(this.getClass().getName() + ": teardownTest");
    }

    /**
     * 设置可用参数及其默认值
     * @return
     */
    @Override
    public Arguments getDefaultParameters() {
        Arguments arguments = new Arguments();
        this.getLogger().debug("--------设置请求参数--------");
        arguments.addArgument("operator","xiang");
        arguments.addArgument("timeOut","500");
        return arguments;
    }
}
