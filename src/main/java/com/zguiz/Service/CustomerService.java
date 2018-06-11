package com.zguiz.Service;

import com.zguiz.bean.*;
import com.zguiz.mapper.BookMapper;
import com.zguiz.mapper.CartMapper;
import com.zguiz.mapper.CustomerMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CustomerService extends BaseService{

    public Customer login(Customer customer) throws Exception {
        if(customer.getAccount()==null || customer.getPassword()==null){
            throw new Exception("账号名或密码不能为空");
        }
        SqlSession sqlSession=sqlSessionFactory.openSession();
        CustomerMapper mapper=sqlSession.getMapper(CustomerMapper.class);
        List<Customer> customers=mapper.findCustomer(customer);
        sqlSession.close();
        if(customers.size()!=0){
            return customers.get(0);
        }
        System.out.println("登录失败!");
        return null;
    }

    public Customer register(String account,String password) throws Exception {
        if(account==null||account.trim().equals("")||
                password==null||password.trim().equals("")){
            throw new Exception("账号名或密码不能为空!");
        }
        Customer customer= (Customer) context.getBean("Customer");
        customer.setAccount(account.trim());
        customer.setPassword(password.trim());
        SqlSession sqlSession=sqlSessionFactory.openSession();
        CustomerMapper mapper=sqlSession.getMapper(CustomerMapper.class);
        int res=mapper.register(customer);
        sqlSession.commit();
        Customer cus=null;
        if(res>0){
            List<Customer> customers=mapper.findCustomer(customer);
            cus=customers.get(0);
        }
        sqlSession.close();
        return cus;
    }

    public List<Cart> findBuyHistory(Customer customer){
        Cart cart= (Cart) context.getBean("Cart");
        cart.setUserId(customer.getUserId());
        SqlSession sqlSession=sqlSessionFactory.openSession();
        CartMapper cartMapper=sqlSession.getMapper(CartMapper.class);
        List<Cart> carts=cartMapper.findCart(cart);
        BookMapper bookMapper=sqlSession.getMapper(BookMapper.class);
        for(Cart c:carts){
            List<CartItem> items=c.getCartItems();
            for(CartItem item:items){
                Book book=new Book();
                book.setIsbn(item.getBookId());
                List<Book> bookList=bookMapper.findBook(book);
                item.setBook(bookList.get(0));
            }
        }
        sqlSession.close();
        return carts;
    }

}
