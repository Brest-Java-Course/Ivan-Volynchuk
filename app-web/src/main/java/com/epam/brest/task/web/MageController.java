package com.epam.brest.task.web;

import com.epam.brest.task.domain.Mage;
import com.epam.brest.task.service.MageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by fieldistor on 24.11.14.
 */
@Controller
public class MageController {

    @Autowired
    MageService mageService;

    @RequestMapping(value={"/"})
    public ModelAndView showAllMages() {

        return new ModelAndView("index", "mages", mageService.getAllMages());
    }

    @RequestMapping(value={"/mage/add"}, method = RequestMethod.GET)
     public ModelAndView showAddFormMage() {

        return new ModelAndView("addMage");
    }

    @RequestMapping(value={"/mage/add"}, method = RequestMethod.POST)
    public String addMage(@RequestParam("name") String name,
                                @RequestParam("level") Long level,
                                @RequestParam("experience") Long exp)  {

        mageService.addMage(new Mage(name, level, exp));
        return "redirect:/";
    }

    @RequestMapping(value={"/mage/delete"}, method = RequestMethod.GET)
    public String deleteMage(@RequestParam("id") Long id)  {

        mageService.removeMageById(id);
        return "redirect:/";
    }

    @RequestMapping(value={"/mage/showMageScrolls"}, method = RequestMethod.GET)
    public ModelAndView showScrollsOfMage(@RequestParam("id") Long id)  {

        return new ModelAndView("mageScrolls", "mage", mageService.getMageById(id));
    }


    @RequestMapping(value={"/mage/update"}, method = RequestMethod.GET)
    public ModelAndView showChangeFormMage(@RequestParam("id") Long id) {

        return new ModelAndView("changeMage", "mage", mageService.getMageById(id));
    }

    @RequestMapping(value={"/mage/update"}, method = RequestMethod.POST)
    public String updateMage(@RequestParam("id") Long id,
                          @RequestParam("name") String name,
                          @RequestParam("level") Long level,
                          @RequestParam("exp") Long exp)  {

        mageService.updateMage(new Mage(id, name, level, exp));
        return "redirect:/";
    }



}
