package com.epam.brest.task.service;

import com.epam.brest.task.domain.MagicScroll;
import com.epam.brest.task.service.Exception.*;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.brest.task.dao.tools.TestMagicScrollFactory.getNewScroll;

/**
 * Created by fieldistor on 16.11.14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-service-context-test.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@Transactional

public class MagicScrollServiceImplTest {

    Long incorrectId = 99L;
    Long correctId1 = 0L;
    Long correctId2 = 1L;

    int amountScrolls = 17;

    @Autowired
    private MagicScrollService magicScrollService;


    // Tests for getAllMagicScrolls
    @Test
    public void getAllScrolls() {

        List<MagicScroll> scrolls = magicScrollService.getAllMagicScrolls();
        Assert.assertEquals(scrolls.size(), amountScrolls);

    }

    @Test(expected = NotFoundException.class)
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

    }

    @Test(expected = NotFoundException.class)
    public void getEmptyLimitScrolls() {

        Long page = 3L;
        Long per_page = 99L;

        List<MagicScroll> scrolls = magicScrollService.getLimitScrolls(page, per_page);
    }

    //Test for removeMagicScroll
    @Test
    public void removeScrollById() {

        int size = magicScrollService.getAllMagicScrolls().size();
        magicScrollService.removeMagicScroll(correctId1);
        Assert.assertEquals(size-1, magicScrollService.getAllMagicScrolls().size());
    }

    @Test(expected = BadRemoveException.class)
    public void removeScrollException() {

        magicScrollService.removeMagicScroll(incorrectId);
    }

    @Test(expected = BadRemoveException.class)
    public void removenullScroll() {

        magicScrollService.removeMagicScroll(null);
    }

    //Tests for getMagicScrollById
    @Test
    public void getScrollByid() {


        MagicScroll scroll = magicScrollService.getMagicScrollById(correctId1);
        Assert.assertEquals(scroll.getScroll_id(), correctId1);
    }

    @Test(expected = NotFoundException.class)
    public void getEmptyScrollByid() {

        MagicScroll scroll = magicScrollService.getMagicScrollById(incorrectId);
    }

    @Test(expected = NotFoundException.class)
    public void getNullScrollByid() {

        MagicScroll scroll = magicScrollService.getMagicScrollById(null);
    }

    //Tests for getMagicScrollByDescription
    @Test
    public void getScrollByDescription() {

        MagicScroll scroll = magicScrollService.getMagicScrollByDescription("Frostball");
        Assert.assertEquals(scroll.getDescription(), "Frostball");
    }

    @Test(expected = NotFoundException.class)
    public void getEmptyScrollByDescription() {

        MagicScroll scroll = magicScrollService.getMagicScrollByDescription(null);
    }

    @Test(expected = NotFoundException.class)
    public void getIncorrectScrollByDescription() {

        MagicScroll scroll = magicScrollService.getMagicScrollByDescription("Burgerball");
    }
    //Test for update scroll
    @Test
    public void updateScroll() {

        MagicScroll scroll = magicScrollService.getMagicScrollById(correctId1);
        scroll.setMage_id(3L);
        scroll.setDescription("TestScroll");
        magicScrollService.updateMagicScroll(scroll);
        Assert.assertEquals(scroll, magicScrollService.getMagicScrollById(scroll.getScroll_id()));
    }

    @Test(expected = BadUpdateException.class)
    public void updateScrollWithIncorrectId() {

        MagicScroll scroll = magicScrollService.getMagicScrollById(correctId1);
        scroll.setScroll_id(incorrectId);
        magicScrollService.updateMagicScroll(scroll);
    }

    @Test(expected = BadUpdateException.class)
    public void updateScrollWithNullDecription() {

        MagicScroll scroll = magicScrollService.getMagicScrollById(correctId1);
        scroll.setDescription(null);
        magicScrollService.updateMagicScroll(scroll);
    }

    @Test(expected = BadUpdateException.class)
    public void updateScrollWithNullDate() {

        MagicScroll scroll = magicScrollService.getMagicScrollById(correctId1);
        scroll.setCreation_date(null);
        magicScrollService.updateMagicScroll(scroll);
    }

    @Test(expected = BadUpdateException.class)
    public void updateScrollWithNullId() {

        MagicScroll scroll = magicScrollService.getMagicScrollById(correctId1);
        scroll.setScroll_id(null);
        magicScrollService.updateMagicScroll(scroll);
    }

    //Test for getMagicScrollsByMageId
    @Test
    public void getMagicScrollsByMageId() {

        List<MagicScroll> scrolls = magicScrollService.getMagicScrollsByMageId(correctId1);
        for(MagicScroll scroll:scrolls) {
            Assert.assertEquals(correctId1, scroll.getMage_id());
        }
        System.out.println(scrolls);
    }

    @Test(expected = NotFoundException.class)
    public void getMagicScrollsByNullMageId() {

        List<MagicScroll> scrolls = magicScrollService.getMagicScrollsByMageId(null);

    }

    @Test(expected = NotFoundException.class)
    public void getMagicScrollsByIncorrectMageId() {

        List<MagicScroll> scrolls = magicScrollService.getMagicScrollsByMageId(incorrectId);

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

    @Test(expected = BadInsertException.class)
    public void addIncorrectScroll() {

        MagicScroll scroll = getNewScroll();
        scroll.setScroll_id(correctId1);
        Long id = magicScrollService.addMagicScroll(scroll);
    }

    // Tests for getLimitMagicScrollsByMageId
    @Test
    public void getLimitMagicScrollsByMageId() {

        Long mageId = 0L;

        Long page = 1L;
        Long per_page = 2L;

        List<MagicScroll> scrolls = magicScrollService.getLimitMagicScrollsByMageId(mageId ,page, per_page);
        for(MagicScroll scroll:scrolls) {
            Assert.assertEquals(scroll.getMage_id(), mageId);
        }
        Assert.assertTrue(scrolls.size()<=per_page);

    }

    @Test(expected = NotFoundException.class)
    public void getEmptyLimitMagicScrollsByMageId() {

        Long mageId = 0L;

        Long page = 3L;
        Long per_page = 99L;

        List<MagicScroll> scrolls = magicScrollService.getLimitMagicScrollsByMageId(mageId ,page, per_page);
    }

    // Tests for getMagicScrollsWithoutMage
    @Test
    public void getMagicScrollsWithoutMage() {

        List<MagicScroll> scrolls = magicScrollService.getMagicScrollsWithoutMage();
        for(MagicScroll scroll:scrolls) {
            Assert.assertNull(scroll.getMage_id());
        }
    }

    @Test(expected = NotFoundException.class)
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

    @Test(expected = NotFoundException.class)
    public void getEmptyLimitMagicScrollsWithoutMage() {

        Long page = 99L;
        Long per_page = 3L;

        magicScrollService.getLimitMagicScrollsWithoutMage(page, per_page);
    }



}
