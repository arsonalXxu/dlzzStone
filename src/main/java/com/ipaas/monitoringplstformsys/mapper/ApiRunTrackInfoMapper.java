package com.ipaas.monitoringplstformsys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ipaas.monitoringplstformsys.module.ApiRunTrackInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApiRunTrackInfoMapper extends BaseMapper<ApiRunTrackInfo> {
    // MyBatis Plus 自带 batch 查询，无需手写
}
