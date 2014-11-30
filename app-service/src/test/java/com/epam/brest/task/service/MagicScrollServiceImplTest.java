package com.epam.brest.task.service;

import com.epam.brest.task.domain.Mage;
import com.epam.brest.task.domain.MagicScroll;
import com.epam.brest.task.service.Exception.*;
import junit.framework.Assert;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.brest.task.dao.tools.TestMagicScrollFactory.getExistScroll;
import static com.epam.brest.task.dao.tools.TestMagicScrollFactory.getNewScroll;

/**
 * Created by fieldistor on 16.11.14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-service-context-test.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@Transactional

public class MagicScrollServiceImplTest {

    private final static Long correctScrollId1 = 0L;

    private final static Long correctMageId = 0L;
    private final static Long amountScrollsOfMage1 = 5L;

    private final static Long correctMageIdWithoutScrolls = 5L;

    private final static Long amountScrollsWithoutMage = 5L;

    private final static String correctScrollDescription = "Frostball";
    private final static Long amountScrolls = 17L;

    private final static LocalDate correctAfterAndBeforeDate = new LocalDate(2009,11,8);

    private final static LocalDate incorrectAfterDate = new LocalDate(2222,11,8);
    private final static Long amoutScrollsCorrectAfterDate = 12L;

    private final static LocalDate incorrectBeforeDate = new LocalDate(1024,11,8);
    private final static Long amoutScrollsCorrectBeforeDate = 5L;

    private final static LocalDate startCorrectBetweenDate = new LocalDate(2004,4,4);
    private final static LocalDate endCorrectBetweenDate = new LocalDate(2012,11,8);

    @Autowired
    private MagicScrollService magicScrollService;


    // Tests for getAllMagicScrolls
    @Test
    public void getAllScrolls() {

        List<MagicScroll> scrolls = magicScrollService.getAllMagicScrolls();
        Assert.assertEquals(new Long(scrolls.size()), amountScrolls);

    }

    @Test(expected = NoItemsFoundException.class)
    public void getEmptyListOfScrolls() {

        List<MagicScroll> scrolls = magicScrollService.getAllMagicScrolls();

        for( MagicScroll scroll:scrolls) {
            magicScrollService.removeMagicScroll(scroll.getScroll_id());
        }

        magicScrollService.getAllMagicScrolls();
    }

    // Tests for getLimitScrolls
    @Test
    public void getLimitScrolls() {

        Long page = 3L;
        Long per_page = 2L;

        List<MagicScroll> scrolls = magicScrollService.getLimitScrolls(page, per_page);
        Assert.assertTrue(scrolls.size()<=per_page);
        for(MagicScroll scroll:scrolls) {

            Long scrollId = scroll.getScroll_id();
            Assert.assertTrue(scrollId>=page*per_page & scrollId<=page*per_page+per_page-1);
        }

    }

    @Test
    public void getFullScrolls() {

        Long page = 0L;
        Long per_page = 99L;

        List<MagicScroll> scrolls = magicScrollService.getLimitScrolls(page, per_page);
        Assert.assertEquals(new Long(scrolls.size()), amountScrolls);

    }

    @Test(expected = NoItemsFoundException.class)
    public void getEmptyLimitScrolls() {

        Long page = 3L;
        Long per_page = 99L;

        List<MagicScroll> scrolls = magicScrollService.getLimitScrolls(page, per_page);
    }

    //Test for removeMagicScroll
    @Test
    public void removeScrollById() {

        Long size = magicScrollService.amountScrolls();
        magicScrollService.removeMagicScroll(correctScrollId1);
        Assert.assertEquals(--size, magicScrollService.amountScrolls());
    }

    //Tests for getMagicScrollById
    @Test
    public void getScrollByid() {


        MagicScroll scroll = magicScrollService.getMagicScrollById(correctScrollId1);
        Assert.assertEquals(scroll.getScroll_id(), correctScrollId1);
    }

    @Test
    public void addAndGetScrollById() {

        MagicScroll scroll = getNewScroll();
        Long id = magicScrollService.addMagicScroll(scroll);
        scroll.setScroll_id(id);
        Assert.assertNotNull(id);
        Assert.assertEquals(scroll, magicScrollService.getMagicScrollById(id));
    }

    //Tests for getMagicScrollByDescription
    @Test
    public void getScrollByDescription() {

        MagicScroll scroll = magicScrollService.getMagicScrollByDescription(correctScrollDescription);
        Assert.assertEquals(scroll.getDescription(), correctScrollDescription);
    }

    @Test
    public void addAndGetScrollByDescription() {

        MagicScroll scroll = getNewScroll();
        Long id = magicScrollService.addMagicScroll(scroll);
        scroll.setScroll_id(id);
        Assert.assertNotNull(id);
        Assert.assertEquals(scroll, magicScrollService.getMagicScrollByDescription(scroll.getDescription()));
    }

    //Test for updateScroll
    @Test
    public void updateScroll() {

        MagicScroll scroll = magicScrollService.getMagicScrollById(correctScrollId1);
        scroll.setMage_id(3L);
        scroll.setDescription("TestScroll");
        magicScrollService.updateMagicScroll(scroll);
        Assert.assertEquals(scroll, magicScrollService.getMagicScrollById(scroll.getScroll_id()));
    }


    @Test(expected = BadUpdateException.class)
    public void updateIncorrectScroll() {

        MagicScroll scroll = getNewScroll();
        magicScrollService.updateMagicScroll(scroll);
    }


    //Test for getMagicScrollsByMageId
    @Test
    public void getMagicScrollsByMageId() {

        List<MagicScroll> scrolls = magicScrollService.getMagicScrollsByMageId(correctMageId);
        Assert.assertEquals(new Long(scrolls.size()),amountScrollsOfMage1);
        for(MagicScroll scroll:scrolls) {
            Assert.assertEquals(correctMageId, scroll.getMage_id());
        }
    }

    @Test(expected = NoItemFoundException.class)
    public void getEmptyMagicScrollsByMageId() {

        List<MagicScroll> scrolls = magicScrollService.getMagicScrollsByMageId(correctMageIdWithoutScrolls);

    }

    //Test for addScroll
    @Test
    public void addScroll() {

        MagicScroll scroll = getNewScroll();
        Long id = magicScrollService.addMagicScroll(scroll);
        Assert.assertNotNull(id);
        scroll.setScroll_id(id);
        MagicScroll scroll2 = magicScrollService.getMagicScrollById(id);
        Assert.assertEquals(scroll,scroll2);
    }

    // Tests for getLimitMagicScrollsByMageId
    @Test
    public void getLimitMagicScrollsByMageId() {

        Long page = 1L;
        Long per_page = 2L;

        List<MagicScroll> scrolls = magicScrollService.getLimitMagicScrollsByMageId(correctMageId ,page, per_page);
        for(MagicScroll scroll:scrolls) {
            Assert.assertEquals(scroll.getMage_id(), correctMageId);
        }
        Assert.assertTrue(scrolls.size()<=per_page);

    }

    @Test
    public void getFullLimitMagicScrollsByMageId() {

        Long page = 0L;
        Long per_page = 99L;

        List<MagicScroll> scrolls = magicScrollService.getLimitMagicScrollsByMageId(correctMageId ,page, per_page);
        Assert.assertEquals(new Long(scrolls.size()), amountScrollsOfMage1);

    }

    @Test(expected = NoItemsFoundException.class)
    public void getEmptyLimitMagicScrollsByMageId() {

        Long page = 3L;
        Long per_page = 99L;

        List<MagicScroll> scrolls = magicScrollService.getLimitMagicScrollsByMageId(correctMageId ,page, per_page);
    }

    // Tests for getMagicScrollsWithoutMage
    @Test
    public void getMagicScrollsWithoutMage() {

        List<MagicScroll> scrolls = magicScrollService.getMagicScrollsWithoutMage();
        Assert.assertEquals(amountScrollsWithoutMage, new Long(scrolls.size()));
        for(MagicScroll scroll:scrolls) {
            Assert.assertNull(scroll.getMage_id());
        }
    }

    @Test(expected = NoItemsFoundException.class)
    public void getEmptyMagicScrollsWithoutMage() {

        List<MagicScroll> scrolls = magicScrollService.getMagicScrollsWithoutMage();
        for(MagicScroll scroll:scrolls) {
            magicScrollService.removeMagicScroll(scroll.getScroll_id());
        }

        magicScrollService.getMagicScrollsWithoutMage();
    }

    // Tests for getLimitMagicScrollsWithoutMage
    @Test
    public void getLimitMagicScrollsWithoutMage() {

        Long page = 1L;
        Long per_page = 3L;

        List<MagicScroll> scrolls = magicScrollService.getLimitMagicScrollsWithoutMage(page, per_page);
        for(MagicScroll scroll:scrolls) {
            Assert.assertNull(scroll.getMage_id());
        }
        Assert.assertTrue(scrolls.size()<=per_page);
    }

    @Test
    public void getFullLimitMagicScrollsWithoutMage() {

        Long page = 0L;
        Long per_page = 99L;

        List<MagicScroll> scrolls = magicScrollService.getLimitMagicScrollsWithoutMage(page, per_page);
        Assert.assertEquals(new Long(scrolls.size()), amountScrollsWithoutMage);
    }

    @Test(expected = NoItemsFoundException.class)
    public void getEmptyLimitMagicScrollsWithoutMage() {

        Long page = 99L;
        Long per_page = 3L;

        magicScrollService.getLimitMagicScrollsWithoutMage(page, per_page);
    }

    //Tests for getAllMagicScrollsAfterDate
    @Test
    public void getAllMagicScrollsAfterDate() {

        List<MagicScroll> scrolls = magicScrollService.getAllMagicScrollsAfterDate(correctAfterAndBeforeDate);
        Assert.assertTrue(scrolls.size() == amoutScrollsCorrectAfterDate);
        for(MagicScroll scroll:scrolls) {
            Assert.assertTrue(scroll.getCreation_date().isAfter(correctAfterAndBeforeDate));
        }
    }
    @Test(expected = NotFoundException.class)
    public void getEmptyAllMagicScrollsAfterDate(){

        List<MagicScroll> scrolls = magicScrollService.getAllMagicScrollsAfterDate(incorrectAfterDate);

    }
    @Test(expected = NotFoundException.class)
    public void getAllMagicScrollAfterDateWithNullDate() {

        magicScrollService.getAllMagicScrollsAfterDate(null);
    }

    //Tests for getAllMagicScrollsBeforeDate
    @Test
    public void getAllMagicScrollsBeforeDate() {

        List<MagicScroll> scrolls = magicScrollService.getAllMagicScrollsBeforeDate(correctAfterAndBeforeDate);
        Assert.assertTrue(scrolls.size() == amoutScrollsCorrectBeforeDate);
        for(MagicScroll scroll:scrolls) {
            Assert.assertTrue(scroll.getCreation_date().isBefore(correctAfterAndBeforeDate));
        }
    }

    @Test(expected = NotFoundException.class)
    public void getEmptyAllMagicScrollsBeforeDate(){

        List<MagicScroll> scrolls = magicScrollService.getAllMagicScrollsBeforeDate(incorrectBeforeDate);

    }
    @Test(expected = NotFoundException.class)
    public void getAllMagicScrollBeforeDateWithNullDate() {

        magicScrollService.getAllMagicScrollsBeforeDate(null);
    }

    //Tests for getAllMagicScrollsBetweenDates
    @Test
    public void getAllMagicScrollsBetweenDates() {

        List<MagicScroll> scrolls = magicScrollService.getAllMagicScrollsBetweenDates(startCorrectBetweenDate, endCorrectBetweenDate);
        for(MagicScroll scroll:scrolls) {
            Assert.assertTrue(scroll.getCreation_date().isAfter(startCorrectBetweenDate) & scroll.getCreation_date().isBefore(endCorrectBetweenDate));
        }
    }
    @Test(expected = NotFoundException.class)
    public void getEmptyAllMagicScrollsBetweenDates(){

        List<MagicScroll> scrolls = magicScrollService.getAllMagicScrollsBetweenDates(endCorrectBetweenDate, endCorrectBetweenDate);

    }
    @Test(expected = NotFoundException.class)
    public void getAllMagicScrollsBetweenDatesWithNullAfterDate() {

        magicScrollService.getAllMagicScrollsBetweenDates(null, new LocalDate());
    }
    @Test(expected = NotFoundException.class)
    public void getAllMagicScrollBetweenDateWithNullBeforeDate() {

        magicScrollService.getAllMagicScrollsBetweenDates(new LocalDate(), null);
    }

    //Test for amountScrolls
    @Test
    public void amountScrolls() {

        Assert.assertEquals(magicScrollService.amountScrolls(), amountScrolls);
    }

    //Test for amountScrollsByMageId
    @Test
    public void amountScrollsByMageId() {

        Assert.assertEquals(amountScrollsOfMage1, magicScrollService.amountScrollsByMageId(correctMageId));
    }

    //Test for amountScrollsWithoutMage
    @Test
    public void amountScrollsWithoutMage() {

        Assert.assertEquals(amountScrollsWithoutMage, magicScrollService.amountScrollsWithoutMage());
    }




}
