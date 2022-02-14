package com.lsc.test.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HtmlUtil {

    private final static Pattern IMAGE_SRC_PATTERN = Pattern.compile("<(img|video|script).*?src=\"(.*?)\".*?/?>", Pattern.CASE_INSENSITIVE);

    private final static Pattern IMAGE_PATTERN = Pattern.compile("(script|img|video).*src=['\"]?([^'\"]*)['\"]?", Pattern.CASE_INSENSITIVE);

    private final static Pattern SEPERATOR = Pattern.compile("\\[(?<=\\[)[^\\]]+]", Pattern.CASE_INSENSITIVE);

    private final static String content = "";

    public static final Pattern URLS = Pattern.compile("(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]");

    public static void main(String[] args) {
//        System.out.println(content);
//        System.out.println("---------------------------");
//        Document document = Jsoup.parse(content);
//        Matcher matcher = URLS.matcher(content);
//        Set<String> list = new HashSet<>();
//        while (matcher.find()) {
//            String group = matcher.group();
//            System.out.println(group);
//            list.add(group);
//        }
//        System.out.println(list);
//        Elements img = document.getElementsByTag("img");
//        for (Element element : img) {
//            String dataSrc = element.attributes().get("data-src");
//            Attributes attributes = new Attributes();
//            attributes.put("src", dataSrc);
//            Element newElement = new Element(Tag.valueOf("img", new ParseSettings(true, true)), "", attributes);
//            Element previous = element.previousElementSibling();
//            if(previous==null){
//               element.parent().insertChildren(element.siblingIndex(),newElement);
//               element.remove();
//               continue;
//            }
//            Element preBeforePre = previous.previousElementSibling();
//            if(preBeforePre==null){
//                newElement.appendTo(previous);
//                element.remove();
//                continue;
//            }
//            element.remove();
//            previous.remove();
//            newElement.appendTo(preBeforePre);
//
//        }
//        System.out.println(document.toString());
//        Elements allElements = document.getAllElements();
//        HttpURLConnection referer = getConnection("https://mmbiz.qpic.cn/mmbiz_png/zdAhODPD1ZQia7Dna3TR0zoBgccJDcIG6rdou4kwsJE2QDkWfEub741cEYBYIk1sL6GqgPVk351A2DrT3Xq1TTQ/640?wx_fmt=png", Collections.singletonMap("Referer", "https://mmbiz.qpic.cn"));
//        System.out.println(referer);
        Map<String,Object> map =new HashMap<>();
        boolean key = (boolean) map.get("key");
        System.out.println(key);
    }

    private static Map<String, String> stringArrayToMap(String[] array) {
        return Arrays.stream(array).
                filter(StringUtils::hasLength).
                collect(Collectors.toMap(s -> s.substring(s.lastIndexOf("-")+1, s.lastIndexOf(".")), s -> s));
    }

    public static HttpURLConnection getConnection(String sourceUrl, Map<String, String> specialHeaders) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(sourceUrl);
            conn = (HttpURLConnection) url.openConnection();
//            Set<Map.Entry<String, String>> entries = specialHeaders.entrySet();
//            for (Map.Entry<String, String> entry : entries) {
//                conn.addRequestProperty(entry.getKey(), entry.getValue());
//            }
            int responseCode = conn.getResponseCode();
            if (responseCode >= HttpStatus.MULTIPLE_CHOICES.value()) {
                return null;
            }
        } catch (IOException e) {
            if (conn != null) {
                conn.disconnect();
            }
            return null;
        }
        return conn;
    }

}
