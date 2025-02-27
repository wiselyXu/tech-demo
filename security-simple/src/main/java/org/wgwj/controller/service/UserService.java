package org.wgwj.controller.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.wgwj.controller.entity.User;

public interface UserService extends IService<User> {
    void saveUserDetails(User user);
}
