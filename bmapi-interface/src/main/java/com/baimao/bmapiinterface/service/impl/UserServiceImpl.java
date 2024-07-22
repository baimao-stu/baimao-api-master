package com.baimao.bmapiinterface.service.impl;

import com.baimao.bmapicommon.model.entity.User;
import com.baimao.bmapiinterface.mapper.UserMapper;
import com.baimao.bmapiinterface.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

}




