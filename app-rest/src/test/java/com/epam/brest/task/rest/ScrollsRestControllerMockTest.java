package com.epam.brest.task.rest;

import com.epam.brest.task.domain.MagicScroll;
import com.epam.brest.task.service.Exception.*;
import com.epam.brest.task.service.MagicScrollService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.LocalDate;
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

import javax.annotation.Resource;

import static com.epam.brest.task.dao.tools.TestMagicScrollFactory.getAllExistScrolls;
import static com.epam.brest.task.dao.tools.TestMagicScrollFactory.getExistScroll;
import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by fieldistor on 29.11.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring-rest-mock-context-test.xml"})
public class ScrollsRestControllerMockTest {

    private MockMvc mockMvc;

    private String MAIN_URL = "/rest/scroll/";

    private Long WRONG_SCROLL_ID1 = 99L;
    private String WRONG_SCROLL_DESCIPTION1 = "Fireblast";

    private String RIGHT_SCROLL_DESCIPTION1 = "Invisibility";
    private Long RIGHT_SCROLL_ID1 = 0L;
    private String JSON_RIGHT_SCROLL_ID1 = "{\"scroll_id\":0,\"description\":\"Invisibility\",\"durability\":100," +
            "\"creation_date\":\"2014-11-30\",\"mana_cost\":0,\"mage_id\":null}";

    private String RIGHT_SCROLL_DESCIPTION2 = "Blink";
    private Long RIGHT_SCROLL_ID2 = 1L;
    private String JSON_RIGHT_SCROLL_ID2 = "{\"scroll_id\":1,\"description\":\"Blink\",\"durability\":100," +
            "\"creation_date\":\"2014-11-30\",\"mana_cost\":0,\"mage_id\":null}";

    private String RIGHT_SCROLL_DESCIPTION3 = "Helicopter";
    private Long RIGHT_SCROLL_ID3 = 2L;
    private String JSON_RIGHT_SCROLL_ID3 = "{\"scroll_id\":2,\"description\":\"Helicopter\",\"durability\":100," +
            "\"creation_date\":\"2014-11-30\",\"mana_cost\":0,\"mage_id\":null}";




    @Resource
    ScrollRestController scrollRestController;

    @Autowired
    MagicScrollService magicScrollService;

    @After
    public void down() {
        reset(magicScrollService);
    }

    @Before
    public void setUp() {
        this.mockMvc = standaloneSetup(scrollRestController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    //Tests for getScrollById
    @Test
    public void getScrollById() throws Exception{

        magicScrollService.getMagicScrollById(RIGHT_SCROLL_ID1);
        expectLastCall().andReturn(getExistScroll(RIGHT_SCROLL_ID1));
        replay(magicScrollService);
        this.mockMvc.perform(
                get(MAIN_URL + RIGHT_SCROLL_ID1)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(JSON_RIGHT_SCROLL_ID1));
        verify(magicScrollService);
    }

    @Test
    public void getScrollByIncorrectId() throws Exception{

        magicScrollService.getMagicScrollById(WRONG_SCROLL_ID1);
        expectLastCall().andThrow(new NoItemFoundException("Bad id","",null));
        replay(magicScrollService);
        this.mockMvc.perform(
                get(MAIN_URL + WRONG_SCROLL_ID1)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("\"Bad id\""));
        verify(magicScrollService);
    }

    //Tests for getScrolls by Date
    @Test
    public void getAllScrollsAfterDate() throws  Exception{

        LocalDate localDate = new LocalDate();

        magicScrollService.getAllMagicScrollsAfterDate(localDate);
        expectLastCall().andReturn(getAllExistScrolls());
        replay(magicScrollService);

        this.mockMvc.perform(
                get(MAIN_URL+"filter/after/"+localDate)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("["+JSON_RIGHT_SCROLL_ID1 +","+JSON_RIGHT_SCROLL_ID2+","+JSON_RIGHT_SCROLL_ID3+"]"));
        verify(magicScrollService);
    }

    @Test
    public void getAllScrollsBeforeDate() throws  Exception{

        LocalDate localDate = new LocalDate();

        magicScrollService.getAllMagicScrollsBeforeDate(localDate);
        expectLastCall().andReturn(getAllExistScrolls());
        replay(magicScrollService);

        this.mockMvc.perform(
                get(MAIN_URL+"filter/before/"+localDate)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("["+JSON_RIGHT_SCROLL_ID1 +","+JSON_RIGHT_SCROLL_ID2+","+JSON_RIGHT_SCROLL_ID3+"]"));
        verify(magicScrollService);
    }

    @Test
    public void getAllScrollsBetweenDates() throws  Exception{

        LocalDate beforeLocalDate = new LocalDate();
        LocalDate afterLocalDate = new LocalDate();

        magicScrollService.getAllMagicScrollsBetweenDates(beforeLocalDate, afterLocalDate );
        expectLastCall().andReturn(getAllExistScrolls());
        replay(magicScrollService);

        this.mockMvc.perform(
                get(MAIN_URL+"filter/between/" + beforeLocalDate + "/" + afterLocalDate)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("["+JSON_RIGHT_SCROLL_ID1 +","+JSON_RIGHT_SCROLL_ID2+","+JSON_RIGHT_SCROLL_ID3+"]"));
        verify(magicScrollService);
    }

    //Tests for getScrolls
    @Test
    public void getAllScrolls() throws  Exception{

        magicScrollService.getAllMagicScrolls();
        expectLastCall().andReturn(getAllExistScrolls());
        replay(magicScrollService);

        this.mockMvc.perform(
                get(MAIN_URL)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("["+JSON_RIGHT_SCROLL_ID1 +","+JSON_RIGHT_SCROLL_ID2+","+JSON_RIGHT_SCROLL_ID3+"]"));
        verify(magicScrollService);
    }

    @Test
    public void getEmptyAllScrolls() throws  Exception{

        magicScrollService.getAllMagicScrolls();
        expectLastCall().andThrow(new NoItemsFoundException("No items", ""));
        replay(magicScrollService);

        this.mockMvc.perform(
                get(MAIN_URL)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("\"No items\""));
        verify(magicScrollService);
    }

    //Tests for getScrollByDescription
    @Test
    public void getScrollByDescription() throws Exception{

        magicScrollService.getMagicScrollByDescription(RIGHT_SCROLL_DESCIPTION1);
        expectLastCall().andReturn(getExistScroll(RIGHT_SCROLL_ID1, RIGHT_SCROLL_DESCIPTION1));
        replay(magicScrollService);
        this.mockMvc.perform(
                get(MAIN_URL + "description/"+ RIGHT_SCROLL_DESCIPTION1)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(JSON_RIGHT_SCROLL_ID1));
        verify(magicScrollService);
    }

    @Test
    public void getScrollByIncorrectDescription() throws Exception{

        magicScrollService.getMagicScrollByDescription(WRONG_SCROLL_DESCIPTION1);
        expectLastCall().andThrow(new NoItemFoundException("Bad description", "", null));
        replay(magicScrollService);
        this.mockMvc.perform(
                get(MAIN_URL + "description/"+ WRONG_SCROLL_DESCIPTION1)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("\"Bad description\""));
        verify(magicScrollService);
    }

    //Tests for addScroll
    @Test
    public void addScroll() throws Exception {

        MagicScroll scroll = getExistScroll(RIGHT_SCROLL_ID1);
        String userJson = new ObjectMapper().writeValueAsString(scroll);

        magicScrollService.addMagicScroll(scroll);
        expectLastCall().andReturn(new Long(0L));
        replay(magicScrollService);

        this.mockMvc.perform(
                post(MAIN_URL)
                        .content(userJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(new Long(0L).toString()));
        verify(magicScrollService);
    }

    @Test
    public void addScrollWithIncorrectData() throws Exception {

        MagicScroll scroll = getExistScroll(WRONG_SCROLL_ID1);
        String userJson = new ObjectMapper().writeValueAsString(scroll);

        magicScrollService.addMagicScroll(scroll);
        expectLastCall().andThrow(new BadInsertException("Incorrect Id", "", null));
        replay(magicScrollService);

        this.mockMvc.perform(
                post(MAIN_URL)
                        .content(userJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("\"Incorrect Id\""));
        verify(magicScrollService);
    }

    //Tests for updateScroll
    @Test
    public void updateScroll() throws Exception {

        MagicScroll scroll = getExistScroll(RIGHT_SCROLL_ID1);
        String userJson = new ObjectMapper().writeValueAsString(scroll);

        magicScrollService.updateMagicScroll(scroll);
        expectLastCall();
        replay(magicScrollService);

        this.mockMvc.perform(
                put(MAIN_URL)
                        .content(userJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
        verify(magicScrollService);
    }

    @Test
    public void updateScrollWithIncorrectData() throws Exception {

        MagicScroll scroll = getExistScroll(WRONG_SCROLL_ID1);
        String userJson = new ObjectMapper().writeValueAsString(scroll);

        magicScrollService.updateMagicScroll(scroll);
        expectLastCall().andThrow(new BadUpdateException("Incorrect id", "", null));
        replay(magicScrollService);

        this.mockMvc.perform(
                put(MAIN_URL)
                        .content(userJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("\"Incorrect id\""));;
        verify(magicScrollService);
    }

    //Test for removeScroll
    @Test
    public void removeScroll() throws Exception {

        magicScrollService.removeMagicScroll(RIGHT_SCROLL_ID1);
        expectLastCall();
        replay(magicScrollService);

        this.mockMvc.perform(
                delete(MAIN_URL+RIGHT_SCROLL_ID1)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());

        verify(magicScrollService);
    }

    @Test
    public void removeScrollWithIncorrectId() throws Exception {

        magicScrollService.removeMagicScroll(WRONG_SCROLL_ID1);
        expectLastCall().andThrow(new BadRemoveException("Incorrect Id", "", null));
        replay(magicScrollService);

        this.mockMvc.perform(
                delete(MAIN_URL+WRONG_SCROLL_ID1)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(content().string("\"Incorrect Id\""));

        verify(magicScrollService);
    }

}
