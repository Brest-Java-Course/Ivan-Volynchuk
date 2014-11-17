package com.epam.brest.task.dao;

import com.epam.brest.task.domain.MagicScroll;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import static com.epam.brest.task.dao.tools.TestMagicScrollFactory.getNewScroll;

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
        MagicScroll scroll = getNewScroll();

        Long id = magicScrollDAO.addMagicScroll(scroll);
        Assert.assertNotNull(id);
        scroll.setScroll_id(id);
        Assert.assertEquals(scroll, magicScrollDAO.getMagicScrollById(id));

        System.out.println( magicScrollDAO.getMagicScrollById(id));

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
        Assert.assertEquals(scroll.getScroll_id(),id);

    }

    @Test
    public void getScrollByDescription() {

        String description="Frostball";

        MagicScroll scroll = magicScrollDAO.getMagicScrollByDescription(description);
        Assert.assertEquals(scroll.getDescription(),description);

    }

    @Test
    public void removeScrollById() {

        magicScrollDAO.removeMagicScroll(2L);
        List<MagicScroll> scrolls = magicScrollDAO.getAllMagicScrolls();
        Assert.assertEquals(scrolls.size(),7);
        magicScrollDAO.removeMagicScroll(1L);
        scrolls = magicScrollDAO.getAllMagicScrolls();
        Assert.assertEquals(scrolls.size(),6);
    }

    @Test
    public void updateScroll() {

        MagicScroll oldScroll = magicScrollDAO.getMagicScrollById(1L);
        oldScroll.setMana_cost(1500L);
        oldScroll.setDescription("Pyroblast");
        oldScroll.setCreation_date(new LocalDate(2018, 12, 31));
        magicScrollDAO.updateMagicScroll(oldScroll);
        MagicScroll newscroll=magicScrollDAO.getMagicScrollById(1L);

        Assert.assertEquals(oldScroll, newscroll);

    }

    @Test
    public void updateScrollWithNullMageId() {

        Long scrollId=1L;

        MagicScroll oldScroll = magicScrollDAO.getMagicScrollById(scrollId);
        oldScroll.setMage_id(null);
        magicScrollDAO.updateMagicScroll(oldScroll);

        Assert.assertNull(magicScrollDAO.getMagicScrollById(scrollId).getMage_id());
    }

    @Test
    public void getScrollsOfMage() {

        Long MageId = 1L;

        List<MagicScroll> scrolls = new LinkedList<MagicScroll>();
        scrolls.add(new MagicScroll(1L,"Frostball", 800L, new LocalDate(2008,03,02), 560L, 1L));
        scrolls.add(new MagicScroll(5L,"ArcanMissles", 100L, new LocalDate(2012,03,02), 55L, 1L));
        scrolls.add(new MagicScroll(7L,"Rocket", 100L, new LocalDate(2012,03,02), 55L, 1L));

        List<MagicScroll> scrollsR= magicScrollDAO.getMagicScrollsByMageId(MageId);
        Assert.assertEquals(scrolls, scrollsR);

    }

    @Test
    public void clearMagicId() {

        Long MageId = 1L;

        Integer size = magicScrollDAO.getMagicScrollsByMageId(MageId).size();
        Assert.assertNotNull(size);
        magicScrollDAO.clearScrollsByMagicId(MageId);
        Integer newsize = magicScrollDAO.getMagicScrollsByMageId(MageId).size();

        Assert.assertEquals(newsize, new Integer(0));
    }


}
