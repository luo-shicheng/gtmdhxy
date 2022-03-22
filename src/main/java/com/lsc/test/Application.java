package com.lsc.test;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.lsc.test.config.Config;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.*;
import java.util.regex.Pattern;


@SpringBootConfiguration
@EnableAutoConfiguration
@EnableAsync
@ComponentScan(basePackages={"com.lsc.test.rs","com.lsc.test.other","com.lsc.test.handler","com.lsc.test.service","com.lsc.test.aspect"})
@ImportAutoConfiguration({Config.class/*, RedisConfig.class*/})
//@EnableAspectJAutoProxy
@MapperScan("com.lsc.test.mapper")
public class Application {

    private static final Logger logger= LoggerFactory.getLogger(Application.class);

    private final Pattern ALL_SRC_PATTERN = Pattern.compile("<(img|video|script).*?src=\"(.*?)\".*?/?>", Pattern.CASE_INSENSITIVE);

    private final Pattern IMAGE_PATTERN = Pattern.compile("(img|video|script).*src=['\"]?([^'\"]*)['\"]?", Pattern.CASE_INSENSITIVE);

    private final List<String> filterTagsInSvg = Arrays.asList("rect", "circle", "ellipse", "line", "polyline", "polygon" /*,"path"*/);

    private final String signal = "Num10";

    public static void main(String[] args) throws Exception{
        initMybatis();
        System.out.println(logger.getClass().getName());
        SpringApplication application = new SpringApplication(Application.class);
//        application.addListeners((event)->{
//            //解决@Async循环依赖出错的问题
//            if(event instanceof ApplicationPreparedEvent){
//                ConfigurableApplicationContext applicationContext = ((ApplicationPreparedEvent) event).getApplicationContext();
//                DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getBeanFactory();
//                beanFactory.setAllowRawInjectionDespiteWrapping(true);
//            }
//        });
        application.run(args);

    }

    public static void initMybatis(){
        FastAutoGenerator.create("jdbc:mysql://rm-bp14r1ey9m7f6tg92fo.mysql.rds.aliyuncs.com:3306/test?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC", "lsc", "Lscqet159753")
                .globalConfig(builder -> {
                    builder.outputDir(System.getProperty("user.dir")+"/src/main/java");
                })
                .packageConfig(builder -> {
                    builder.parent("com.lsc.test")// 设置父包名
                            .entity("model")
                            .service("service")
                            .controller("rs")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir")+"/src/main/resources/static")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("test","test3"); // 设置需要生成的表名
                })
                .execute();
    }

    @EventListener(WebServerInitializedEvent.class)
    public void sendMessage(WebServerInitializedEvent event){
        RocketMQTemplate rocketMQTemplate = event.getApplicationContext().getBean(RocketMQTemplate.class);
        MessageBuilder<String> wdnmd = MessageBuilder.withPayload("wdnmd");
        Message<String> message = wdnmd.build();
        rocketMQTemplate.send("topic1:tag1", message);

        rocketMQTemplate.syncSendOrderly("topic1:tag1",message,"1");
        rocketMQTemplate.syncSend("topic1:tag1",message);
        wdnmd.setHeader("myId","7777777777777777777");
        message = wdnmd.build();
        rocketMQTemplate.syncSend("topic1:tag1",message);
        rocketMQTemplate.syncSendOrderly("topic1:tag1", message, "1");
//        for (int i = 0; i < 5; i++) {
//            for (int j = 0; j < 4; j++) {
//                for (int k = 0; k < 3; k++) {
//                    Message<String> message = MessageBuilder.withPayload(i + " " + j + " "+k).build();
//                    rocketMQTemplate.sendMessageInTransaction("topic1:tag1",message,1);
//                }
//            }
//        }
//        Message<String> message = MessageBuilder.withPayload("transactional message").build();
//        for (int i = 0; i < 7; i++) {
//            rocketMQTemplate.sendMessageInTransaction("topic1",message,1);
//        }
//        for (int i = 0; i < 1; i++) {
//            Message<String> message = MessageBuilder.withPayload("wdnmd"+i+signal).build();
//            SendResult sendResult = rocketMQTemplate.syncSend("topic1:tag1"/*+*(i%2==0?1:2)**/, message);
//            System.out.println(sendResult);
//        }
    }

}
