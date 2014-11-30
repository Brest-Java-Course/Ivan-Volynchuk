package com.epam.brest.task.service;

import com.epam.brest.task.domain.Mage;
import com.epam.brest.task.domain.MagicScroll;
import com.epam.brest.task.service.Exception.*;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.brest.task.dao.tools.TestMageScrollFactory.*;

/**
 * Created by fieldistor on 17.11.14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-service-context-test.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@Transactional

public class MageServiceImplTest {

    @Autowired
    MageService mageService;

    @Autowired
    MagicScrollService magicScrollService;

    private final static Long correctMageId1 = 0L;
    private final static Long incorrectMageId = 99L;
    private final static Long coorectMageIdWithoutScrolls = 5L;

    private final static String correctMageName = "Enigma";
    private final static String incorrectMageName = "Void";

    private final static Long amountMage = 8L;
    private final static Long amoutScrollsOfMageId1 = 5L;

    //Tests for getMageById
    @Test
    public void getMageById() {

        Mage mage = mageService.getMageById(correctMageId1);
        Assert.assertEquals(correctMageId1, mage.getMage_id());
    }

    @Test
    public void addAndGetMageById() {

        Mage mage = getNewMage();
        Long id = mageService.addMage(mage);
        mage.setMage_id(id);
        Assert.assertNotNull(id);
        Assert.assertEquals(mage, mageService.getMageById(id));
    }

    @Test
    public void getMageByIdWithScrolls() {

        Mage mage = mageService.getMageById(correctMageId1);
        List<MagicScroll> scrolls = mage.getMagicScrollList();
        Assert.assertEquals(new Long(scrolls.size()), amoutScrollsOfMageId1);
        for(MagicScroll scroll:scrolls) {

            Assert.assertEquals(scroll.getMage_id(), mage.getMage_id());
        }
    }

    @Test
    public void getMageByIdAndCkeckAmtandAvg() {

        Long manacostSum = 0L;
        Mage mage = mageService.getMageById(correctMageId1);

        List<MagicScroll> scrolls = mage.getMagicScrollList();
        for(MagicScroll scroll:scrolls) {

            manacostSum+=scroll.getMana_cost();
        }
        Assert.assertEquals(new Long(manacostSum/scrolls.size()), mage.getAverage_manacost());
        Assert.assertEquals(new Long(scrolls.size()), mage.getScroll_amount());

    }
    //Tests for getMageByName
    @Test
    public void getMageByName() {

        Mage mage = mageService.getMageByName(correctMageName);

        Assert.assertEquals(correctMageName, mage.getName());
    }

    @Test
    public void getMageByNameWithScrolls() {

        Mage mage = mageService.getMageByName(correctMageName);
        List<MagicScroll> scrolls = mage.getMagicScrollList();
        Assert.assertEquals(new Long(scrolls.size()), amoutScrollsOfMageId1);

        for(MagicScroll scroll:scrolls) {
            Assert.assertEquals(scroll.getMage_id(), mage.getMage_id());
         }
    }

    @Test
    public void addAndGetMageByName() {

        Mage mage = getNewMage();
        Long id = mageService.addMage(mage);
        mage.setMage_id(id);
        Assert.assertNotNull(id);
        Assert.assertEquals(mage, mageService.getMageByName(mage.getName()));
    }

    @Test
    public void getMageByNameAndCkeckAmtandAvg() {

        Long manacostSum = 0L;
        Mage mage = mageService.getMageByName(correctMageName);

        List<MagicScroll> scrolls = mage.getMagicScrollList();
        for(MagicScroll scroll:scrolls) {

            manacostSum+=scroll.getMana_cost();
        }
        Assert.assertEquals(new Long(manacostSum/scrolls.size()), mage.getAverage_manacost());
        Assert.assertEquals(new Long(scrolls.size()), mage.getScroll_amount());

    }
    //Test getAllMages
    @Test
    public void getAllMages() {

        List<Mage> mages =  mageService.getAllMages();
        System.out.println(mages);
        Assert.assertEquals(new Long(mages.size()), amountMage);
    }

    @Test(expected = NoItemsFoundException.class)
    public void getEmptyAllMages() {

        List<Mage> mages =  mageService.getAllMages();
        for(Mage mage:mages) {
            mageService.removeMageById(mage.getMage_id());
        }
        mageService.getAllMages();
    }
    @Test
    public void getAllMageAndCkeckAmtandAvg() {

        List<Mage> mages = mageService.getAllMages();
        for(Mage mage:mages) {

            Long manacostSum = 0L;
            List<MagicScroll> scrolls = mageService.getMageById(mage.getMage_id()).getMagicScrollList();

            for(MagicScroll scroll:scrolls) {

                manacostSum+=scroll.getMana_cost();
            }
            Assert.assertEquals(new Long(manacostSum/(scrolls.size()==0?1:scrolls.size())), mage.getAverage_manacost());
            Assert.assertEquals(new Long(scrolls.size()), mage.getScroll_amount());

        }

    }

    //Test addMage
    @Test
    public void addMage() {

        Mage mage = getNewMage();
        Long id = mageService.addMage(mage);
        mage.setMage_id(id);
        Assert.assertNotNull(id);
        Assert.assertEquals(mage, mageService.getMageById(id));
    }

    //Test removeMage
    @Test
    public void removeMage() {

        Long oldsize = mageService.amountMages();
        mageService.removeMageById(correctMageId1);
        Long newsize = mageService.amountMages();
        Assert.assertEquals(++newsize, oldsize);

    }

    @Test(expected = NoItemFoundException.class)
    public void removeScrollsOfMage() {

        int beforeSize = magicScrollService.getMagicScrollsByMageId(correctMageId1).size();

        Mage mage = mageService.getMageById(correctMageId1);
        int beforeSizeCheck = mage.getMagicScrollList().size();
        Assert.assertEquals(beforeSize, beforeSizeCheck);
        mageService.removeMageById(correctMageId1);

        int afterSize = magicScrollService.getMagicScrollsByMageId(correctMageId1).size();
    }

    //Test getLimitMages
    @Test
    public void getLimitMages() {

        Long page = 1L;
        Long per_page = 2L;

        List<Mage> mages =  mageService.getLimitMages(page, per_page);
        Assert.assertTrue(mages.size()<=per_page);
        for(Mage mage:mages) {

            Long mageId = mage.getMage_id();
            Assert.assertTrue(mageId>=page*per_page & mageId<=page*per_page+per_page-1);
        }

    }

    @Test(expected = NoItemsFoundException.class)
    public void getEmptyLimitMages() {

        Long page = 99L;
        Long per_page = 2L;

        List<Mage> mages =  mageService.getLimitMages(page, per_page);

    }
    //Test for amountMages
    @Test
    public void amountMages() {
        Assert.assertEquals(amountMage, mageService.amountMages());
    }

    //Tests for updateMage
    @Test
    public void updateMage() {

        Mage mage = mageService.getMageById(correctMageId1);
        mage.setExp(300L);
        mage.setName("Yoda");
        mageService.updateMage(mage);
        Assert.assertEquals(mage, mageService.getMageById(mage.getMage_id()));
    }
}
