package com.zguiz.mapper;

import com.zguiz.bean.Book;
import com.zguiz.bean.Category;

import java.util.List;

public interface BookMapper {
    List<Book> findBookByCategoryName(Category category);
    List<Book> findAll();
    int addBook(Book book);
    List<Book> findAllBookAndCategory();
    List<Book> findBook(Book book);
    int updateBook(Book book);
}
