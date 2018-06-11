package com.zguiz.Service;

import com.zguiz.bean.Book;
import com.zguiz.bean.Cart;
import com.zguiz.bean.CartItem;
import com.zguiz.mapper.CartItemMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;

public class CartItemService extends BaseService{

    public boolean addItem(Book book, int number, Cart cart){
        CartItem cartItem= (CartItem) context.getBean("CartItem");
        cartItem.setAddTime(new Date());
        cartItem.setBookId(book.getIsbn());
        cartItem.setCartId(cart.getCartId());
        cartItem.setCount(number);
        SqlSession sqlSession=sqlSessionFactory.openSession();
        CartItemMapper mapper=sqlSession.getMapper(CartItemMapper.class);
        int res=mapper.addCartItem(cartItem);
        sqlSession.commit();
        sqlSession.close();
        if(res>0){
            return true;
        }
        return false;
    }

}
