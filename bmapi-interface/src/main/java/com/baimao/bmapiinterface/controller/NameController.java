package com.baimao.bmapiinterface.controller;


import com.baimao.bmapicommon.model.entity.User;
import com.baimao.bmapiinterface.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author baimao
 * @title NameControoler
 */

@RestController
@RequestMapping("/name")
public class NameController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String getNameUsingGet(String name, HttpServletRequest request,
                                  HttpServletResponse response) {
        System.out.println(request.getHeader("testHeader"));
        return "Get your name：" + name;
    }

    @PostMapping
    public String getNameUsingPost(@RequestParam String name) {
        return "Post your name：" + name;
    }

    @PostMapping("/json")
    public String getNameUsingPostJson(@RequestBody User user, HttpServletRequest request) {
        return "Post[json] your name：" + user.getUserName();
    }

}
