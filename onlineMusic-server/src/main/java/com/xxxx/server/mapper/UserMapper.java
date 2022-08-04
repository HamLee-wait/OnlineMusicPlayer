package com.xxxx.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxx.server.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lee
 * @since 2022-08-01
 */
public interface UserMapper extends BaseMapper<User> {

    int getNumberByUsername();

    void register(@Param("param") Map<String, Object> map);
}
