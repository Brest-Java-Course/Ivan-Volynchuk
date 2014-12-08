package com.epam.brest.task.rest;

import com.epam.brest.task.domain.Mage;
import com.epam.brest.task.service.Exception.*;
import com.epam.brest.task.service.MageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
@RequestMapping("/rest/mage")
public class MageRestController {

    private static final Logger LOGGER = LogManager.getLogger(MageRestController.class);

    @Resource
    MageService mageService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Mage> getMageById(@PathVariable Long id) {
        LOGGER.debug("getMageById({})", id);

        try {
            Mage mage = mageService.getMageById(id);
            return new ResponseEntity(mage, HttpStatus.OK);
        }catch(NoItemFoundException e) {
            LOGGER.debug(e);
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @ResponseBody
    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<List<Mage>> getMages() {
        LOGGER.debug("getMages()");

        try {
            List<Mage> mages = mageService.getAllMages();
            return new ResponseEntity(mages, HttpStatus.OK);
        }catch(NoItemsFoundException e) {
            LOGGER.debug(e);
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Mage> getMageByName(@PathVariable String name) {
        LOGGER.debug("getMageByName({})", name);

        try {
            Mage mage = mageService.getMageByName(name);
            return new ResponseEntity(mage, HttpStatus.OK);
        }catch(NoItemFoundException e) {
            LOGGER.debug(e);
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @RequestMapping(method= RequestMethod.POST)
    public ResponseEntity<Long> addMage(@RequestBody Mage mage) {
        LOGGER.debug("addMage({})", mage);

        try {
            Long id = mageService.addMage(mage);
            return new ResponseEntity(id, HttpStatus.CREATED);
        }catch(BadInsertException e) {
            LOGGER.debug(e);
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity updateMage(@RequestBody Mage mage) {
        LOGGER.debug("updateMage({})", mage);

        try {
            mageService.updateMage(mage);
            return new ResponseEntity("", HttpStatus.OK);
        }catch(BadUpdateException e) {
            LOGGER.debug(e);
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity removeMage(@PathVariable Long id) {
        LOGGER.debug("removeMage({})", id);

        try {
            mageService.removeMageById(id);
            return new ResponseEntity("", HttpStatus.OK);
        }catch(BadRemoveException e) {
            LOGGER.debug(e);
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
