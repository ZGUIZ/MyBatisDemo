package com.zguiz.Service;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.InputStream;

public abstract class BaseService {
    protected static SqlSessionFactory sqlSessionFactory;
    protected static ApplicationContext context;

    static{
        String resource= "mybatis-conf.xml";
        String contextResource="applicationContext.xml";
        InputStream is=null;
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
        }
    }
}
