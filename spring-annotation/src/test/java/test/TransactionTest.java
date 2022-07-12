package test;

import com.ly.tx.TxConfig;
import com.ly.tx.UserService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * FileName:TransactionTest.class
 * Author:ly
 * Date:2022/7/12
 * Description:
 */
public class TransactionTest {

    /**
     *简单测试jdbc template，没有开启任何事务
     */
    @Test
    public void testNormal(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TxConfig.class);
        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.insertUser();


        applicationContext.close();
    }
}
