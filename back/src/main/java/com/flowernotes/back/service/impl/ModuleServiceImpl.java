package com.flowernotes.back.service.impl;

import com.flowernotes.back.dao.ModuleMapper;
import com.flowernotes.back.service.ModuleService;
import com.flowernotes.back.service.RoleModuleService;
import com.flowernotes.common.exception.CommonException;
import com.flowernotes.common.exception.Error;
import com.flowernotes.common.utils.ObjectUtil;
import com.flowernotes.common.utils.StringUtil;
import com.flowernotes.core.base.BaseMapper;
import com.flowernotes.core.base.BaseServiceImpl;
import com.flowernotes.core.bean.ModuleBean;
import com.flowernotes.core.bean.RoleModuleBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
@Service
@Transactional
public class ModuleServiceImpl extends BaseServiceImpl<ModuleBean> implements ModuleService {
    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private RoleModuleService roleModuleService;

    @Override
    protected BaseMapper<ModuleBean> getMainMapper() {
        return moduleMapper;
    }

    @Override
    public ModuleBean addModule(ModuleBean moduleBean) {
        if (StringUtil.isEmpty(moduleBean.getName())) {
            throw new CommonException(Error.common_lost_params, "模块缺少名称");
        }
        if (StringUtil.isEmpty(moduleBean.getUrl())) {
            throw new CommonException(Error.common_lost_params, "模块缺少对应的连接");
        }
        if (moduleBean.getPid() == null) {
            throw new CommonException(Error.common_lost_params, "模块缺少父级");
        }
        if (moduleBean.getType() == null) {
            throw new CommonException(Error.common_lost_params, "模块类型未指定");
        }
        add(moduleBean);
        return moduleBean;
    }

    @Override
    public ModuleBean updateModule(ModuleBean moduleBean) {
        if (moduleBean.getId() == null || moduleBean.getId().equals(0)) {
            throw new CommonException(Error.id_must_not_be_null, "更新模块id不能为空");
        }
        ModuleBean byId = getById(moduleBean.getId());
        if (byId == null) {
            throw new CommonException(Error.common_obj_not_exist, "更新模块不存在");
        }
        update(moduleBean);
        ObjectUtil.insertObj(byId, moduleBean);
        return byId;
    }

    @Override
    public void deleteModule(ModuleBean moduleBean) {
        if (moduleBean.getId() == null || moduleBean.getId().equals(0)) {
            throw new CommonException(Error.id_must_not_be_null, "删除模块id不能为空");
        }
        ModuleBean byId = getById(moduleBean.getId());
        if (byId == null) {
            throw new CommonException(Error.common_obj_not_exist, "删除模块不存在");
        }
        deleteIteration(moduleBean);
    }

    /**
     * 删除模块及子模块，同时删除中间关联表信息
     * @param moduleBean
     */
    public void deleteIteration(ModuleBean moduleBean){
        ModuleBean module = new ModuleBean();
        module.setPid(moduleBean.getId());
        List<ModuleBean> select = select(module);
        if (select !=null && !select.isEmpty()){
            for (ModuleBean m: select) {
                deleteIteration(m);
            }
        }
        // 删除模块
        delete(moduleBean);
        // 删除关联表
        RoleModuleBean roleModuleBean = new RoleModuleBean();
        roleModuleBean.setModule_id(moduleBean.getId());
        roleModuleService.delete(roleModuleBean);
    }

    /**
     * 根据list 生成 权限树
     *
     * @param modules 所有的权限
     * @param pid     根节点的父id, 在本项目中,根节点父id从1开始
     * @return
     */
    public List<ModuleBean> generateTree(List<ModuleBean> modules, Long pid) {
        List<ModuleBean> list = new ArrayList<>();
        if (modules == null || modules.isEmpty()) {
            return list;
        }
        for (ModuleBean module : modules) {
            if (pid.equals(module.getPid())) {
                list.add(module);
                List<ModuleBean> list1 = generateTree(modules, module.getId());
                module.setChildrenList(list1);
            }
        }
        return list;
    }

    @Override
    public List<ModuleBean> getModuleList() {
        ModuleBean moduleBean = new ModuleBean();
        List<ModuleBean> modules = select(moduleBean);
        modules = generateTree(modules,1L);
        return modules;
    }
}
