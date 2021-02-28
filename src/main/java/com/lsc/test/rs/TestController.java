package com.lsc.test.rs;

import com.lsc.test.model.Read;
import com.lsc.test.model.UrlBody;
import com.lsc.test.model.Write;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RequestMapping("/test")
@RestController
public class TestController {

    @PostMapping("/load")
    public void downLoad(@RequestPart("uploadFiles") List<MultipartFile> uploadFiles,@RequestPart("body") UrlBody body){
        for (MultipartFile uploadFile : uploadFiles) {
            System.out.println(uploadFile.getOriginalFilename());
            System.out.println(body.getUrl());
        }
    }

    @PostMapping("/write")
    public void write(@RequestBody @Valid UrlBody body){
        System.out.println("write");
    }

    @PostMapping("/read")
    public void read(@RequestBody @Valid UrlBody body){
        System.out.println("read");
    }
    @GetMapping ("/redirect")
    public void redirect(HttpServletResponse response) throws Exception{
        response.sendRedirect("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.tupian1.cn%2Fuploads%2Fallimg%2F150919%2F1-15091Z11F0.jpg&refer=http%3A%2F%2Fwww.tupian1.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613037468&t=c1a92dc15af5413d68f446461809beca");

    }

}
