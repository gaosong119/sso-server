package com.aerotop.ssoserver.dao;

import com.aerotop.ssoserver.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @Description: UserMapper接口
 * @Author: gaosong
 * @Date: 2021/2/3 14:24
 **/
@Mapper
public interface UserDao {
    /** 通过主键删除数据 */
    int deleteByPrimaryKey(String id);
    /** 新增数据 */
    int insert(User record);
    /** 新增数据时进行非空校验 */
    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    User selectUser(User user);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}