package com.epam.brest.courses.web;

import com.epam.brest.courses.domain.exception.BadInputData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
/**
 * Created by fieldistor on 13.11.14.
 */
@ControllerAdvice
public class ExceptionHandlerController {

    private static final Logger LOGGER = LogManager.getLogger();

    public static final String DEFAULT_ERROR_VIEW = "error/error";
    public static final String BAD_DATA_ERROR_VIEW= "error/badData";

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception e) {

        LOGGER.error(e.fillInStackTrace());

        ModelAndView mav = new ModelAndView(DEFAULT_ERROR_VIEW);
        mav.addObject("datetime", new Date());
        mav.addObject("exception", e);
        mav.addObject("url", request.getRequestURL());
        return mav;
    }

    @ExceptionHandler(value = {BadInputData.class})
    public ModelAndView BadInputDataErrorHandler(HttpServletRequest request, Exception e) {

        LOGGER.error(e.fillInStackTrace());

        ModelAndView mav = new ModelAndView(BAD_DATA_ERROR_VIEW);
        mav.addObject("datetime", new Date());
        mav.addObject("exception", e);
        mav.addObject("url", request.getRequestURL());
        return mav;
    }
}