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

        Mage mage=mageDAO.getMageById(1L);
        Assert.assertEquals(mage.getMage_id(),new Long(1L));
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

        Mage mage = mageDAO.getMageByName("Paladin");
        Assert.assertEquals(mage.getName(),"Paladin");
    }


}
