package com.zguiz.Service;

import com.zguiz.bean.Book;
import com.zguiz.mapper.BookMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class BookService extends BaseService{

    public List<Book> findBook(Book book){
        SqlSession sqlSession=sqlSessionFactory.openSession();
        BookMapper bookMapper=sqlSession.getMapper(BookMapper.class);
        List<Book> books=bookMapper.findBook(book);
        sqlSession.close();
        return books;
    }

}
