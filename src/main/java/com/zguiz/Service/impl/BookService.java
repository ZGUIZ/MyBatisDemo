package com.zguiz.service.impl;

import com.zguiz.service.IBookService;
import com.zguiz.bean.Book;
import com.zguiz.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookService extends BaseService implements IBookService{

    @Autowired
    private BookMapper bookMapper;

    public List<Book> findBook(Book book){
        List<Book> books=bookMapper.findBook(book);
        return books;
    }

}
