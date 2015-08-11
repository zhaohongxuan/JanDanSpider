package jandan;

import com.zeusjava.jandan.processor.PicProcessor;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.PriorityScheduler;

/**
 * Created by LittleXuan on 2015/8/10.
 */
public class JanDanSpiderTest {
    private static Logger logger = Logger.getLogger(JanDanSpiderTest.class);

    public static void main(String[] args) {
        PropertyConfigurator.configure(ClassLoader.getSystemResourceAsStream("log4j.properties"));
        Spider.create(new PicProcessor()).scheduler(new PriorityScheduler()).run();
    }
}
