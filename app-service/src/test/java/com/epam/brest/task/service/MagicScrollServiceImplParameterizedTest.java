package com.epam.brest.task.service;

import com.epam.brest.task.domain.MagicScroll;
import com.epam.brest.task.service.Exception.BadUpdateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;

import java.util.Arrays;

import static com.epam.brest.task.tools.TestMagicScrollFactory.*;

/**
 * Created by fieldistor on 24.11.14.
 */
@RunWith(value = Parameterized.class)
@ContextConfiguration(locations = {"classpath:/spring-service-context-test.xml"})
public class MagicScrollServiceImplParameterizedTest {

    private final static Long correctId = 0L;
    private final static Long incorrectId = 99L;

    private final static String existScrollDescription = "Frostball";

    @Autowired
    private MagicScrollService magicScrollService;

    private MagicScroll scroll;

    private TestContextManager testContextManager;

    @Before
    public void setUpStringContext() throws Exception {
        testContextManager = new TestContextManager(getClass());
        testContextManager.prepareTestInstance(this);
    }

    public MagicScrollServiceImplParameterizedTest(MagicScroll scroll) {
        this.scroll = scroll;
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {getExistScroll(incorrectId)},
                {getNewScroll()},
                {null},
                {getExistScrollWithoutDescription(correctId)},
                {getExistScrollWithoutDate(correctId)},
                {getExistScrollWithoutDurability(correctId)},
                {getExistScrollWithoutManaCost(correctId)},
                {getExistScroll(correctId, existScrollDescription)}
        });
    }

    @Test(expected = BadUpdateException.class)
    public void updateScroll() {

        magicScrollService.updateMagicScroll(scroll);
    }
}
