package com.epam.brest.task.service;

import com.epam.brest.task.domain.Mage;
import com.epam.brest.task.service.Exception.BadUpdateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;

import java.util.Arrays;

import static com.epam.brest.task.dao.tools.TestMageScrollFactory.*;

/**
 * Created by fieldistor on 24.11.14.
 */
@RunWith(value = Parameterized.class)
@ContextConfiguration(locations = {"classpath:/spring-service-context-test.xml"})
public class MageServiceImplParameterizedTest {

    private static final Long incorrectId = 99L;
    private static final Long correctId = 0L;

    private static final String existName = "Paladin";

    @Autowired
    private MageService mageService;

    private Mage mage;

    private TestContextManager testContextManager;

    @Before
    public void setUpStringContext() throws Exception {
        testContextManager = new TestContextManager(getClass());
        testContextManager.prepareTestInstance(this);
    }

    public MageServiceImplParameterizedTest(Mage mage) {
        this.mage = mage;
    }
    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {getExistMage(incorrectId)},
                {getNewMage()},
                {null},
                {getExistMageWithoutName(correctId)},
                {getExistMageWithoutExp(correctId)},
                {getExistMageWithoutLevel(correctId)},
                {getExistMage(correctId, existName)}
        });
    }

    @Test(expected = BadUpdateException.class)
    public void updateMage() {

        mageService.updateMage(mage);
    }
}
