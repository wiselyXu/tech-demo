package org.wgwj.controller.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.wgwj.controller.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
