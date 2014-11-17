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

    @Autowired
    MageService mageService;

    @Autowired
    MagicScrollService magicScrollService;

    //Tests for getMageById
    @Test
    public void getMageById() {

        Long id=0L;

        Mage mage = mageService.getMageById(id);
        Assert.assertEquals(id, mage.getMage_id());
    }

    @Test(expected = NotFoundException.class)
    public void getMageByNullId() {

        Long id=null;

        Mage mage = mageService.getMageById(id);

    }

    @Test(expected = NotFoundException.class)
    public void getMageByIncorrectId() {

        Long id=99L;

        Mage mage = mageService.getMageById(id);

    }

    @Test
    public void getMageByIdWithScrolls() {


        for(long id=0;id<2;id++) {
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

        String name=null;

        Mage mage = mageService.getMageByName(name);

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
        Assert.assertEquals(mages.size(), 2);
    }

    //Test
    public void getEmptyAllMages() {

        List<Mage> mages =  mageService.getAllMages();
        for(Mage mage:mages) {

        }
        Assert.assertEquals(mages.size(), 2);
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

        Long deleteId = 0L;

        int oldsize = mageService.getAllMages().size();
        mageService.removeMageById(deleteId);
        int newsize = mageService.getAllMages().size();
        Assert.assertEquals(newsize + 1, oldsize);

    }

    @Test(expected = BadRemoveException.class)
    public void removeMageByIncorrectId() {

        mageService.removeMageById(99L);
    }

    @Test(expected = BadRemoveException.class)
    public void removeMageByNullId() {

        mageService.removeMageById(null);
    }

    @Test(expected = NotFoundException.class)
    public void removeScrollsOfMage() {

        Long mageId = 0L;

        int beforeSize = magicScrollService.getMagicScrollsByMageId(mageId).size();

        Mage mage = mageService.getMageById(mageId);
        int beforeSizeCheck = mage.getMagicScrollList().size();
        Assert.assertEquals(beforeSize, beforeSizeCheck);
        mageService.removeMageById(mageId);

        int afterSize = magicScrollService.getMagicScrollsByMageId(mageId).size();
    }
}
