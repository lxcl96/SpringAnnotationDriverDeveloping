package com.ly.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

/**
 * FileName:UserService.class
 * Author:ly
 * Date:2022/7/12
 * Description: 操作UserDao
 */

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 一般事务都放在Service层开启，出现异常进行回滚
     */
    @Transactional
    public void insertUser() {
        int insert = userDao.insert();
        System.out.println("更新记录：" + insert);
        //其他的dao操作
        int except = 10 / 0;
        System.out.println("所以dao操作全部完成");

    }
}















