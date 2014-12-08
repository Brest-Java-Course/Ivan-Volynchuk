package com.epam.brest.task.rest;

import com.epam.brest.task.domain.MagicScroll;
import com.epam.brest.task.service.Exception.*;
import com.epam.brest.task.service.MagicScrollService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by fieldistor on 27.11.14.
 */
@Controller
@RequestMapping("/rest/scroll")
public class ScrollRestController {

    private static final Logger LOGGER = LogManager.getLogger(ScrollRestController.class);

    @Resource
    private MagicScrollService magicScrollService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<MagicScroll> getScrollById(@PathVariable Long id) {
        LOGGER.debug("getScrollById({})", id);

        try {
            MagicScroll scroll = magicScrollService.getMagicScrollById(id);
            return new ResponseEntity(scroll, HttpStatus.OK);
        }catch(NoItemFoundException e) {
            LOGGER.debug(e);
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @ResponseBody
    @RequestMapping(value = "/filter/after/{date}", method= RequestMethod.GET)
    public ResponseEntity<List<MagicScroll>> getScrollsAfterDate(@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date) {
        LOGGER.debug("getScrollsAfterDate({})", date);

        try {
            List<MagicScroll> scrolls = magicScrollService.getAllMagicScrollsAfterDate(date);
            return new ResponseEntity(scrolls, HttpStatus.OK);
        }catch(NoItemsFoundException e) {
            LOGGER.debug(e);
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @ResponseBody
    @RequestMapping(value = "/filter/before/{date}", method= RequestMethod.GET)
    public ResponseEntity<List<MagicScroll>> getScrollsBeforeDate(@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date) {
        LOGGER.debug("getScrollsBeforeDate({})", date);

        try {
            List<MagicScroll> scrolls = magicScrollService.getAllMagicScrollsBeforeDate(date);
            return new ResponseEntity(scrolls, HttpStatus.OK);
        }catch(NoItemsFoundException e) {
            LOGGER.debug(e);
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @ResponseBody
    @RequestMapping(value = "/filter/between/{afterDate}/{beforeDate}", method= RequestMethod.GET)
    public ResponseEntity<List<MagicScroll>> getScrollsBetweenDates(@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate afterDate,
                                                                @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate beforeDate) {
        LOGGER.debug("getScrollsBetweenDates({}-{})", afterDate, beforeDate);

        try {
            List<MagicScroll> scrolls = magicScrollService.getAllMagicScrollsBetweenDates(afterDate, beforeDate);
            return new ResponseEntity(scrolls, HttpStatus.OK);
        }catch(NoItemsFoundException e) {
            LOGGER.debug(e);
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @ResponseBody
    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<List<MagicScroll>> getScrolls() {
        LOGGER.debug("getScrolls()");

        try {
            List<MagicScroll> scrolls = magicScrollService.getAllMagicScrolls();
            return new ResponseEntity(scrolls, HttpStatus.OK);
        }catch(NoItemsFoundException e) {
            LOGGER.debug(e);
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/description/{description}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<MagicScroll> getScrollByDescription(@PathVariable String description) {
        LOGGER.debug("getScrollByDescription({})", description);

        try {
            MagicScroll scroll = magicScrollService.getMagicScrollByDescription(description);
            return new ResponseEntity(scroll, HttpStatus.OK);
        }catch(NoItemFoundException e) {
            LOGGER.debug(e);
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @RequestMapping(method= RequestMethod.POST)
    public ResponseEntity<Long> addScroll(@RequestBody MagicScroll scroll) {
        LOGGER.debug("addScroll({})", scroll);

        try {
            Long id = magicScrollService.addMagicScroll(scroll);
            return new ResponseEntity(id, HttpStatus.CREATED);
        }catch(BadInsertException e) {
            LOGGER.debug(e);
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity updateScroll(@RequestBody MagicScroll scroll) {
        LOGGER.debug("updateScroll({})", scroll);

        try {
            magicScrollService.updateMagicScroll(scroll);
            return new ResponseEntity("", HttpStatus.OK);
        }catch(BadUpdateException e) {
            LOGGER.debug(e);
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity removeScroll(@PathVariable Long id) {
        LOGGER.debug("removeScroll({})", id);

        try {
            magicScrollService.removeMagicScroll(id);
            return new ResponseEntity("", HttpStatus.OK);
        }catch(BadRemoveException e) {
            LOGGER.debug(e);
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/mage/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<MagicScroll>> getScrollsByMageId(@PathVariable Long id) {
        LOGGER.debug("getScrollsByMageId({})", id);

        try {
            List<MagicScroll> scrolls = magicScrollService.getMagicScrollsByMageId(id);
            return new ResponseEntity(scrolls, HttpStatus.OK);
        }catch(NoItemFoundException e) {
            LOGGER.debug(e);
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
