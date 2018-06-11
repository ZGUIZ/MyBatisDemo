package com.zguiz.view;

import com.zguiz.Service.BookService;
import com.zguiz.Service.CartItemService;
import com.zguiz.Service.CartService;
import com.zguiz.Service.CustomerService;
import com.zguiz.bean.Book;
import com.zguiz.bean.Cart;
import com.zguiz.bean.CartItem;
import com.zguiz.bean.Customer;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args){
        indexPage();
    }

    private static void indexPage(){
        Customer customer=null;  //用户
        Cart cart=null;               //购物车

        CartItemService itemService=new CartItemService();
        CartService cartService=new CartService();

        boolean flag=true;  //标记是否结束
        int selected;
        while (flag) {
            System.out.println("===================================");
            System.out.println("请选择：");
            System.out.println("1.登录");
            System.out.println("2.注册");
            System.out.println("3.查找书籍");
            System.out.println("4.购买");
            System.out.println("5.查看购买记录");
            System.out.println("6.注销");
            System.out.println("7.退出");
            String num = scanner.nextLine();
            try {
                selected = Integer.parseInt(num);
            }
            catch (NumberFormatException e){
                e.fillInStackTrace();
                System.out.println("输入错误");
                selected=0;
            }
            switch (selected){
                case 1:
                    customer=login();

                    break;
                case 2:
                    customer=register();
                    break;
                case 3:
                    findBoods();
                    break;
                case 4:
                    if(cart==null&&customer!=null){
                        cart=setCart(cartService,customer);
                    }
                    buy(itemService,cart);
                    break;
                case 5:
                    findBuyHistory(customer);
                    break;
                case 6:
                    customer=null;
                    cart=null;
                    break;
                case 7:
                    flag=false;
                    break;
            }
        }
        scanner.close();
    }

    private static Cart setCart(CartService cartService,Customer customer){
        Cart cart=null;
        if(customer!=null) {
            cart = cartService.addCart(customer);   //新建购物车
        }
        return cart;
    }

    private static Customer login(){
        System.out.println("请输入账号：");
        String account=scanner.nextLine();
        System.out.println("请输入密码：");
        String password=scanner.nextLine();
        CustomerService service=new CustomerService();
        Customer customer=new Customer();
        customer.setAccount(account);
        customer.setPassword(password);
        try {
            customer=service.login(customer);
            if(customer!=null){
                System.out.println("登录成功！");
            }
            else{
                System.out.println("登录失败！");
            }
            return customer;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static Customer register(){
        System.out.println("请输入账号：");
        String account=scanner.nextLine();
        System.out.println("请输入密码：");
        String password=scanner.nextLine();
        CustomerService service=new CustomerService();
        Customer customer=new Customer();
        customer.setAccount(account);
        customer.setPassword(password);
        Customer cus=null;
        try {
            cus=service.register(account,password);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return cus;
    }

    private static void findBoods(){
        BookService bookService=new BookService();
        System.out.println();
        System.out.println("请输入书名");
        String name=scanner.nextLine();
        Book book=new Book();
        book.setBookName(name);
        List<Book> books=bookService.findBook(book);
        System.out.println("ISBN\t书名\t价格\t出版社\t出版日期");
        for(Book b:books){
            System.out.println(b.toString());
        }
    }

    private static void buy(CartItemService itemService,Cart cart){
        if(cart==null){
            System.out.println("请登陆！");
            return;
        }
        System.out.println("请输入要购买的书籍的ISBN：");
        String isbn=scanner.nextLine();
        Book book=new Book();
        book.setIsbn(isbn);
        System.out.println("请输入购买的数量：");
        String numberStr=scanner.nextLine();
        int num=0;
        try{
            num=Integer.parseInt(numberStr);
            if(num<=0){
                throw new Exception("数量输入错误");
            }
            itemService.addItem(book,num,cart);
        }
        catch (NumberFormatException e){
            e.fillInStackTrace();
            System.out.println("数量输入错误");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void findBuyHistory(Customer customer){
        if(customer==null){
            System.out.println("请登陆");
            return;
        }
        CustomerService customerService=new CustomerService();
        List<Cart> carts=customerService.findBuyHistory(customer);
        for(Cart cart:carts){
            System.out.println("账号："+customer.getAccount()+"\t日期:"+cart.getCreateDate());
            List<CartItem> items=cart.getCartItems();
            for (CartItem item:items){
                System.out.println(item);

            }
        }
    }

}
