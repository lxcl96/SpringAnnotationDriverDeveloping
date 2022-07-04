package com.ly.controller;

import com.ly.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * FileName:BookController.class
 * Author:ly
 * Date:2022/7/1
 * Description:
 */
@Controller
public class BookController {
    @Autowired
    private BookService bookService;
}
