package com.epam.brest.task.service;

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

import static com.epam.brest.task.dao.tolls.TestMagicScrollFactory.getNewScroll;

/**
 * Created by fieldistor on 16.11.14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-service-context-test.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@Transactional

public class MagicScrollServiceImplTest {

    @Autowired
    private MagicScrollService magicScrollService;


    // Tests for getAllMagicScrolls
    @Test
    public void getAllScrolls() {

        List<MagicScroll> scrolls = magicScrollService.getAllMagicScrolls();
        Assert.assertEquals(scrolls.size(),8);

    }

    @Test(expected = NotFoundException.class)
    public void getEmptyListOfScrolls() {

        List<MagicScroll> scrolls = magicScrollService.getAllMagicScrolls();

        for( MagicScroll scroll:scrolls) {
            magicScrollService.removeMagicScroll(scroll.getScroll_id());
        }

        magicScrollService.getAllMagicScrolls();
    }

    //Test for removeMagicScroll
    @Test
    public void removeScrollById() {

        int size = magicScrollService.getAllMagicScrolls().size();
        magicScrollService.removeMagicScroll(0L);
        Assert.assertEquals(size-1, magicScrollService.getAllMagicScrolls().size());
    }

    @Test(expected = BadRemoveException.class)
    public void removeScrollException() {

        magicScrollService.removeMagicScroll(10L);
    }

    @Test(expected = BadRemoveException.class)
    public void removenullScroll() {

        magicScrollService.removeMagicScroll(null);
    }

    //Tests for getMagicScrollById
    @Test
    public void getScrollByid() {

        Long id = 0L;
        MagicScroll scroll = magicScrollService.getMagicScrollById(id);
        Assert.assertEquals(scroll.getScroll_id(), new Long(id));
    }

    @Test(expected = NotFoundException.class)
    public void getEmptyScrollByid() {

        MagicScroll scroll = magicScrollService.getMagicScrollById(99L);
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

        MagicScroll scroll = magicScrollService.getMagicScrollById(0L);
        scroll.setMage_id(3L);
        scroll.setDescription("TestScroll");
        magicScrollService.updateMagicScroll(scroll);
        Assert.assertEquals(scroll, magicScrollService.getMagicScrollById(scroll.getScroll_id()));
    }

    @Test(expected = BadUpdateException.class)
    public void updateScrollWithIncorrectId() {

        MagicScroll scroll = magicScrollService.getMagicScrollById(1L);
        scroll.setScroll_id(99L);
        magicScrollService.updateMagicScroll(scroll);
    }

    @Test(expected = BadUpdateException.class)
    public void updateScrollWithNullDecription() {

        MagicScroll scroll = magicScrollService.getMagicScrollById(1L);
        scroll.setDescription(null);
        magicScrollService.updateMagicScroll(scroll);
    }

    @Test(expected = BadUpdateException.class)
    public void updateScrollWithNullDate() {

        MagicScroll scroll = magicScrollService.getMagicScrollById(1L);
        scroll.setCreation_date(null);
        magicScrollService.updateMagicScroll(scroll);
    }

    @Test(expected = BadUpdateException.class)
    public void updateScrollWithNullId() {

        MagicScroll scroll = magicScrollService.getMagicScrollById(1L);
        scroll.setScroll_id(null);
        magicScrollService.updateMagicScroll(scroll);
    }

    //Test for getMagicScrollsByMageId
    @Test
    public void getMagicScrollsByMageId() {

        Long mage_id=0L;

        List<MagicScroll> scrolls = magicScrollService.getMagicScrollsByMageId(mage_id);
        for(MagicScroll scroll:scrolls) {
            Assert.assertEquals(mage_id, scroll.getMage_id());
        }
        System.out.println(scrolls);
    }

    @Test(expected = NotFoundException.class)
    public void getMagicScrollsByNullMageId() {

        List<MagicScroll> scrolls = magicScrollService.getMagicScrollsByMageId(null);

    }

    @Test(expected = NotFoundException.class)
    public void getMagicScrollsByIncorrectMageId() {

        List<MagicScroll> scrolls = magicScrollService.getMagicScrollsByMageId(99L);

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
        scroll.setScroll_id(0L);
        Long id = magicScrollService.addMagicScroll(scroll);
    }





}
