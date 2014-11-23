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

    private final static Long correctScrollId1 = 0L;
    private final static Long incorrectScrollId = 99L;

    private final static Long correctMageId = 0L;
    private final static Long incorrectMageId = 99L;
    private final static Long amountScrollsOfMage1 = 5L;

    private final static Long amountScrollsWithoutMage = 5L;

    private final static String correctScrollDescription = "Frostball";
    private final static Long amount_scrolls = 17L;

    @Autowired
    private MagicScrollDAO magicScrollDAO;

    //Tests for addMagicScroll
    @Test
    public void AddScroll() {

        MagicScroll scroll = getNewScroll();

        Long id = magicScrollDAO.addMagicScroll(scroll);
        Assert.assertNotNull(id);
        scroll.setScroll_id(id);
        Assert.assertEquals(scroll, magicScrollDAO.getMagicScrollById(id));
        Long size = magicScrollDAO.amountScrolls();
        Assert.assertEquals(--size, amount_scrolls);

    }

    //Tests for getAllMagicScrolls
    @Test
    public void getAllScrolls() {

        List<MagicScroll> scrolls = magicScrollDAO.getAllMagicScrolls();
        Assert.assertEquals(new Long(scrolls.size()), amount_scrolls);

    }

    @Test
    public void getEmptyAllScrolls() {

        List<MagicScroll> scrolls = magicScrollDAO.getAllMagicScrolls();
        for(MagicScroll scroll:scrolls) {

            magicScrollDAO.removeMagicScroll(scroll.getScroll_id());
        }
        Assert.assertTrue(magicScrollDAO.getAllMagicScrolls().size() == 0);

    }

    //Tests for getMagicScrollById
    @Test
    public void getScrollById() {

        MagicScroll scroll = magicScrollDAO.getMagicScrollById(correctScrollId1);
        Assert.assertEquals(scroll.getScroll_id(), correctScrollId1);
    }

    @Test
    public void AddAndGetScrollById() {

        MagicScroll scroll = getNewScroll();

        Long id = magicScrollDAO.addMagicScroll(scroll);
        Assert.assertNotNull(id);
        scroll.setScroll_id(id);
        Assert.assertEquals(scroll, magicScrollDAO.getMagicScrollById(id));

    }
    //Tests for getMagicScrollByDescription
    @Test
    public void getScrollByDescription() {

        MagicScroll scroll = magicScrollDAO.getMagicScrollByDescription(correctScrollDescription);
        Assert.assertEquals(scroll.getDescription(), correctScrollDescription);
    }

    @Test
    public void AddAndGetScrollByDescription() {

        MagicScroll scroll = getNewScroll();

        Long id = magicScrollDAO.addMagicScroll(scroll);
        Assert.assertNotNull(id);
        scroll.setScroll_id(id);
        Assert.assertEquals(scroll, magicScrollDAO.getMagicScrollByDescription(scroll.getDescription()));
    }

    //Tests for removeMagicScroll
    @Test
    public void removeScrollById() {

        magicScrollDAO.removeMagicScroll(incorrectScrollId);
        Long size1 = magicScrollDAO.amountScrolls();
        Assert.assertEquals(size1, amount_scrolls);

        magicScrollDAO.removeMagicScroll(correctScrollId1);
        Long size2 = magicScrollDAO.amountScrolls();
        Assert.assertEquals(++size2, amount_scrolls);
    }

    //Tests for updateMagicScroll
    @Test
    public void updateScroll() {

        MagicScroll oldScroll = magicScrollDAO.getMagicScrollById(correctScrollId1);
        oldScroll.setMana_cost(1500L);
        oldScroll.setDescription("Pyroblast");
        oldScroll.setCreation_date(new LocalDate(2018, 12, 31));
        magicScrollDAO.updateMagicScroll(oldScroll);

        MagicScroll newscroll = magicScrollDAO.getMagicScrollById(oldScroll.getScroll_id());
        Assert.assertEquals(oldScroll, newscroll);

    }

    @Test
    public void updateScrollWithIncorrectScrollId() {

        MagicScroll original = magicScrollDAO.getMagicScrollById(correctScrollId1);
        MagicScroll oldScroll = magicScrollDAO.getMagicScrollById(correctScrollId1);

        oldScroll.setScroll_id(incorrectScrollId);
        oldScroll.setMana_cost(1500L);
        oldScroll.setDescription("Pyroblast");
        oldScroll.setCreation_date(new LocalDate(2018, 12, 31));
        magicScrollDAO.updateMagicScroll(oldScroll);

        Assert.assertEquals(magicScrollDAO.getMagicScrollById(correctScrollId1), original);
    }

    //Tests for getMagicScrollsByMageId
    @Test
    public void getScrollsOfMage() {

        List<MagicScroll> scrolls = magicScrollDAO.getMagicScrollsByMageId(correctMageId);
        Assert.assertEquals(new Long(scrolls.size()), amountScrollsOfMage1);
        for(MagicScroll scroll:scrolls) {
            Assert.assertEquals(scroll.getMage_id(), correctMageId);
        }

    }

    @Test
    public void getEmptyScrollsOfMage() {

        List<MagicScroll> scrolls = magicScrollDAO.getMagicScrollsByMageId(incorrectMageId);
        Assert.assertTrue(scrolls.size() == 0);

    }

    //Tests for clearScrollsByMagicId
    @Test
    public void clearMagicId() {

        Long size = magicScrollDAO.amountScrollsByMageId(correctMageId);
        Assert.assertEquals(size, amountScrollsOfMage1);
        magicScrollDAO.clearScrollsByMagicId(correctMageId);
        Long newsize = magicScrollDAO.amountScrollsByMageId(correctMageId);

        Assert.assertEquals(newsize, new Long(0));
    }

    //Tests for getMagicScrollsWithoutMage
    @Test
    public void getMagicScrollsWithoutMage() {

        List<MagicScroll> scrolls = magicScrollDAO.getMagicScrollsWithoutMage();
        Assert.assertEquals(new Long(scrolls.size()), amountScrollsWithoutMage);
        for(MagicScroll scroll:scrolls) {
            Assert.assertNull(scroll.getMage_id());
        }
    }

    @Test
    public void getEmptyMagicScrollsWithoutMage() {

        List<MagicScroll> scrolls = magicScrollDAO.getMagicScrollsWithoutMage();
        Assert.assertEquals(new Long(scrolls.size()), amountScrollsWithoutMage);
        for(MagicScroll scroll:scrolls) {
            magicScrollDAO.removeMagicScroll(scroll.getScroll_id());
        }

        Assert.assertTrue(magicScrollDAO.getMagicScrollsWithoutMage().size() == 0);
    }

    //Tests for getLimitScrolls
    @Test
    public void getLimitScrolls() {

        Long amt = 5L;
        Long n_from = 2L;

        List<MagicScroll> scrolls = magicScrollDAO.getLimitScrolls(amt, n_from);
        Assert.assertTrue(scrolls.size()<=amt);
        for(MagicScroll scroll:scrolls) {
            Long scroll_id = scroll.getScroll_id();
            Assert.assertTrue(scroll_id>=n_from && scroll_id<=n_from+amt-1);
        }
    }

    @Test
    public void getEmptyLimitScrolls() {

        Long amt = 9L;
        Long n_from = 99L;

        List<MagicScroll> scrolls = magicScrollDAO.getLimitScrolls(amt, n_from);
        Assert.assertTrue(scrolls.size() == 0);
    }

    @Test
    public void getFullLimitScrolls() {

        Long amt = 99L;
        Long n_from = 0L;

        List<MagicScroll> scrolls = magicScrollDAO.getLimitScrolls(amt, n_from);
        Assert.assertEquals(new Long(scrolls.size()), amount_scrolls);
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

    @Test
    public void getEmptyLimitMagicScrollsWithoutMage() {

        Long amt = 9L;
        Long n_from = 99L;

        List<MagicScroll> scrolls = magicScrollDAO.getLimitMagicScrollsWithoutMage(amt, n_from);
        Assert.assertTrue(scrolls.size() == 0);
    }

    @Test
    public void getFullLimitMagicScrollsWithoutMage() {

        Long amt = 99L;
        Long n_from = 0L;

        List<MagicScroll> scrolls = magicScrollDAO.getLimitMagicScrollsWithoutMage(amt, n_from);
        Assert.assertEquals(new Long(scrolls.size()), amountScrollsWithoutMage);
    }

    //Tests for getLimitMagicScrollsByMageId
    @Test
    public void getLimitMagicScrollsByMageId() {

        Long amt = 4L;
        Long n_from = 1L;

        List<MagicScroll> scrolls = magicScrollDAO.getLimitMagicScrollsByMageId(correctMageId, amt, n_from);
        Assert.assertTrue(scrolls.size()<=amt);
        for(MagicScroll scroll:scrolls) {
            Assert.assertEquals(scroll.getMage_id(), correctMageId);
        }
    }

    @Test
    public void getEmptyLimitMagicScrollsByMageId() {

        Long amt = 9L;
        Long n_from = 99L;

        List<MagicScroll> scrolls = magicScrollDAO.getLimitMagicScrollsByMageId(correctMageId, amt, n_from);
        Assert.assertTrue(scrolls.size() == 0);

        List<MagicScroll> scrolls2 = magicScrollDAO.getLimitMagicScrollsByMageId(incorrectMageId, amt, n_from);
        Assert.assertTrue(scrolls2.size() == 0);
    }

    //Tests for amountScrolls
    @Test
    public void amountScrolls() {

        Long amt = magicScrollDAO.amountScrolls();
        Assert.assertTrue(amt == amount_scrolls);
    }

    //Tests for amountScrollsByMageId
    @Test
    public void amountScrollsByMageId() {

        Long amt = magicScrollDAO.amountScrollsByMageId(correctMageId);
        Assert.assertTrue(amt == amountScrollsOfMage1);
    }

    //Tests for amountScrollsWithoutMage
    @Test
    public void amountScrollsWithoutMage() {

        Long amt = magicScrollDAO.amountScrollsWithoutMage();
        Assert.assertTrue(amt == amountScrollsWithoutMage);
    }
    //Tests for getAllMagicScrollsAfterDate
    @Test
    public void getAllMagicScrollsAfterDate() {

        LocalDate date = new LocalDate(2009,11,8);
        List<MagicScroll> scrolls = magicScrollDAO.getAllMagicScrollsAfterDate(date);
        for(MagicScroll scroll:scrolls) {

            Assert.assertTrue(scroll.getCreation_date().isAfter(date));
        }
    }
    //Tests for getAllMagicScrollsBeforeDate
    @Test
    public void getAllMagicScrollsBeforeDate() {

        LocalDate date = new LocalDate(2009,11,8);
        List<MagicScroll> scrolls = magicScrollDAO.getAllMagicScrollsBeforeDate(date);
        for(MagicScroll scroll:scrolls) {
            Assert.assertTrue(scroll.getCreation_date().isBefore(date));
        }

    }

    //Tests for getAllMagicScrollsBetweenDates
    @Test
    public void getAllMagicScrollsBetweenDates() {

        LocalDate date1 = new LocalDate(2004,4,4);
        LocalDate date2 = new LocalDate(2012,11,8);
        List<MagicScroll> scrolls = magicScrollDAO.getAllMagicScrollsBetweenDates(date1, date2);
        for(MagicScroll scroll:scrolls) {

            Assert.assertTrue(scroll.getCreation_date().isAfter(date1) & scroll.getCreation_date().isBefore(date2));
        }
    }
}
