package com.epam.brest.task.dao;

import com.epam.brest.task.domain.MagicScroll;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by fieldistor on 15.11.14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-dao-context-test.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@Transactional
public class MagicScrollDAOImplTest {

    @Autowired
    private MagicScrollDAO magicScrollDAO;

    @Test
    public void TestAddUser() {

        Long id = magicScrollDAO.addMagicScroll(new MagicScroll(null, "Fireball", 100L, new Date(),50L));
        System.out.println(id);
        Assert.assertNotNull(id);
    }

    @Test
    public void getAllScrolls() {

        List<MagicScroll> scrolls = magicScrollDAO.getAllMagicScrolls();
        System.out.println(scrolls);

    }

    @Test
    public void getScrollById() {

        Long id=1L;

        MagicScroll scroll = magicScrollDAO.getMagicScrollById(id);
        System.out.println(scroll);
        Assert.assertEquals(scroll.getScroll_id(),id);

    }

    @Test
    public void getScrollByDescription() {

        String description="Fireball";

        MagicScroll scroll = magicScrollDAO.getMagicScrollByDescription("Fireball");
        System.out.println(scroll);
        Assert.assertEquals(scroll.getDescription(),description);

    }

    @Test
    public void removeScrollById() {

        magicScrollDAO.removeMagicScroll(1L);
        List<MagicScroll> scrolls = magicScrollDAO.getAllMagicScrolls();
        System.out.println(scrolls);
        Assert.assertEquals(scrolls.size(),1);
        magicScrollDAO.removeMagicScroll(0L);
        scrolls = magicScrollDAO.getAllMagicScrolls();
        Assert.assertEquals(scrolls.size(),0);
    }

    @Test
    public void updateScroll() {

        MagicScroll scroll = new MagicScroll(0L,"FrostNova", 100L, new Date(),2500L);
        magicScrollDAO.updateMagicScroll(scroll);
     // Assert.assertEquals(scroll, magicScrollDAO.getMagicScrollById(0L));
    }

    @Test
    public void setupDate() {

        MagicScroll scroll = new MagicScroll(5L,"MysteryArrows", 5L, new Date(),15000L);
        magicScrollDAO.addMagicScroll(scroll);
        MagicScroll scroll2 = magicScrollDAO.getMagicScrollById(2L);
        System.out.println("Input: "+scroll+"\nOutput:"+scroll2);
    }
}
