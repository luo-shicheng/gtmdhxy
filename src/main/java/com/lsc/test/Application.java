package com.lsc.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpATTRS;
import com.lsc.test.config.Config;
import com.lsc.test.config.RedisConfig;
import com.lsc.test.model.PathInfo;
import com.lsc.test.util.HttpUtil;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.apache.catalina.loader.WebappLoader;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;


@SpringBootConfiguration
@EnableAutoConfiguration
@EnableAsync
@ComponentScan(basePackages={"com.lsc.test.rs","com.lsc.test.other","com.lsc.test.handler"})
@ImportAutoConfiguration({Config.class/*, RedisConfig.class*/})
public class Application {

    private static final Logger logger= LoggerFactory.getLogger(Application.class);

    private final Pattern ALL_SRC_PATTERN = Pattern.compile("<(img|video|script).*?src=\"(.*?)\".*?/?>", Pattern.CASE_INSENSITIVE);

    private final Pattern IMAGE_PATTERN = Pattern.compile("(img|video|script).*src=['\"]?([^'\"]*)['\"]?", Pattern.CASE_INSENSITIVE);

    private final List<String> filterTagsInSvg = Arrays.asList("rect", "circle", "ellipse", "line", "polyline", "polygon" /*,"path"*/);

    public static void main(String[] args) throws Exception{
        System.out.println(logger.getClass().getName());
        SpringApplication.run(Application.class, args);

    }

//    @EventListener(WebServerInitializedEvent.class)
    public void sendMessage(WebServerInitializedEvent event){
        RocketMQTemplate rocketMQTemplate = event.getApplicationContext().getBean(RocketMQTemplate.class);
//        Message<String> message = MessageBuilder.withPayload("wdnmd").build();
//        System.out.println(message.getHeaders().get("id"));
//        rocketMQTemplate.send("topic1:tag1", message);
//        rocketMQTemplate.syncSendOrderly("topic1:tag1",message,"wdnfjkd");
//        for (int i = 0; i < 5; i++) {
//            for (int j = 0; j < 4; j++) {
//                for (int k = 0; k < 3; k++) {
//                    Message<String> message = MessageBuilder.withPayload(i + " " + j + " "+k).build();
//                    rocketMQTemplate.sendMessageInTransaction("topic1:tag1",message,1);
//                }
//            }
//        }
        Message<String> message = MessageBuilder.withPayload("transactional message").build();
        for (int i = 0; i < 7; i++) {
            rocketMQTemplate.sendMessageInTransaction("topic1",message,1);
        }
//        for (int i = 0; i < 20; i++) {
//            Message<String> message = MessageBuilder.withPayload("wdnmd"+i).build();
//            System.out.println(message.getHeaders().get("id"));
//            rocketMQTemplate.syncSend("topic1", message);
//        }
    }

//    @EventListener(WebServerInitializedEvent.class)
    @SuppressWarnings("unchecked")
    public void onReady(WebServerInitializedEvent event) throws Exception{
        System.out.println(WebappLoader.class.getClassLoader());
        String html = StreamUtils.copyToString(new FileInputStream("C:\\Users\\DELL\\Desktop\\wechat-20211102105237-11.jsonTreated"), StandardCharsets.UTF_8);
        String[] array = html.split("\n");
        ObjectMapper objectMapper = event.getApplicationContext().getBean(ObjectMapper.class);
        for (String json : array) {
            Map<String, Object> map = objectMapper.readValue(json, Map.class);
            String content = map.getOrDefault("content_html", "").toString();
            String contentHtml = map.getOrDefault("content_html", "").toString();
            String sourceUrl = map.getOrDefault("source_url", "").toString();
            String websiteName = map.getOrDefault("website_name", "").toString();
            String title = map.getOrDefault("title", "").toString();
            Document document = Jsoup.parse(content);
            Elements elements = document.getElementsByAttributeValue("class", "rich_media_content");
            if(CollectionUtils.isEmpty(elements)||elements.size()!=1){
                continue;
            }
            Elements img = elements.get(0).getElementsByTag("img");
            for (Element element : img) {
                String dataSrc = Optional.ofNullable(element.attributes().get("data-src")).map(o -> StringUtils.hasLength(o) ? o : element.attributes().get("src")).orElseThrow(() -> new Exception("77777"));

                System.out.println(dataSrc);
            }

//            List<Element> subElements = elements.get(0).getElementsByTag("img").stream().filter(e -> e.attributes().get("data-src").equals("https://mmbiz.qpic.cn/mmbiz_png/cZV2hRpuAPia7BoxO6EplMAiaCwaVscZylUUEWu2Wwkz9EQOjnOsXicJ3TEbDHB48wfyNfDichicSQSxsgcEfAgtcLg/640?wx_fmt=png")).collect(Collectors.toList());
//            if(CollectionUtils.isEmpty(subElements)){
//                continue;
//            }
//            Element lastElement = subElements.get(subElements.size() - 1);
//            lastElement = this.findAncestor(lastElement);
//            lastElement.remove();
//            System.out.println(elements.get(0).toString());
//            Map<String, Object> map = objectMapper.readValue(json, Map.class);
//            String title = map.getOrDefault("title", "").toString();
//            String contentHtml = map.getOrDefault("content_html", "").toString();
//            if(!"七夕特辑丨“单身狗”自救指南".equals(title)){
//                continue;
//            }
//            List<Element> list =new ArrayList<>();
//            Document document = Jsoup.parse(contentHtml);
//            Elements svgs = document.getElementsByTag("svg");
//            for (Element element : svgs) {
//                String attr = element.attr("opacity");
//                if(StringUtils.hasLength(attr)){
//                    element.attributes().put("opacity","0");
//                }
//            }
//            Map<String, Object> map = objectMapper.readValue(json, Map.class);
            Element element = elements.get(0);
            Elements svg = document.getElementsByTag("svg");
            for (Element svgElement : svg) {
                Elements allElements = svgElement.getAllElements();
                boolean match =StringUtils.hasLength(svgElement.attr("opacity")) &&allElements.stream().anyMatch(this::filter);
                if (match) {
                    System.out.println("wdnmd");
                }
            }
//            System.out.println(element.toString());
//            Elements grandChildren = element.children().get(0).children();
//            int size = grandChildren.size();
//            for (int i = size - 1; i >= size-5; i--) {
//                grandChildren.get(i).remove();
//            }
//            Elements img = element.getElementsByTag("img");
//            for (Element sub : img) {
//                String dataSrc = sub.attributes().get("data-src");
//                Attributes attributes = new Attributes();
//                //使用url的MD5值占位是防止两个url的协议、域名、端口、路径部分相同但请求参数不同的情况详情见，https://mp.weixin.qq.com/s?__biz=MzA3OTE0MjQzMw==&mid=2651892387&idx=1&sn=88d95ee99fd0bb63c094fb8c5b4be823&scene=0
//                String urlTreated = dataSrc.replaceAll("&amp;", "&");
//                attributes.put("src", urlTreated);
//                attributes.put("display", "block");
//                attributes.put("max-width", "-webkit-fill-available");
//                attributes.put("margin", "auto");
//                Element newElement = new Element(Tag.valueOf("img", new ParseSettings(true, true)), "", attributes);
//                this.replaceElement(sub, newElement);
//            }
//            simplifyElement(element,"责任编辑 |",true);
            System.out.println(element.toString());

            Map<String,Object> param =new HashMap<>();
            param.put("url",sourceUrl);
            param.put("source",websiteName);
            param.put("html",element.toString());
            String string = objectMapper.writeValueAsString(param);

            String request = HttpUtil.request(HttpMethod.POST, "http://test-cms.gmdaily.cn/editor/api/transform", RequestBody.create(MediaType.get("application/json"), string), Headers.of());
            if(request!=null){
                System.out.println(request);
                System.out.println("清洗成功   title为 "+title+" 的文章长度为:"+contentHtml.getBytes().length+" bytes"+" 请求体长度为"+ string.getBytes().length+" bytes");
            }else{
                System.out.println("清洗不成功   title为 "+title+" 的文章长度为:"+contentHtml.getBytes().length+" bytes"+" 请求体长度为"+ string.getBytes().length+" bytes");
            }
        }
    }

    private void replaceElement(Element originElement, Element newElement) {
        Element previous = originElement.previousElementSibling();
        if (previous == null) {
            originElement.parent().insertChildren(originElement.elementSiblingIndex(), newElement);
            originElement.remove();
            return;
        }
        Element preBeforePre = previous.previousElementSibling();
        if (preBeforePre == null) {
            previous.parent().insertChildren(originElement.elementSiblingIndex(), newElement);
            originElement.remove();
            return;
        }
        originElement.remove();
        previous.remove();
        newElement.appendTo(preBeforePre);
    }

    private boolean simplifyElement(Element element, String mark, boolean ignoreSelf) {
        Elements children = element.children();
        int size = children.size();
        int index = size;
        Element target = null;
        for (int i = 0; i < size; i++) {
            Element subElement = children.get(i);
            if (subElement.toString().contains(mark)) {
                index = i;
                target = subElement;
                break;
            }
        }
        for (int i = size - 1; i > index; i--) {
            children.get(i).remove();
        }
        if (Objects.isNull(target)) {
            if (size == 0 && !ignoreSelf) {
                element.remove();
            }
        } else {
            this.simplifyElement(target, mark, ignoreSelf);
        }
        return index != size;
    }

    private Element findAncestor(Element lastElement) {
        Element parent = lastElement.parent();
        if (Objects.isNull(parent)) {
            return lastElement;
        }
        String value = parent.attributes().get("data-role");
        if (!"paragraph".equals(value)) {
            return this.findAncestor(parent);
        }
        return parent;
    }

    @SuppressWarnings("unchecked")
    public void getFiles(ApplicationContext applicationContext) throws Exception{
        ChannelSftp sftp = applicationContext.getBean(ChannelSftp.class);
        String currentDate = "2021-08-17";
        String jsons = StreamUtils.copyToString(sftp.get("/upload/wechat/"+currentDate+"/test2.json"), StandardCharsets.UTF_8);
//        String jsons = StreamUtils.copyToString(new FileInputStream("C:\\Users\\DELL\\Desktop\\wechat-20210813165301-1.json"), StandardCharsets.UTF_8);
        ObjectMapper objectMapper = applicationContext.getBean(ObjectMapper.class);
//        String jsonPath = "/upload/wechat/2021-08-11";
//        Vector<ChannelSftp.LsEntry> ls = sftp.ls(jsonPath);
//        for (ChannelSftp.LsEntry entry : ls) {
//            String fileName = entry.getFilename();
//            //是否是json
//            if (!fileName.endsWith(".json")) {
//                continue;
//            }
//            String jsonAbsolutePath = jsonPath + "/" + fileName;
//            String jsons = StreamUtils.copyToString(sftp.get(jsonAbsolutePath), StandardCharsets.UTF_8);
        String[] array = jsons.split("\n");
        for (String json : array) {
            try {
                Map<String, Object> map = objectMapper.readValue(json, Map.class);
                String pictures = map.getOrDefault("picture_path", "").toString();
                String videos = map.getOrDefault("video_path", "").toString();
                String firstPicturePath = map.getOrDefault("first_picture_path", "").toString();
                for (String path : pictures.split("#")) {
                    sftp.get(path,"D:\\test\\home\\data"+path);
                }
                sftp.get(firstPicturePath,"D:\\test\\home\\data"+firstPicturePath);
                for (String path : videos.split("#")) {
                    sftp.get(path,"D:\\test\\home\\data"+path);
                }

            } catch (Exception e) {
                //donothing
            }
        }
    }

    private static void createDir(String filePath, ChannelSftp sftp) throws Exception{

        int i = filePath.lastIndexOf("/");
        if(i==-1) return;
        String parentPath = filePath.substring(0, i);
        getOrCreateDir(parentPath,sftp);
    }

    private static void getOrCreateDir(String parentPath, ChannelSftp sftp) throws Exception{
        String[] split = parentPath.split("/");
        StringBuilder stringBuilder =new StringBuilder();
        boolean createDir = false;
        for (String s : split) {
            if (!StringUtils.hasLength(s)) continue;
            stringBuilder.append("/").append(s);
            String cdir = stringBuilder.toString();
            if(createDir){
                sftp.mkdir(cdir);
                continue;
            }
            if(!dirExists(cdir, sftp).isExist()){
                createDir=true;
                sftp.mkdir(cdir);
            }
        }
    }

    private static PathInfo dirExists(String path, ChannelSftp sftp){
        boolean isDir;
        try{
            SftpATTRS lstat = sftp.lstat(path);
            isDir = lstat.isDir();
        }catch (Exception e){
            return new PathInfo();
        }
        return new PathInfo(true,isDir);
    }

    private boolean filter(Element svgElement) {
        return this.filterTagsInSvg.contains(svgElement.tagName());
    }

}
