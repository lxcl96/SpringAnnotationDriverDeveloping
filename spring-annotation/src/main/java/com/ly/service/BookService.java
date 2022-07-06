package com.ly.service;

import com.ly.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * FileName:BookService.class
 * Author:ly
 * Date:2022/7/1
 * Description:
 */
@Primary
@Service
public class BookService {

    //@Inject
    //@Resource()
    //Qualifier
    @Autowired
    private BookDao bookDao;

    public BookDao getBookDao() {
        return bookDao;
    }

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }
}
