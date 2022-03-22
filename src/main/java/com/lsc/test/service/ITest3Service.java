package com.lsc.test.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lsc.test.model.Test;
import com.lsc.test.model.Test3;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 作者
 * @since 2022-03-14
 */
public interface ITest3Service extends IService<Test3> {

    IPage<Test3> selectMany(String name);

}
