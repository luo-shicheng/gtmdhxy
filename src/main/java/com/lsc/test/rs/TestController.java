package com.lsc.test.rs;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lsc.test.model.Test;
import com.lsc.test.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 作者
 * @since 2022-03-14
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ITestService iTestService;


    @GetMapping("/getTest")
    public Test getTest(@RequestParam long id) {
       return iTestService.getOne(id);
    }

    @GetMapping("/getMany")
    public List<Test> getMany() {
        IPage<Test> testIPage = iTestService.selectMany();
        System.out.println(testIPage.getTotal());
        return testIPage.getRecords();
    }

}
