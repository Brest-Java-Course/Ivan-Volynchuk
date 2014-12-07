package com.epam.brest.task.service;

import com.epam.brest.task.domain.Mage;
import com.epam.brest.task.service.Exception.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;

import static com.epam.brest.task.tools.TestMageScrollFactory.*;

/**
 * Created by fieldistor on 23.11.14.
 */
@RunWith(Theories.class)
@ContextConfiguration(locations = {"classpath:/spring-service-context-test.xml"})
public class MageServiceImplTheoriesTest {

    @Autowired
    MageService mageService;

    private TestContextManager testContextManager;

    @Before
    public void setUpStringContext() throws Exception {
        testContextManager = new TestContextManager(getClass());
        testContextManager.prepareTestInstance(this);
    }

    private final static Long incorrectId = 99L;
    private final static Long correctId = 0L;
    private final static String incorrectMageName = "Void";

    @DataPoints
    public static final Long[] getMageByIdIncorrectData = new Long[] {
            null,
            incorrectId
    };

    @DataPoints
    public static String[] getMageByNameIncorrectData = new String[] {
            null,
            incorrectMageName
    };

    @DataPoints
    public static Mage[] MageIncorrectData = new Mage[] {
            null,
            getExistMage(correctId),
            getNewMageWithoutName(),
            getNewMageWithoutExp(),
            getNewMageWithoutLevel()
    };

    //Tests for getMageById
    @Theory
    @Test(expected = NoItemFoundException.class)
    public void getMageByIncorrectId(Long data) {

        Mage mage = mageService.getMageById(data);
    }

    //Tests for getMageByName
    @Theory
    @Test(expected = NoItemFoundException.class)
    public void getMageByNullName(String data) {

        Mage mage = mageService.getMageByName(data);
    }

    //Test addMage
    @Theory
    @Test(expected = BadInsertException.class)
    public void addMageWithIncorrectData(Mage mage) {

        Long id = mageService.addMage(mage);
    }

    //Test removeMage
    @Theory
    @Test(expected = BadRemoveException.class)
    public void removeMageWithIncorrectData(Long data) {

        mageService.removeMageById(data);
    }

}
