package com.epam.brest.task.dao;

import com.epam.brest.task.domain.MagicScroll;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
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
        MagicScroll scroll = new MagicScroll(null, "Fireball", 100L, new LocalDate(),50L);
        System.out.println(scroll);

        Long id = magicScrollDAO.addMagicScroll(scroll);
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

        Long id=0L;

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

        MagicScroll oldScroll = magicScrollDAO.getMagicScrollById(0L);
        oldScroll.setMana_cost(1500L);
        oldScroll.setDescription("Pyroblast");
        oldScroll.setCreation_date(new LocalDate(2018,12,31));
        magicScrollDAO.updateMagicScroll(oldScroll);
        MagicScroll newscroll=magicScrollDAO.getMagicScrollById(0L);

        Assert.assertEquals(oldScroll, newscroll);

    }

    @Test
    public void AddGetTest() {

        MagicScroll scroll = new MagicScroll(null, "Nova", 15L, new LocalDate(1999,11,10),400L);
        Long id=magicScrollDAO.addMagicScroll(scroll);
        scroll.setScroll_id(id);
        MagicScroll scroll2 = magicScrollDAO.getMagicScrollById(id);

        Assert.assertEquals(scroll, scroll2);

    }


}
