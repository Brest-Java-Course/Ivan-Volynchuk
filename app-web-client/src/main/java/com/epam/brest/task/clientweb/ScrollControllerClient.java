package com.epam.brest.task.clientweb;

import com.epam.brest.task.clientservice.Exception.*;
import com.epam.brest.task.clientservice.MageServiceClient;
import com.epam.brest.task.clientservice.MagicScrollServiceClient;
import com.epam.brest.task.domain.MagicScroll;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by fieldistor on 25.11.14.
 */
@Controller
@RequestMapping("/scroll")
public class ScrollControllerClient {

    @Autowired
    private MagicScrollServiceClient magicScrollService;

    @Autowired
    private MageServiceClient mageService;

    private static final Logger LOGGER = LogManager.getLogger(ScrollControllerClient.class);


    @RequestMapping(value={"/filter/after"}, method = RequestMethod.GET)
    public ModelAndView filterAfterDate(RedirectAttributes redirectAttributes, @RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date) {

        try {
            ModelAndView mav = new ModelAndView("allScrolls");
            mav.addObject("scrolls", magicScrollService.getAllMagicScrollsAfterDate(date));
            return mav;
        }catch(NoItemsFoundException e) {
            LOGGER.debug(e);
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return new ModelAndView("redirect:/scroll/allScrolls");
        }
    }

    @RequestMapping(value={"/filter/before"}, method = RequestMethod.GET)
    public ModelAndView filterBeforeDate(RedirectAttributes redirectAttributes, @RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date) {

        try {
            return new ModelAndView("allScrolls", "scrolls", magicScrollService.getAllMagicScrollsBeforeDate(date));
        }catch(NoItemsFoundException e) {
            LOGGER.debug(e);
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return new ModelAndView("redirect:/scroll/allScrolls");
        }
    }

    @RequestMapping(value={"/filter/between"}, method = RequestMethod.GET)
    public ModelAndView filterBetweenDates(RedirectAttributes redirectAttributes,
                                           @RequestParam("date1") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate afterDate,
                                           @RequestParam("date2") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate beforeDate) {

        try {
            return new ModelAndView("allScrolls", "scrolls", magicScrollService.getAllMagicScrollsBetweenDates(afterDate, beforeDate));
        }catch(NoItemsFoundException e) {
            LOGGER.debug(e);
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return new ModelAndView("redirect:/scroll/allScrolls");
        }
    }

    @RequestMapping(value={"/allScrolls"}, method = RequestMethod.GET)
    public ModelAndView showAllScrolls() {

        try {
            return new ModelAndView("allScrolls", "scrolls", magicScrollService.getAllMagicScrolls());
        }catch(NoItemsFoundException e) {
            LOGGER.debug(e);
            return new ModelAndView("allScrolls","message",e.getMessage());
        }
    }

    //Update
    @RequestMapping(value={"/update"}, method = RequestMethod.GET)
    public ModelAndView showFormChangeScroll(RedirectAttributes redirectAttributes, @RequestParam("id") Long id) {

        try {
            ModelAndView mav = new ModelAndView("changeScroll", "scroll", magicScrollService.getMagicScrollById(id));
            mav.addObject("mags", mageService.getAllMages());
            return mav;
        }catch(NoItemFoundException e) {
            LOGGER.debug(e);
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return new ModelAndView("redirect:/scroll/allScrolls");
        }

    }

    @RequestMapping(value={"/update"}, method = RequestMethod.POST)
    public ModelAndView updateScroll(RedirectAttributes redirectAttributes,
                                     @RequestParam("description") String description,
                                     @RequestParam("durability") Long durability,
                                     @RequestParam("creationDate") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate creationDate,
                                     @RequestParam("manaCost") Long manaCost,
                                     @RequestParam("mageId") Long mageId,
                                     @RequestParam("scrollId") Long scrollId) {

        try {
            magicScrollService.updateMagicScroll(new MagicScroll(scrollId, description, durability, creationDate, manaCost, mageId));
            return  new ModelAndView("redirect:/scroll/allScrolls");
        }catch(BadUpdateException e) {
            LOGGER.debug(e);
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return new ModelAndView("redirect:/scroll/update","id", scrollId);
        }
    }

    @RequestMapping(value={"/updateMageScroll"}, method = RequestMethod.GET)
    public ModelAndView showFormChangeMageScroll(RedirectAttributes redirectAttributes, @RequestParam("id") Long id, @RequestParam("mageId") Long mageId) {

        try {
            ModelAndView mav = new ModelAndView("changeMageScroll", "scroll", magicScrollService.getMagicScrollById(id));
            mav.addObject("mags", mageService.getAllMages());
            return mav;
        }catch(NoItemFoundException e) {
            LOGGER.debug(e);
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return new ModelAndView("redirect:/mage/showMageScrolls","id",mageId);
        }

    }

    @RequestMapping(value={"/updateMageScroll"}, method = RequestMethod.POST)
    public ModelAndView updateMageScroll(RedirectAttributes redirectAttributes,
                                         @RequestParam("description") String description,
                                         @RequestParam("durability") Long durability,
                                         @RequestParam("creationDate") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate creationDate,
                                         @RequestParam("manaCost") Long manaCost,
                                         @RequestParam("mageId") Long mageId,
                                         @RequestParam("scrollId") Long scrollId) {

        try {
            magicScrollService.updateMagicScroll(new MagicScroll(scrollId, description, durability, creationDate, manaCost, mageId));
            ModelAndView mav = new ModelAndView("redirect:/mage/showMageScrolls");
            mav.addObject("id",mageId);
            return  mav;
        }catch(BadUpdateException e) {
            LOGGER.debug(e);
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            ModelAndView mav = new ModelAndView("redirect:/scroll/updateMageScroll","mageId",mageId);
            return mav.addObject("id", scrollId);
        }

    }

    //Delete
    @RequestMapping(value={"/deleteMageScroll"}, method = RequestMethod.GET)
    public ModelAndView deleteMageScroll(RedirectAttributes redirectAttributes, @RequestParam("mageId") Long mageId, @RequestParam("scrollId") Long scrollId)  {

        try {
            magicScrollService.removeMagicScroll(scrollId);
            ModelAndView mav = new ModelAndView("redirect:/mage/showMageScrolls");
            return  mav.addObject("id", mageId);
        }catch(BadRemoveException e) {
            LOGGER.debug(e);
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return new ModelAndView("redirect:/mage/showMageScrolls","id",mageId);
        }

    }

    @RequestMapping(value={"/delete"}, method = RequestMethod.GET)
    public String deleteScroll(RedirectAttributes redirectAttributes, @RequestParam("scrollId") Long scrollId)  {

        try {
            magicScrollService.removeMagicScroll(scrollId);
            return "redirect:/scroll/allScrolls";
        }catch(BadRemoveException e) {
            LOGGER.debug(e);
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/scroll/allScrolls";
        }
    }

    //Add
    @RequestMapping(value={"/add"}, method = RequestMethod.GET)
    public ModelAndView showFormAddScroll() {

        return new ModelAndView("addScroll");
    }

    @RequestMapping(value={"/add"}, method = RequestMethod.POST)
    public String addScroll(RedirectAttributes redirectAttributes,
                            @RequestParam("description") String description,
                            @RequestParam("durability") Long durability,
                            @RequestParam("creationDate") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate creationDate,
                            @RequestParam("manaCost") Long manaCost) {

        try {
            magicScrollService.addMagicScroll( new MagicScroll(description, durability, creationDate, manaCost));
            return "redirect:/scroll/allScrolls";
        }catch(BadInsertException e) {
            LOGGER.debug(e);
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/scroll/allScrolls";
        }

    }

    @RequestMapping(value={"/addToMage"}, method = RequestMethod.GET)
    public ModelAndView showFormAddScrollToMage(@RequestParam("id") Long id) {

        return new ModelAndView("addScrollToMage","mageId",id);
    }

    @RequestMapping(value={"/addToMage"}, method = RequestMethod.POST)
    public ModelAndView addScrollToMage(RedirectAttributes redirectAttributes,
                                        @RequestParam("description") String description,
                                        @RequestParam("durability") Long durability,
                                        @RequestParam("creationDate") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate creationDate,
                                        @RequestParam("manaCost") Long manaCost,
                                        @RequestParam("id") Long id) {

        try {
            magicScrollService.addMagicScroll( new MagicScroll(description, durability, creationDate, manaCost, id));
            ModelAndView mav = new ModelAndView("redirect:/mage/showMageScrolls");
            return  mav.addObject("id", id);
        }catch(BadInsertException e) {
            LOGGER.debug(e);
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return new ModelAndView("redirect:/scroll/addToMage","id",id);
        }

    }
}
