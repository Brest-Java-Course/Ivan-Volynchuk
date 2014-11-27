package com.epam.brest.task.rest;

import com.epam.brest.task.rest.MageRestController;
import com.epam.brest.task.service.MageService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;

import static org.easymock.EasyMock.reset;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by fieldistor on 27.11.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring-rest-mock-context-test.xml"})
public class MageRestControllerMockTest {

    private MockMvc mockMvc;

    @Resource
    MageRestController mageRestController;

    @Autowired
    MageService mageService;

    @After
    public void down() {
        reset(mageService);
    }

    @Before
    public void setUp() {
        this.mockMvc = standaloneSetup(mageRestController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @Test
    public void test() {

        //TODO: all test
    }
}
