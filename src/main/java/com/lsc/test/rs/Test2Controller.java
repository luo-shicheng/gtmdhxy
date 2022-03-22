package com.lsc.test.rs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lsc.test.model.UrlBody;
import com.lsc.test.util.ScriptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("/test2")
@RestController
public class Test2Controller {

    @Autowired
    private ObjectMapper objectMapper;

//    @Autowired
//    private TestMapper testMapper;


//    @Autowired
//    private RBloomFilter<String> rBloomFilter;
//
//    @Autowired
//    private RedisTemplate redisTemplate;

    @PostMapping("/load")
    public void downLoad(@RequestPart("uploadFiles") List<MultipartFile> uploadFiles){
        for (MultipartFile uploadFile : uploadFiles) {
            System.out.println(uploadFile.getOriginalFilename());

        }
    }

    @GetMapping("/filter")
    public void filter(@RequestParam String id){
    }

//    @PutMapping("/setCache")
//    public void setCache(@RequestParam String id){
//        redisTemplate.opsForValue().set("lsc",id);
//        Object lsc = redisTemplate.opsForValue().get("lsc");
//        System.out.println(lsc.getClass());
//        System.out.println(lsc);
//    }

    @GetMapping("/write")
    public void write(HttpServletResponse response) throws Exception{
        response.getOutputStream().write("wdnmd".getBytes());
    }

    @PostMapping("/read")
    public void read(@RequestBody UrlBody body){
        System.out.println("read");
    }
    @GetMapping ("/redirect")
    public void redirect(HttpServletRequest request,HttpServletResponse response) throws Exception{
        response.sendRedirect("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.tupian1.cn%2Fuploads%2Fallimg%2F150919%2F1-15091Z11F0.jpg&refer=http%3A%2F%2Fwww.tupian1.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613037468&t=c1a92dc15af5413d68f446461809beca");

    }

    @GetMapping ("/command")
    public void command() throws Exception{
        String ffmpegPath = "D:\\javaDev\\ffmpeg-4.4-essentials_build\\bin\\ffmpeg.exe";
        String sourceFile = "C:\\Users\\DELL\\Videos\\Captures\\test.mp4";
        String destination = "C:\\Users\\DELL\\Videos\\Captures\\thumb.jpg";
        int exec = ScriptUtil.exec(ffmpegPath + " -i " + sourceFile + " -frames:v 1 " + destination);
    }

    @GetMapping ("/getJson")
    public List<Map<?,?>> getJson() throws Exception{
        List<Map<?,?>> list =new ArrayList<>();
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\DELL\\Desktop\\wechat-20210825153631-1.json");
        String string = StreamUtils.copyToString(fileInputStream, StandardCharsets.UTF_8);
        String[] array = string.split("\n");
        for (String s : array) {
            Map map = objectMapper.readValue(s, Map.class);
            list.add(map);
        }
        return list;
    }

}
