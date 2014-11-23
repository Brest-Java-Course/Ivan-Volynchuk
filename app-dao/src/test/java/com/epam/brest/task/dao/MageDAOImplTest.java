package com.epam.brest.task.dao;

import com.epam.brest.task.domain.Mage;
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

import static com.epam.brest.task.dao.tools.TestMageScrollFactory.getNewMage;

/**
 * Created by fieldistor on 16.11.14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-dao-context-test.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@Transactional
public class MageDAOImplTest {

    @Autowired
    private MageDAO mageDAO;

    private final static Long correctMageId1 = 0L;
    private final static Long incorrectMageId = 99L;

    private final static String correctMageName = "Paladin";

    private final static Long amountMage = 8L;

    //Tests for addMage
    @Test
    public void AddMage() {

        Mage mage = getNewMage();
        Long id = mageDAO.addMage(mage);
        Assert.assertNotNull(id);
        Long size = mageDAO.amountMages();
        Assert.assertEquals(--size, amountMage);
    }


    //Tests for getMageById
    @Test
    public void getMageById() {

        Mage mage=mageDAO.getMageById(correctMageId1);
        Assert.assertEquals(mage.getMage_id(), correctMageId1);
    }

    @Test
    public void AddAndGetMageById() {

        Mage mage = getNewMage();
        Long id = mageDAO.addMage(mage);
        Assert.assertNotNull(id);
        mage.setMage_id(id);
        Mage newMage = mageDAO.getMageById(id);
        Assert.assertEquals(mage, newMage);
    }

    //Tests for getAllMages
    @Test
    public void getAllMages() {

        List<Mage> mages= mageDAO.getAllMages();
        int size = mages.size();
        Assert.assertEquals(new Long(size), amountMage);
        mageDAO.addMage(getNewMage());
        Assert.assertEquals(size,mageDAO.getAllMages().size()-1);
    }

    //Tests for getMageByName
    @Test
    public void getMageByName() {

        Mage mage = mageDAO.getMageByName(correctMageName);
        Assert.assertEquals(mage.getName(), correctMageName);
    }

    @Test
    public void AddAndGetMageByName() {

        Mage mage = getNewMage();
        Long id = mageDAO.addMage(mage);
        Assert.assertNotNull(id);
        mage.setMage_id(id);
        Mage newMage = mageDAO.getMageByName(mage.getName());
        Assert.assertEquals(mage, newMage);
    }

    //Tests for removeMageById
    @Test
    public void removeMageById() {

        List<Mage> mages= mageDAO.getAllMages();
        int size = mages.size();
        mageDAO.removeMageById(correctMageId1);
        Assert.assertEquals(size-1, mageDAO.getAllMages().size());
    }

    //Tests for getLimitMages
    @Test
    public void getLimitMages() {

        Long amt = 3L;
        Long n_from = 0L;

        List<Mage> mages = mageDAO.getLimitMages(amt, n_from);
        Assert.assertTrue(mages.size() <= amt);
        for(Mage mage:mages) {
            Assert.assertTrue(mage.getMage_id()<=amt);
        }
    }

    @Test
    public void getEmptyLimitMages() {

        Long amt = 3L;
        Long n_from = 99L;

        List<Mage> mages = mageDAO.getLimitMages(amt, n_from);
        Assert.assertTrue(mages.size() == 0);
    }

    //Tests for amount
    @Test
    public void amountMages() {

        Long amt = mageDAO.amountMages();
        Assert.assertEquals(amt, amountMage);

    }

    //Tests for updateMage
    @Test
    public void updateMage() {

        Mage oldMage = mageDAO.getMageById(correctMageId1);

        oldMage.setExp(0L);
        oldMage.setLevel(80L);
        oldMage.setName("Gul'Dan");
        mageDAO.updateMage(oldMage);

        Mage newMage= mageDAO.getMageById(oldMage.getMage_id());
        Assert.assertEquals(oldMage, newMage);
    }

    @Test
    public void updateMageWithIncorrectId() {

        Mage original = mageDAO.getMageById(correctMageId1);
        Mage oldMage = mageDAO.getMageById(correctMageId1);

        oldMage.setMage_id(incorrectMageId);
        oldMage.setExp(0L);
        oldMage.setLevel(80L);
        oldMage.setName("Gul'Dan");
        mageDAO.updateMage(oldMage);

        Assert.assertEquals(original, mageDAO.getMageById(correctMageId1));
    }


}
