package com.zguiz.Service;

import com.zguiz.bean.Customer;
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

}
