package org.wgwj.controller.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Author: 徐钢
 * @Date: 2025/2/26:09:47
 * @Description:
 **/

@Data
public class User {
    @TableId(value = "id", type= IdType.AUTO)  // 主键自增
    private Integer id;

    private String username;
    private String password;
    private Boolean enabled;
}
