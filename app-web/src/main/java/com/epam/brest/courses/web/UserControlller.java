package com.epam.brest.courses.web;

import com.epam.brest.courses.domain.User;
import com.epam.brest.courses.domain.UserImpl;
import com.epam.brest.courses.service.UserService;
import com.sun.javafx.sg.prism.NGShape;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by fieldistor on 10.11.14.
 */
@Controller
@RequestMapping("/mvc")
public class UserControlller {

    @Autowired
    private UserService userService;

    @RequestMapping(value={"/index"})
    public ModelAndView showIndex() {

        List<User> users =userService.getUsers();
        return new ModelAndView("index","users",users);
    }

    @RequestMapping(value={"/"})
    public ModelAndView launchInputForm() {


        return new ModelAndView("inputForm","user",new UserImpl());
    }

    @RequestMapping(value={"/submitData"})
    public ModelAndView getInputForm(@RequestParam("userName") String userName,@RequestParam("login") String login) {

        UserImpl user= new UserImpl();
        user.setUserName(userName);
        user.setLogin(login);

        Long id=userService.addUser(user);
        user.setUserId(id);

        return new ModelAndView("displayResult","user",user);
    }
}
