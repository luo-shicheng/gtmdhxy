package com.lsc.test.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lsc.test.model.Test;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 作者
 * @since 2022-03-14
 */
public interface ITestService extends IService<Test> {

    Test getOne(long id);

    IPage<Test> selectMany();

}
