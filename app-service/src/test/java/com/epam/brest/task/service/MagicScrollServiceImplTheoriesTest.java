package com.epam.brest.task.service;

import com.epam.brest.task.domain.MagicScroll;
import com.epam.brest.task.service.Exception.BadInsertException;
import com.epam.brest.task.service.Exception.BadRemoveException;
import com.epam.brest.task.service.Exception.NoItemFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;

import static com.epam.brest.task.tools.TestMagicScrollFactory.*;

/**
 * Created by fieldistor on 23.11.14.
 */
@RunWith(Theories.class)
@ContextConfiguration(locations = {"classpath:/spring-service-context-test.xml"})
public class MagicScrollServiceImplTheoriesTest {

    private final static Long correctId = 0L;
    private final static Long incorrectId = 99L;

    private final static String incorrectScrollDescription = "Burgerball";
    private final static String correctScrollDescription = "Frostball";

    @Autowired
    private MagicScrollService magicScrollService;

    private TestContextManager testContextManager;

    @Before
    public void setUpStringContext() throws Exception {
        testContextManager = new TestContextManager(getClass());
        testContextManager.prepareTestInstance(this);
    }

    @DataPoints
    public final static Long[] incorrectIdData = new Long[] {
            null,
            incorrectId
    };

    @DataPoints
    public final static MagicScroll[] incorrectMagicScrollData = new MagicScroll[] {
            null,
            getExistScroll(correctId),
            getNewScrollWithoutDate(),
            getNewScrollWithoutDescription(),
            getNewScrollWithoutDurability(),
            getNewScrollWithoutManaCost(),
            getNewScroll(correctScrollDescription)
    };

    @DataPoints
    public final static String[] incorrectDateData = new String[] {
            null,
            incorrectScrollDescription
    };

    //Tests for removeMagicScroll
    @Theory
    @Test(expected = BadRemoveException.class)
    public void removeMageByIncorrectId(Long data) {

        magicScrollService.removeMagicScroll(data);
    }

    //Tests for getMagicScrollById
    @Theory
    @Test(expected = NoItemFoundException.class)
    public void getMageByIncorrectId(Long data) {

        magicScrollService.getMagicScrollById(data);
    }

    //Tests for getMagicScrollByDescription
    @Theory
    @Test(expected = NoItemFoundException.class)
    public void getMageByIncorrectDescription(String data) {

        magicScrollService.getMagicScrollByDescription(data);
    }

    //Tests for getMagicScrollsByMageId
    @Theory
    @Test(expected = NoItemFoundException.class)
    public void getScrollsByMageWithIncorrectId(Long data) {

        magicScrollService.getMagicScrollsByMageId(data);
    }

    @Theory
    @Test(expected = BadInsertException.class)
    public void addMageWithIncorrectData(MagicScroll data) {

        magicScrollService.addMagicScroll(data);
    }

}
