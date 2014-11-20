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

    private int amount_scrolls = 17;

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
        Assert.assertEquals(scrolls.size(),amount_scrolls-1);
        magicScrollDAO.removeMagicScroll(1L);
        scrolls = magicScrollDAO.getAllMagicScrolls();
        Assert.assertEquals(scrolls.size(),amount_scrolls-2);
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
        for(MagicScroll scroll:scrolls) {
            Assert.assertEquals(scroll.getMage_id(), MageId);
        }

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

    //Tests for getMagicScrollsWithoutMage
    @Test
    public void getMagicScrollsWithoutMage() {

        List<MagicScroll> scrolls = magicScrollDAO.getMagicScrollsWithoutMage();
        for(MagicScroll scroll:scrolls) {
            Assert.assertNull(scroll.getMage_id());
        }
    }

    //Tests for getLimitScrolls
    @Test
    public void getLimitScrolls() {

        Long amt = 9L;
        Long n_from = 2L;

        List<MagicScroll> scrolls = magicScrollDAO.getLimitScrolls(amt, n_from);
        Assert.assertTrue(scrolls.size()<=amt);
        for(MagicScroll scroll:scrolls) {
            Long scroll_id = scroll.getScroll_id();
            Assert.assertTrue(scroll_id>=n_from && scroll_id<=n_from+amt-1);
        }
    }

    //Tests for getLimitMagicScrollsWithoutMage
    @Test
    public void getLimitMagicScrollsWithoutMage() {

        Long amt = 3L;
        Long n_from = 1L;

        List<MagicScroll> scrolls = magicScrollDAO.getLimitMagicScrollsWithoutMage(amt, n_from);
        Assert.assertTrue(scrolls.size()<=amt);
        for(MagicScroll scroll:scrolls) {
            Assert.assertNull(scroll.getMage_id());
        }

    }

    //Tests for getLimitMagicScrollsByMageId
    @Test
    public void getLimitMagicScrollsByMageId() {

        Long mage_id = 0L;

        Long amt = 4L;
        Long n_from = 1L;

        List<MagicScroll> scrolls = magicScrollDAO.getLimitMagicScrollsByMageId(mage_id, amt, n_from);
        Assert.assertTrue(scrolls.size()<=amt);
        for(MagicScroll scroll:scrolls) {
            Assert.assertEquals(scroll.getMage_id(), mage_id);
        }
    }

    //Tests for amountScrolls
    @Test
    public void amountScrolls() {

        Long amt = magicScrollDAO.amountScrolls();
        int size = magicScrollDAO.getAllMagicScrolls().size();
        Assert.assertTrue(amt==size);
    }
    //Tests for amountScrollsByMageId
    @Test
    public void amountScrollsByMageId() {

        Long mageId = 0L;

        Long amt = magicScrollDAO.amountScrollsByMageId(mageId);
        int size = magicScrollDAO.getMagicScrollsByMageId(mageId).size();
        Assert.assertTrue(amt==size);
    }


}
