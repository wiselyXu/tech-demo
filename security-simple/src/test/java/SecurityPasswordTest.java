import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

/**
 * @Author: 徐钢
 * @Date: 2025/2/26:15:48
 * @Description:
 **/

@SpringBootTest
public class SecurityPasswordTest {


    @Test
    void contextLoads(){

    }


    @Test
    void testPassword(){
        // 工作因子  ， 默认值 是 10, 值 范围为  4-31， 越大则越慢
        PasswordEncoder  encoder = new BCryptPasswordEncoder(4);
        String result = encoder.encode("abc123");
        System.out.println("加密后的密文"+result);

        Assert.isTrue(encoder.matches("abc123",result),"密码不一致");

    }



}
