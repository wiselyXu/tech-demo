package com.gwkj.example;

/**
 * @Author: 徐钢
 * @Date: 2025/3/5:11:24
 * @Description:
 **/
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * OAuth resource controller.
 *
 * @author Josh Cummings
 */
@RestController
public class OAuth2ResourceServerController {

    @GetMapping("/")
    public String index(@AuthenticationPrincipal Jwt jwt) {
        return String.format("Hello, %s!", jwt.getSubject());
    }

    @GetMapping("/message")
    public String message() {
        return "secret message";
    }

    @PostMapping("/message")
    public String createMessage(@RequestBody String message) {
        System.out.println("============== in create message , "+ message);
        return String.format("Message was created. Content: %s", message);
    }

}
