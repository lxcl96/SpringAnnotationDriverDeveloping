package com.ly.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * FileName:UserDao.class
 * Author:ly
 * Date:2022/7/12
 * Description: 操作数据库
 */
@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int insert() {
        String sql = "INSERT INTO `tbl_user` (username,age) VALUES(?,?);";
        String username = UUID.randomUUID().toString().substring(0, 5);
        return jdbcTemplate.update(sql, username,25);
    }
}
