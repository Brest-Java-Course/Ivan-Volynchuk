package com.epam.brest.courses.rest.controller;

import com.epam.brest.courses.domain.User;
import com.epam.brest.courses.domain.UserImpl;
import com.epam.brest.courses.domain.exception.BadInputData;
import com.epam.brest.courses.domain.exception.NotFoundException;
import com.epam.brest.courses.rest.UserRestController;
import com.epam.brest.courses.rest.VersionRestController;
import com.epam.brest.courses.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.core.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

/**
 * Created by fieldistor on 03.11.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring-rest-mock-test.xml"})
public class UserRestControllerMockTest {

    private MockMvc mockMvc;

    @Resource
    private UserRestController userRestController;

    @Autowired
    private UserService userService;

    @After
    public void down() {
        reset(userService);
    }
    @Before
    public void setUp() {
        this.mockMvc = standaloneSetup(userRestController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @Test
    public void getAllUsers() throws  Exception{

        expect(userService.getUsers()).andReturn(UserDataFixture.getSampleUserList());

        replay(userService);
        this.mockMvc.perform(
                get("/users")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"userId\":1,\"login\":\"login1\",\"userName\":\"name1\"}," +
                        "{\"userId\":2,\"login\":\"login2\",\"userName\":\"name2\"}," +
                        "{\"userId\":3,\"login\":\"login3\",\"userName\":\"name3\"}]"));
        verify(userService);
    }

    @Test
    public void getEmptyUsers() throws  Exception{

        expect(userService.getUsers()).andThrow(new NotFoundException("SPACE",""));

        replay(userService);
        this.mockMvc.perform(
                get("/users")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("\"SPACE\""));
        verify(userService);
    }

    @Test
    public void getUserByIdTest() throws Exception{

        userService.getUserById(1L);
        expectLastCall().andReturn(UserDataFixture.getExistUser(1L));

        replay(userService);
        this.mockMvc.perform(
                get("/users/1")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("{\"userId\":1,\"login\":\"login\",\"userName\":\"name\"}"));
        verify(userService);
    }

    @Test
    public void getUserByNullIdTest() throws Exception{

        userService.getUserById(0L);
        expectLastCall().andThrow(new BadInputData("NULL ID"));

        replay(userService);
        this.mockMvc.perform(
                get("/users/0")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("\"NULL ID\""));
        verify(userService);
    }

    @Test
    public void getUserByWrongIdTest() throws Exception{

        userService.getUserById(55L);
        expectLastCall().andThrow(new NotFoundException("Bad id","55"));

        replay(userService);
        this.mockMvc.perform(
                get("/users/55")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("\"Bad id\""));
        verify(userService);
    }
    @Ignore
    @Test
    public void addUserWithoutLogin() throws Exception {

        User user=UserDataFixture.getNewUserWithoutLogin();
        String userJson = new ObjectMapper().writeValueAsString(user);

        userService.addUser(user);
        expectLastCall().andThrow(new BadInputData("Incorrect ID"));

        replay(userService);
        this.mockMvc.perform(
                post("/users")
                        .content(userJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
        verify(userService);
    }



    @Test
    public void deleteUserById() throws Exception {

        userService.removeUser(1L);
        expectLastCall();
        replay(userService);
        this.mockMvc.perform(
                delete("/users/1")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());

        verify(userService);
    }







    public static class UserDataFixture {

        public static User getNewUser() {
            User user = new UserImpl();
            user.setUserName("name");
            user.setLogin("login");
            return user;
        }

        public static User getNewUser(Long id) {
            User user = new UserImpl();
            user.setUserId(id);
            user.setUserName("name" + id);
            user.setLogin("login" + id);
            return user;
        }

        public static User getNewUserWithoutLogin() {
            User user = new UserImpl();
            user.setUserName("name");
            return user;
        }

        public static User getExistUser(long id) {
            User user = new UserImpl();
            user.setUserId(id);
            user.setUserName("name");
            user.setLogin("login");
            return user;
        }

        public static List<User> getSampleUserList() {
            List<User> list = new ArrayList(3);
            list.add(UserDataFixture.getNewUser(1L));
            list.add(UserDataFixture.getNewUser(2L));
            list.add(UserDataFixture.getNewUser(3L));
            return list;
        }
        public static User getExistUserWithoutLogin(Long id) {
            return new UserImpl(id,null,"name");
        }
    }
}
