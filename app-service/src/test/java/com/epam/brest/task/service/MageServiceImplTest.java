package com.epam.brest.task.service;

import com.epam.brest.task.domain.Mage;
import com.epam.brest.task.domain.MagicScroll;
import com.epam.brest.task.service.Exception.BadInsertException;
import com.epam.brest.task.service.Exception.BadRemoveException;
import com.epam.brest.task.service.Exception.NotFoundException;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.brest.task.dao.tools.TestMageScrollFactory.getNewMage;

/**
 * Created by fieldistor on 17.11.14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-service-context-test.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@Transactional

public class MageServiceImplTest {

    Long incorrectId = 99L;
    Long correctId1 = 0L;
    Long correctId2 = 1L;

    int amountMages = 8;

    @Autowired
    MageService mageService;

    @Autowired
    MagicScrollService magicScrollService;

    //Tests for getMageById
    @Test
    public void getMageById() {

        Mage mage = mageService.getMageById(correctId1);
        Assert.assertEquals(correctId1, mage.getMage_id());

        System.out.println(mage);
    }

    @Test(expected = NotFoundException.class)
    public void getMageByNullId() {

        Mage mage = mageService.getMageById(null);

    }

    @Test(expected = NotFoundException.class)
    public void getMageByIncorrectId() {


        Mage mage = mageService.getMageById(incorrectId);

    }

    @Test
    public void getMageByIdWithScrolls() {


        for(long id=0;id<amountMages;id++) {
            Mage mage = mageService.getMageById(id);
            List<MagicScroll> scrolls = mage.getMagicScrollList();

            for(MagicScroll scroll:scrolls) {
                Assert.assertEquals(scroll.getMage_id(), mage.getMage_id());
            }

        }

    }

    //Tests for getMageByName
    @Test
    public void getMageByName() {

        String name="Enigma";

        Mage mage = mageService.getMageByName(name);
        Assert.assertEquals(name, mage.getName());
    }

    @Test(expected = NotFoundException.class)
    public void getMageByNullName() {

        Mage mage = mageService.getMageByName(null);

    }

    @Test(expected = NotFoundException.class)
    public void getMageByIncorrectName() {

        String name= "Void";

        Mage mage = mageService.getMageByName(name);

    }

    @Test
    public void getMageByNameWithScrolls() {

            Mage mage = mageService.getMageByName("Enigma");
            List<MagicScroll> scrolls = mage.getMagicScrollList();

            for(MagicScroll scroll:scrolls) {
                Assert.assertEquals(scroll.getMage_id(), mage.getMage_id());
            }


    }

    //Test getAllMages
    @Test
    public void getAllMages() {

        List<Mage> mages =  mageService.getAllMages();
        Assert.assertEquals(mages.size(), amountMages);
    }

    @Test(expected = NotFoundException.class)
    public void getEmptyAllMages() {

        List<Mage> mages =  mageService.getAllMages();
        for(Mage mage:mages) {
            mageService.removeMageById(mage.getMage_id());
        }
        mageService.getAllMages();//Expect NotFoundException
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

    @Test(expected = BadInsertException.class)
    public void addNotNullIdMage() {

        Mage mage = getNewMage();
        mage.setMage_id(5L);
        Long id = mageService.addMage(mage);

    }

    @Test(expected = BadInsertException.class)
    public void addNullMageMage() {

        Mage mage = getNewMage();
        mage.setName(null);
        Long id = mageService.addMage(mage);

    }

    //Test removeMage
    @Test
    public void removeTest() {

        int oldsize = mageService.getAllMages().size();
        mageService.removeMageById(correctId1);
        int newsize = mageService.getAllMages().size();
        Assert.assertEquals(newsize + 1, oldsize);

    }

    @Test(expected = BadRemoveException.class)
    public void removeMageByIncorrectId() {

        mageService.removeMageById(incorrectId);
    }

    @Test(expected = BadRemoveException.class)
    public void removeMageByNullId() {

        mageService.removeMageById(null);
    }

    @Test(expected = NotFoundException.class)
    public void removeScrollsOfMage() {

        int beforeSize = magicScrollService.getMagicScrollsByMageId(correctId1).size();

        Mage mage = mageService.getMageById(correctId1);
        int beforeSizeCheck = mage.getMagicScrollList().size();
        Assert.assertEquals(beforeSize, beforeSizeCheck);
        mageService.removeMageById(correctId1);

        int afterSize = magicScrollService.getMagicScrollsByMageId(correctId1).size();
    }

    //Test getLimitMages
    @Test
    public void getLimitMages() {

        Long page = 1L;
        Long per_page = 2L;

        List<Mage> mages =  mageService.getLimitMages(page, per_page);
        Assert.assertTrue(mages.size()<=per_page);

    }

    @Test(expected = NotFoundException.class)
    public void getEmptyLimitMages() {

        Long page = 99L;
        Long per_page = 2L;

        List<Mage> mages =  mageService.getLimitMages(page, per_page);

    }

}
