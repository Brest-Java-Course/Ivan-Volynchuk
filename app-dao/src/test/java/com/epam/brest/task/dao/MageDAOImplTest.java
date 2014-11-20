package com.epam.brest.task.dao;

import com.epam.brest.task.domain.Mage;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Test
    public void AddMage() {

        Mage mage = new Mage("John");

        Long id=mageDAO.addMage(mage);
    }

    @Test
    public void getMageById() {

        Long mageid=1L;

        Mage mage=mageDAO.getMageById(mageid);
        System.out.println(mage);
        Assert.assertEquals(mage.getMage_id(),new Long(mageid));
    }

    @Test
    public void AddAndGetTest() {

        Mage mage = new Mage("Boris");
        Long id=mageDAO.addMage(mage);
        Mage mage2=mageDAO.getMageById(id);
        Assert.assertEquals(mage2.getName(), mage.getName());

    }

    @Test
    public void getAllMages() {

        List<Mage> mages= mageDAO.getAllMages();
        int size = mages.size();
        mageDAO.addMage(new Mage("tesT"));
        Assert.assertEquals(size,mageDAO.getAllMages().size()-1);
    }

    @Test
    public void getMageByLogin() {

        String mageName="Paladin";

        Mage mage = mageDAO.getMageByName(mageName);
        Assert.assertEquals(mage.getName(),mageName);
    }

    @Test
    public void removeMageById() {

        List<Mage> mages= mageDAO.getAllMages();
        int size = mages.size();
        mageDAO.removeMageById(0L);
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

    //Tests for amount ;)
    @Test
    public void amountMages() {

        Long amt = mageDAO.amountMages();
        int size = mageDAO.getAllMages().size();
        Assert.assertTrue(amt==size);

    }


}
