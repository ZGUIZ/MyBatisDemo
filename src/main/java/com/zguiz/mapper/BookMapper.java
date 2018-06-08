package com.zguiz.mapper;

import com.zguiz.bean.Book;
import com.zguiz.bean.Category;

import java.util.List;

public interface BookMapper {
    List<Book> findBookByCategoryName(Category category);
}
