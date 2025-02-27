package org.wgwj.controller.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.wgwj.controller.entity.User;
import org.wgwj.controller.mapper.UserMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

/**
 * @Author: 徐钢
 * @Date: 2025/2/26:10:57
 * @Description:
 **/

//@Component
public class DBUserDetailsManager  implements UserDetailsManager, UserDetailsPasswordService {


    static {
        System.out.println(".........DBUserDetailManager loaded ...............");
    }
    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails updatePassword(UserDetails userDetails, String newPassword) {
        QueryWrapper<User>  queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userDetails.getUsername());
        User user = userMapper.selectOne(queryWrapper); // 数据库中取得的user
        if(user == null) {
            throw new UsernameNotFoundException(userDetails.getUsername());
        }else{
            user.setPassword(newPassword);
            //queryWrapper.setEntity(user);
            userMapper.update(user,queryWrapper);
        }

        return userDetails;
    }

    @Override
    public void createUser(UserDetails userDetails) {
        User user =  new User();

        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setEnabled(true);
        userMapper.insert(user);
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }


    /**
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User>  queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        User user = userMapper.selectOne(queryWrapper); // 数据库中取得的user
        if(user == null) {
            throw new UsernameNotFoundException(username);
        }else{

            // 权限还没实现， 先用一空列表 ， 作下面的参数
//            Collection<GrantedAuthority> authorities = new ArrayList<>();
////            authorities.add(() -> "USER_LIST");
////            authorities.add(() -> "USER_ADD");    // 这里直接硬编码 ， 只是验证效果， 实现应该在表中存储的
//            // 参照InMemoryUserDetailsManager  对应的方法写
//            return new org.springframework.security.core.userdetails.User
//                    (user.getUsername(),
//                            user.getPassword(),
//                            user.getEnabled(),
//                            true, // 用户账号是否过期
//                            true, // 用户凭证是否过期
//                            true,// 用户是否未补锁定
//                             authorities);

            /***
             * 第二咱方法， 基于角色
             * */
            return  org.springframework.security.core.userdetails.User
                    .withUsername(user.getUsername())
                    .password(user.getPassword())
                    .disabled(!user.getEnabled())
                    .credentialsExpired(false)
                    .accountLocked(false)
                    .roles("ADMIN")
                    .build();
        }

    }
}
