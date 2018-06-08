package test;

import com.zguiz.bean.Book;
import com.zguiz.bean.Category;
import com.zguiz.mapper.BookMapper;
import com.zguiz.mapper.CategoryMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestClass {
    public static void main(String[] args){
        String resource="mybatis-conf.xml";
        InputStream inputStream=null;
        SqlSession sqlSession=null;
        try {
            inputStream= Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession=sqlSessionFactory.openSession();
            BookMapper bookMapper=sqlSession.getMapper(BookMapper.class);
            Category category=new Category();
            category.setName("机械工业");
            List<Book> books=bookMapper.findBookByCategoryName(category);
            Book a=null;
            for(Book b:books){
                a=b;
                System.out.println(b);
            }
            CategoryMapper categoryMapper=sqlSession.getMapper(CategoryMapper.class);
            if(a==null){
                return;
            }
            List<Category> categories=categoryMapper.getCategoryByBookName(a);
            for(Category category1:categories){
                System.out.println(category1.getId()+"\t"+category1.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(inputStream!=null){
                    inputStream.close();
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
            if(sqlSession!=null){
                sqlSession.close();
            }
        }
    }
}
