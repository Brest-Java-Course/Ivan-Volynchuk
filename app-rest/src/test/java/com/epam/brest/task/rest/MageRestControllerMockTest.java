package com.epam.brest.task.rest;

import com.epam.brest.task.domain.Mage;
import com.epam.brest.task.rest.MageRestController;
import com.epam.brest.task.service.Exception.*;
import com.epam.brest.task.service.MageService;
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

import java.util.LinkedList;
import java.util.List;

import static com.epam.brest.task.dao.tools.TestMageScrollFactory.getAllExistMages;
import static com.epam.brest.task.dao.tools.TestMageScrollFactory.getExistMage;
import static com.epam.brest.task.dao.tools.TestMageScrollFactory.getNewMage;
import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by fieldistor on 27.11.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring-rest-mock-context-test.xml"})
public class MageRestControllerMockTest {

    private MockMvc mockMvc;

    private String MAIN_URL = "/rest/mage/";

    private Long WRONG_MAGE_ID1 = 99L;
    private String WRONG_MAGE_NAME1 = "Guldanidas";

    private String RIGHT_MAGE_NAME1 = "Talnidas";
    private Long RIGHT_MAGE_ID1 = 0L;
    private String JSON_RIGHT_MAGE_ID1 = "{\"mage_id\":0,\"name\":\"Talnidas\",\"level\":10,\"exp\":55,\"scroll_amount\":null," +
            "\"average_manacost\":null,\"magicScrollList\":null}";

    private String RIGHT_MAGE_NAME2 = "Shazaram";
    private Long RIGHT_MAGE_ID2 = 1L;
    private String JSON_RIGHT_MAGE_ID2 = "{\"mage_id\":1,\"name\":\"Shazaram\",\"level\":10,\"exp\":55,\"scroll_amount\":null," +
            "\"average_manacost\":null,\"magicScrollList\":null}";

    private String RIGHT_MAGE_NAME3 = "Ivan";
    private Long RIGHT_MAGE_ID3 = 2L;
    private String JSON_RIGHT_MAGE_ID3 = "{\"mage_id\":2,\"name\":\"Ivan\",\"level\":10,\"exp\":55,\"scroll_amount\":null," +
            "\"average_manacost\":null,\"magicScrollList\":null}";

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

    //Tests for getMageById
    @Test
    public void getMageById() throws Exception{

        mageService.getMageById(RIGHT_MAGE_ID1);
        expectLastCall().andReturn(getExistMage(RIGHT_MAGE_ID1));
        replay(mageService);
        this.mockMvc.perform(
                get(MAIN_URL + RIGHT_MAGE_ID1)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(JSON_RIGHT_MAGE_ID1));
        verify(mageService);
    }

    @Test
    public void getMageByNullOrIncorrectId() throws Exception{

        mageService.getMageById(WRONG_MAGE_ID1);
        expectLastCall().andThrow(new NoItemFoundException("Incorrect Id","",null));

        replay(mageService);
        this.mockMvc.perform(
                get(MAIN_URL + WRONG_MAGE_ID1)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("\"Incorrect Id\""));
        verify(mageService);
    }

    //Tests for getMageByName
    @Test
    public void getMageByName() throws Exception{

        mageService.getMageByName(RIGHT_MAGE_NAME1);
        expectLastCall().andReturn(getExistMage(RIGHT_MAGE_ID1, RIGHT_MAGE_NAME1));
        replay(mageService);
        this.mockMvc.perform(
                get(MAIN_URL + "name/" + RIGHT_MAGE_NAME1)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(JSON_RIGHT_MAGE_ID1));
        verify(mageService);
    }

    @Test
    public void getMageByIncorrectName() throws Exception{

        mageService.getMageByName(WRONG_MAGE_NAME1);
        expectLastCall().andThrow(new NoItemFoundException("Incorrect name","",null));
        replay(mageService);
        this.mockMvc.perform(
                get(MAIN_URL + "name/" + WRONG_MAGE_NAME1)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("\"Incorrect name\""));
        verify(mageService);
    }

    //Tests for getAllMages
    @Test
    public void getAllMages() throws  Exception{

        mageService.getAllMages();
        expectLastCall().andReturn(getAllExistMages());
        replay(mageService);

        this.mockMvc.perform(
                get(MAIN_URL)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("["+JSON_RIGHT_MAGE_ID1 +","+JSON_RIGHT_MAGE_ID2+","+JSON_RIGHT_MAGE_ID3+"]"));
        verify(mageService);
    }

    @Test
    public void getEmptyAllMages() throws  Exception{

        mageService.getAllMages();
        expectLastCall().andThrow(new NoItemsFoundException("No items",""));
        replay(mageService);

        this.mockMvc.perform(
                get(MAIN_URL)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("\"No items\""));
        verify(mageService);
    }

    //Tests for addMage
    @Test
    public void addMage() throws Exception {

        Mage mage = getExistMage(RIGHT_MAGE_ID1);
        String userJson = new ObjectMapper().writeValueAsString(mage);

        mageService.addMage(mage);
        expectLastCall().andReturn(new Long(0L));
        replay(mageService);

        this.mockMvc.perform(
                post(MAIN_URL)
                        .content(userJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(new Long(0L).toString()));
        verify(mageService);
    }

    @Test
    public void addMageWithIncorrectData() throws Exception {

        Mage mage = getExistMage(RIGHT_MAGE_ID1);
        String userJson = new ObjectMapper().writeValueAsString(mage);

        mageService.addMage(mage);
        expectLastCall().andThrow(new BadInsertException("Incorrect Id","",null));
        replay(mageService);

        this.mockMvc.perform(
                post(MAIN_URL)
                        .content(userJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("\"Incorrect Id\""));
        verify(mageService);
    }
    //Tests for updateMage
    @Test
    public void updateMage() throws Exception {

        Mage mage = getExistMage(RIGHT_MAGE_ID1);
        String userJson = new ObjectMapper().writeValueAsString(mage);

        mageService.updateMage(mage);
        expectLastCall();
        replay(mageService);

        this.mockMvc.perform(
                put(MAIN_URL)
                        .content(userJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
        verify(mageService);
    }

    @Test
    public void updateMageWithIncorrectData() throws Exception {

        Mage mage = getExistMage(WRONG_MAGE_ID1);
        String userJson = new ObjectMapper().writeValueAsString(mage);

        mageService.updateMage(mage);
        expectLastCall().andThrow(new BadUpdateException("Incorrect id","",null));
        replay(mageService);

        this.mockMvc.perform(
                put(MAIN_URL)
                        .content(userJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("\"Incorrect id\""));;
        verify(mageService);
    }

    //Test for deleteMage
    @Test
    public void deleteMageById() throws Exception {

        mageService.removeMageById(RIGHT_MAGE_ID1);
        expectLastCall();
        replay(mageService);

        this.mockMvc.perform(
                delete(MAIN_URL+RIGHT_MAGE_ID1)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());

        verify(mageService);
    }

    @Test
    public void deleteMageByIncorrectId() throws Exception {

        mageService.removeMageById(WRONG_MAGE_ID1);
        expectLastCall().andThrow(new BadRemoveException("Incorrect Id","",null));
        replay(mageService);

        this.mockMvc.perform(
                delete(MAIN_URL + WRONG_MAGE_ID1)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("\"Incorrect Id\""));

        verify(mageService);
    }
}
