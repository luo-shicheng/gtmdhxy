package com.lsc.test.rs;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lsc.test.model.Test;
import com.lsc.test.model.Test3;
import com.lsc.test.service.ITest3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/test3")
public class Test3Controller {

    @Autowired
    private ITest3Service iTest3Service;

    @GetMapping("/getMany")
    public List<Test3> getMany(@RequestParam String name) {
        IPage<Test3> testIPage = iTest3Service.selectMany(name);
        System.out.println(testIPage.getTotal());
        return testIPage.getRecords();
    }

}
