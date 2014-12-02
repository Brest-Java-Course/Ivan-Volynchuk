package com.epam.brest.task.clientweb;

import com.epam.brest.task.clientservice.MageServiceClient;
import com.epam.brest.task.clientservice.MagicScrollServiceClient;
import com.epam.brest.task.domain.MagicScroll;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by fieldistor on 25.11.14.
 */
@Controller
public class ScrollControllerClient {

    @Autowired
    private MagicScrollServiceClient magicScrollService;

    @Autowired
    private MageServiceClient mageService;

    @RequestMapping(value={"/scroll/filter/after"}, method = RequestMethod.GET)
    public ModelAndView filterAfterDate(@RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date) {

        return new ModelAndView("allScrolls", "scrolls", magicScrollService.getAllMagicScrollsAfterDate(date));
    }

    @RequestMapping(value={"/scroll/filter/before"}, method = RequestMethod.GET)
    public ModelAndView filterBeforeDate(@RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date) {

        return new ModelAndView("allScrolls", "scrolls", magicScrollService.getAllMagicScrollsBeforeDate(date));
    }

    @RequestMapping(value={"/scroll/filter/between"}, method = RequestMethod.GET)
    public ModelAndView filterBetweenDates(@RequestParam("date1") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate afterDate,
                                           @RequestParam("date2") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate beforeDate) {

        return new ModelAndView("allScrolls", "scrolls", magicScrollService.getAllMagicScrollsBetweenDates(afterDate, beforeDate));
    }

    @RequestMapping(value={"/scroll/allScrolls"}, method = RequestMethod.GET)
    public ModelAndView showAllScrolls() {

        return new ModelAndView("allScrolls", "scrolls", magicScrollService.getAllMagicScrolls());
    }

    //Update
    @RequestMapping(value={"/scroll/update"}, method = RequestMethod.GET)
    public ModelAndView showFormChangeScroll(@RequestParam("id") Long id) {

        ModelAndView mav = new ModelAndView("changeScroll", "scroll", magicScrollService.getMagicScrollById(id));
        mav.addObject("mags", mageService.getAllMages());
        return mav;
    }

    @RequestMapping(value={"/scroll/update"}, method = RequestMethod.POST)
    public String updateScroll(@RequestParam("description") String description,
                                         @RequestParam("durability") Long durability,
                                         @RequestParam("creationDate") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate creationDate,
                                         @RequestParam("manaCost") Long manaCost,
                                         @RequestParam("mageId") Long mageId,
                                         @RequestParam("scrollId") Long scrollId) {

        magicScrollService.updateMagicScroll(new MagicScroll(scrollId, description, durability, creationDate, manaCost, mageId));
        return  "redirect:/scroll/allScrolls";
    }

    @RequestMapping(value={"/scroll/updateMageScroll"}, method = RequestMethod.GET)
    public ModelAndView showFormChangeMageScroll(@RequestParam("id") Long id) {

        ModelAndView mav = new ModelAndView("changeMageScroll", "scroll", magicScrollService.getMagicScrollById(id));
        mav.addObject("mags", mageService.getAllMages());
        return mav;
    }

    @RequestMapping(value={"/scroll/updateMageScroll"}, method = RequestMethod.POST)
    public ModelAndView updateMageScroll(@RequestParam("description") String description,
                                        @RequestParam("durability") Long durability,
                                        @RequestParam("creationDate") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate creationDate,
                                        @RequestParam("manaCost") Long manaCost,
                                        @RequestParam("mageId") Long mageId,
                                        @RequestParam("scrollId") Long scrollId) {

        magicScrollService.updateMagicScroll(new MagicScroll(scrollId, description, durability, creationDate, manaCost, mageId));
        ModelAndView mav = new ModelAndView("redirect:/mage/showMageScrolls");
        return  mav.addObject("id", mageId);
    }

    //Delete
    @RequestMapping(value={"/scroll/delete1"}, method = RequestMethod.GET)
    public ModelAndView deleteMageScroll(@RequestParam("mageId") Long mageId, @RequestParam("scrollId") Long scrollId)  {

        magicScrollService.removeMagicScroll(scrollId);
        ModelAndView mav = new ModelAndView("redirect:/mage/showMageScrolls");
        return  mav.addObject("id", mageId);
    }

    @RequestMapping(value={"/scroll/delete2"}, method = RequestMethod.GET)
    public String deleteScroll(@RequestParam("scrollId") Long scrollId)  {

        magicScrollService.removeMagicScroll(scrollId);
        return "redirect:/scroll/allScrolls";
    }

    //Add
    @RequestMapping(value={"/scroll/add"}, method = RequestMethod.GET)
    public ModelAndView showFormAddScroll() {

        ModelAndView mav = new ModelAndView("addScroll");
        return mav;
    }

    @RequestMapping(value={"/scroll/add"}, method = RequestMethod.POST)
    public String addScroll(@RequestParam("description") String description,
                                        @RequestParam("durability") Long durability,
                                        @RequestParam("creationDate") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate creationDate,
                                        @RequestParam("manaCost") Long manaCost) {

        magicScrollService.addMagicScroll( new MagicScroll(description, durability, creationDate, manaCost));

        return  "redirect:/scroll/allScrolls";
    }

    @RequestMapping(value={"/scroll/addToMage"}, method = RequestMethod.GET)
    public ModelAndView showFormAddScrollToMage(@RequestParam("id") Long id) {

        ModelAndView mav = new ModelAndView("addScrollToMage");
        mav.addObject("mageId", id);
        return mav;
    }

    @RequestMapping(value={"/scroll/addToMage"}, method = RequestMethod.POST)
    public ModelAndView addScrollToMage(@RequestParam("description") String description,
                          @RequestParam("durability") Long durability,
                          @RequestParam("creationDate") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate creationDate,
                          @RequestParam("manaCost") Long manaCost,
                          @RequestParam("id") Long id) {

        magicScrollService.addMagicScroll( new MagicScroll(description, durability, creationDate, manaCost, id));
        ModelAndView mav = new ModelAndView("redirect:/mage/showMageScrolls");
        return  mav.addObject("id", id);
    }
}
