package com.epam.brest.courses.rest;

import com.epam.brest.courses.domain.User;
import com.epam.brest.courses.domain.UserImpl;
import com.epam.brest.courses.domain.exception.BadInputData;
import com.epam.brest.courses.domain.exception.NotFoundException;
import com.epam.brest.courses.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by fieldistor on 03.11.14.
 */
@Controller
@RequestMapping("/users")
public class UserRestController {


    @Resource
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<List<User>> getUsers() {

        try {
            List<User> users = userService.getUsers();
            return new ResponseEntity(users, HttpStatus.OK);
        }catch(NotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> getUserById(@PathVariable Long id) {

        try {
            User user = userService.getUserById(id);
            return new ResponseEntity(user, HttpStatus.OK);
        }catch(NotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(BadInputData e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/login/{login}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> getUserByLogin(@PathVariable String login) {

        try {
            User user = userService.getUserByLogin(login);
            return new ResponseEntity(user, HttpStatus.OK);
        }catch(NotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(BadInputData e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @ResponseBody
    @RequestMapping(method= RequestMethod.POST)
    public ResponseEntity<Long> addUser(@RequestBody User user) {
        try {
            Long id = userService.addUser(user);
            return new ResponseEntity(id, HttpStatus.CREATED);
        }catch(BadInputData e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity updateUser(@RequestBody User user) {

        try {
            userService.updateUser(user);
            return new ResponseEntity("", HttpStatus.OK);
        }catch(NotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(BadInputData e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity removeUser(@PathVariable Long id) {

        try {
            userService.removeUser(id);
            return new ResponseEntity("", HttpStatus.OK);
        }catch(NotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(BadInputData e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}