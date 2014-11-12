package com.epam.brest.courses.web;

import com.epam.brest.courses.domain.User;
import com.epam.brest.courses.domain.UserImpl;
import com.epam.brest.courses.service.UserService;
import com.sun.javafx.sg.prism.NGShape;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by fieldistor on 10.11.14.
 */
@Controller
public class UserControlller {

    @Autowired
    private UserService userService;

    @RequestMapping(value={"/"})
    public ModelAndView showIndex() {

        List<User> users =userService.getUsers();
        return new ModelAndView("index","users",users);
    }

    @RequestMapping(value={"/delete"}, method = RequestMethod.GET)
    public String deleteUser(@RequestParam("id") Long id) {

        userService.removeUser(id);
        return "redirect:/";

    }

    @RequestMapping(value={"/update"}, method = RequestMethod.GET)
    public ModelAndView showUserToUpdate(@RequestParam("id") Long id) {


        return new ModelAndView("changer","user",userService.getUserById(id));
    }

    @RequestMapping(value={"/update"}, method = RequestMethod.POST)
    public String updateUser(@RequestParam("userId") Long id,
                                   @RequestParam("userName") String name,
                                   @RequestParam("login") String login) {

        userService.updateUser(new UserImpl(id,login,name));
        return "redirect:/";
    }

    @RequestMapping(value={"/add"}, method = RequestMethod.GET)
    public ModelAndView showAdd() {

        return new ModelAndView("adder");
    }
    @RequestMapping(value={"/add"}, method = RequestMethod.POST)
    public String addUser(@RequestParam("userName") String name,
                                @RequestParam("login") String login) {

        userService.addUser(new UserImpl(null,login,name));
        return "redirect:/";
    }


}
