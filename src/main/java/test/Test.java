package test;

import com.zguiz.bean.Book;
import com.zguiz.bean.Pager;
import com.zguiz.mapper.BookMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test {
    private Logger logger=Logger.getRootLogger();
    protected static SqlSessionFactory sqlSessionFactory;
    protected static ApplicationContext context;
    @Before
    public void init(){
        String resource= "mybatis-conf.xml";
        String contextResource="applicationContext.xml";
        InputStream is=null;
        logger.info("准备初始化MyBatis");
        try {
            is= Resources.getResourceAsStream(resource);
            sqlSessionFactory=new SqlSessionFactoryBuilder().build(is);
            context=new ClassPathXmlApplicationContext(contextResource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            logger.info("初始化完毕");
        }
    }

    @org.junit.Test
    public void test1(){
        SqlSession sqlSession=sqlSessionFactory.openSession();
        BookMapper mapper=sqlSession.getMapper(BookMapper.class);
        Book book=new Book();
        book.setIsbn("ISBN-21554-5412-21");
        book.setPrice(33.3);
        mapper.updateBook(book);
        sqlSession.commit();
        sqlSession.close();
    }

    @org.junit.Test
    public void testFindByPagere(){
        Pager pager=null;
        SqlSession sqlSession=sqlSessionFactory.openSession();
        BookMapper bookMapper=sqlSession.getMapper(BookMapper.class);
        int total=bookMapper.countForPager(pager);
        pager=new Pager(total);
        pager.setCurrentPage(2);
        pager.setPageSize(5);
        System.out.println("上一页："+pager.getPrePage()+"下一页："+pager.getNextPage()+"总页数："+pager.getPages());
        List<Book> list=bookMapper.findByPager(pager);
        for(Book book:list){
            System.out.println(book);
        }
    }

    @org.junit.Test
    public void testfindByIsbnList(){
        List<String> isbnList=new ArrayList<String>();
        isbnList.add("ISBN-21554-5412-29");
        isbnList.add("ISBN-21554-5412-24");
        isbnList.add("ISBN-21554-5412-21");
        SqlSession sqlSession=sqlSessionFactory.openSession();
        BookMapper bookMapper=sqlSession.getMapper(BookMapper.class);
        List<Book> bookList=bookMapper.findByIsbnList(isbnList);
        for(Book book:bookList){
            System.out.println(book);
        }
        sqlSession.close();
    }

    @org.junit.Test
    public void testfindByPageWithParam(){
        Pager<Book> pager=new Pager<Book>(1,5);
        Book b=new Book();
        b.setBookName("数学");
        b.setPrice(26);
        pager.setParam(b);
        SqlSession sqlSession=sqlSessionFactory.openSession();
        BookMapper bookMapper=sqlSession.getMapper(BookMapper.class);
        int total=bookMapper.countForPager(pager);
        pager.setTotal(total);
        System.out.println("上一页："+pager.getPrePage()+"\t下一页："+pager.getNextPage()+"\t总页数："+pager.getPages());
        List<Book> list=bookMapper.findByPager(pager);
        for(Book book:list){
            System.out.println(book);
        }
    }

    @org.junit.Test
    public void testdeleteByIsbnList(){
        List<String> isbnList=new ArrayList<String>();
        isbnList.add("ISBN-21554-5412-39");
        isbnList.add("ISBN-21554-5412-41");
        isbnList.add("ISBN-21554-5412-42");
        SqlSession sqlSession=sqlSessionFactory.openSession();
        BookMapper bookMapper=sqlSession.getMapper(BookMapper.class);
        bookMapper.deleteByIsbnList(isbnList);
        sqlSession.commit();
        sqlSession.close();
    }

    @org.junit.Test
    public void testupdateByList(){
        List<Book> bookList=new ArrayList<Book>();
        Book book1=new Book();
        book1.setIsbn("ISBN-21554-5412-26");
        book1.setPrice(34);
        book1.setBookName("乌合之众");
        bookList.add(book1);
        Book book2=new Book();
        book2.setIsbn("ISBN-21554-5412-25");
        book2.setBookName("微粒社会");
        book2.setPrice(54);
        bookList.add(book2);
        SqlSession sqlSession=sqlSessionFactory.openSession();
        BookMapper bookMapper=sqlSession.getMapper(BookMapper.class);
        bookMapper.editBooks(bookList);
        sqlSession.commit();
        sqlSession.close();
    }
    @org.junit.Test
    public void testinsertByList(){
        List<Book> bookList=new ArrayList<Book>();
        Book book1=new Book();
        book1.setIsbn("ISBN-21554-5412-37");
        book1.setPrice(34.3);
        book1.setBookName("设计模式");
        book1.setPublishDate(new Date());
        book1.setPublisher("大宁电子出版社");
        book1.setCategoryId(1);
        bookList.add(book1);
        Book book2=new Book();
        book2.setIsbn("ISBN-21554-5412-38");
        book2.setBookName("Python入门");
        book2.setPublisher("大宁电子出版社");
        book2.setPublishDate(new Date());
        book2.setPrice(44.6);
        book2.setCategoryId(4);
        bookList.add(book2);
        SqlSession sqlSession=sqlSessionFactory.openSession();
        BookMapper bookMapper=sqlSession.getMapper(BookMapper.class);
        bookMapper.addBooks(bookList);
        sqlSession.commit();
        sqlSession.close();
    }
}
