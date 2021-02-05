package com.aerotop.ssoserver.service.impl;

import com.aerotop.ssoserver.dao.UserDao;
import com.aerotop.ssoserver.pojo.User;
import com.aerotop.ssoserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: UserServiceImpl
 * @Description: TODO
 * @Author: gaosong
 * @Date 2021/2/3 14:44
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User selectUser(User user) {
        return  userDao.selectUser(user);
    }
}
