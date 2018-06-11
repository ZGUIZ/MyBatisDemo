package com.zguiz.Service;

import com.zguiz.bean.Cart;
import com.zguiz.bean.Customer;
import com.zguiz.mapper.CartMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;
import java.util.List;

public class CartService extends BaseService{

    public Cart addCart(Customer customer){
        Date date=new Date();
        Cart cart= (Cart) context.getBean("Cart");
        cart.setCreateDate(date);
        cart.setUserId(customer.getUserId());
        SqlSession sqlSession=sqlSessionFactory.openSession();
        CartMapper cartMapper=sqlSession.getMapper(CartMapper.class);
        int res=cartMapper.addCart(cart);
        if(res>0){
            List<Cart> carts=cartMapper.findCart(cart);
            Cart c=carts.get(0);
            sqlSession.commit();
            sqlSession.close();
            return c;
        }
        sqlSession.close();
        return null;
    }

}
