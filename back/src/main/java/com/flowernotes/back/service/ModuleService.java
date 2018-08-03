package com.flowernotes.back.service;

import com.flowernotes.core.base.BaseService;
import com.flowernotes.core.bean.ModuleBean;

import java.util.List;

/**
 * @author cyk
 * @date 2018/8/2/002 15:01
 * @email choe0227@163.com
 * @desc
 * @modifier
 * @modify_time
 * @modify_remark
 */
public interface ModuleService extends BaseService<ModuleBean> {

    /**
     * 添加模块
     * @param moduleBean
     * @return
     */
    ModuleBean addModule(ModuleBean moduleBean);

    /**
     * 更新模块
     * @param moduleBean
     * @return
     */
    ModuleBean updateModule(ModuleBean moduleBean);

    /**
     * 删除模块
     * @param moduleBean
     * @return
     */
    void deleteModule(ModuleBean moduleBean);


    /**
     * 将模块生成树结构
     * @param modules
     * @param pid
     * @return
     */
    List<ModuleBean> generateTree(List<ModuleBean> modules, Long pid);

    /**
     * 获取所有的模块列表，并生成树结构
     * @return
     */
    List<ModuleBean> getModuleList();
}
