package com.test.activiti.service.impl;

import com.test.activiti.entity.Dictionary;
import com.test.activiti.mapper.DictionaryMapper;
import com.test.activiti.service.IDictionaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 系统数据字典表 服务实现类
 * </p>
 *
 * @author aaa
 * @since 2020-05-07
 */
@Service
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary> implements IDictionaryService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void test() {
        this.baseMapper.insert(new Dictionary().setDicName("测试字典！").setDicRemark("测试字典！"));
        int i = 1/0;
        this.baseMapper.insert(new Dictionary().setDicName("测试字典1111！").setDicRemark("测试字典1111！"));
    }
}
