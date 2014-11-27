package com.epam.brest.task.rest;

import com.epam.brest.task.domain.Mage;
import com.epam.brest.task.service.Exception.*;
import com.epam.brest.task.service.MageService;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MageRestController {  //TODO: If there is time i will do limit and amount

    @Resource
    MageService mageService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Mage> getMageById(@PathVariable Long id) {

        try {
            Mage mage = mageService.getMageById(id);
            return new ResponseEntity(mage, HttpStatus.OK);
        }catch(NoItemFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @ResponseBody
    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<List<Mage>> getMages() {

        try {
            List<Mage> mages = mageService.getAllMages();
            return new ResponseEntity(mages, HttpStatus.OK);
        }catch(NoItemsFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Mage> getMageByName(@PathVariable String name) {

        try {
            Mage mage = mageService.getMageByName(name);
            return new ResponseEntity(mage, HttpStatus.OK);
        }catch(NoItemFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @RequestMapping(method= RequestMethod.POST)
    public ResponseEntity<Long> addMage(@RequestBody Mage mage) {

        try {
            Long id = mageService.addMage(mage);
            return new ResponseEntity(id, HttpStatus.CREATED);
        }catch(BadInsertException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity updateMage(@RequestBody Mage mage) {

        try {
            mageService.updateMage(mage);
            return new ResponseEntity("", HttpStatus.OK);
        }catch(BadUpdateException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity removeMage(@PathVariable Long id) {

        try {
            mageService.removeMageById(id);
            return new ResponseEntity("", HttpStatus.OK);
        }catch(BadRemoveException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
