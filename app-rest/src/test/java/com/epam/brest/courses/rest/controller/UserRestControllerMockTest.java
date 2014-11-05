package com.epam.brest.courses.rest.controller;

import com.epam.brest.courses.domain.User;
import com.epam.brest.courses.domain.UserDataFixture;
import com.epam.brest.courses.domain.UserImpl;
import com.epam.brest.courses.domain.exception.BadInputData;
import com.epam.brest.courses.domain.exception.NotFoundException;
import com.epam.brest.courses.rest.UserRestController;
import com.epam.brest.courses.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
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
    public void addUserLogin() throws Exception {

        User user=UserDataFixture.getExistUser(0L);
        String userJson = new ObjectMapper().writeValueAsString(user);

        userService.addUser(user);
        expectLastCall().andReturn(new Long(1L));

        replay(userService);
        this.mockMvc.perform(
                post("/users")
                        .content(userJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
		.andExpect(content().string(new Long(1L).toString()));
        verify(userService);
    }

    @Test
    public void updateUser() throws Exception {

        User user=UserDataFixture.getExistUser(10L);
        String userJson = new ObjectMapper().writeValueAsString(user);

        userService.updateUser(user);
        expectLastCall();

        replay(userService);
        ResultActions resultActions =this.mockMvc.perform(
                put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                        .accept(MediaType.APPLICATION_JSON)
        );
        resultActions.andDo(print());
        resultActions.andExpect(status().isOk());
        verify(userService);
    }

    @Test
    public void addIncorrectUserLogin() throws Exception {

        User user=UserDataFixture.getExistUser(0L);
        String userJson = new ObjectMapper().writeValueAsString(user);

        userService.addUser(user);
        expectLastCall().andThrow(new BadInputData(""));

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
                .andExpect(content().string("{\"type\":\"userimpl\",\"userId\":1,\"login\":\"login\",\"userName\":\"name\"}"));
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


}
