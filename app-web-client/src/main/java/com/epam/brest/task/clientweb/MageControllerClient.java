package com.epam.brest.task.clientweb;

import com.epam.brest.task.clientservice.Exception.*;
import com.epam.brest.task.clientservice.MageServiceClient;
import com.epam.brest.task.domain.Mage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by fieldistor on 24.11.14.
 */
@Controller
@RequestMapping("/mage")
public class MageControllerClient {

    @Autowired
    MageServiceClient mageService;

    private static final Logger LOGGER = LogManager.getLogger(MageControllerClient.class);

    @RequestMapping(value={"/"})
    public ModelAndView showAllMages() {
        ModelAndView mav = new ModelAndView("index");
        try {
            List<Mage> mages = mageService.getAllMages();
            mav.addObject("mages",mages);
            return mav;
        }catch(NoItemsFoundException e) {
            LOGGER.debug(e);
            mav.addObject("message",e.getMessage());
            return mav;
        }
    }

    @RequestMapping(value={"/search/name"}, method = RequestMethod.GET)
    public ModelAndView searchMageByName(RedirectAttributes redirectAttributes, @RequestParam("name") String name) {

        try {
            ModelAndView mav = new ModelAndView("redirect:/mage/showMageScrolls");
            mav.addObject("id", mageService.getMageByName(name).getMage_id());
            return mav;
        }catch(NoItemFoundException e) {
            LOGGER.debug(e);
            redirectAttributes.addFlashAttribute( "message", e.getMessage());
            return new ModelAndView("redirect:/mage/");
        }
    }

    @RequestMapping(value={"/add"}, method = RequestMethod.GET)
    public ModelAndView showAddFormMage() {

        return new ModelAndView("addMage");
    }

    @RequestMapping(value={"/add"}, method = RequestMethod.POST)
    public ModelAndView addMage(RedirectAttributes redirectAttributes,
                                @RequestParam("name") String name,
                                @RequestParam("level") Long level,
                                @RequestParam("experience") Long exp)  {

        try {
            mageService.addMage(new Mage(name, level, exp));
            return new ModelAndView("redirect:/mage/");
        }catch(BadInsertException e) {
            LOGGER.debug(e);
            redirectAttributes.addFlashAttribute( "message", e.getMessage());
            return new ModelAndView("redirect:/mage/add");
        }

    }

    @RequestMapping(value={"/delete"}, method = RequestMethod.GET)
    public ModelAndView deleteMage(RedirectAttributes redirectAttributes, @RequestParam("id") Long id)  {

        try {
            mageService.removeMageById(id);
            return new ModelAndView("redirect:/mage/");
        }catch(BadRemoveException e) {
            LOGGER.debug(e);
            redirectAttributes.addFlashAttribute( "message", e.getMessage());
            return new ModelAndView("redirect:/mage/");
        }
    }

    @RequestMapping(value={"/showMageScrolls"}, method = RequestMethod.GET)
    public ModelAndView showScrollsOfMage(RedirectAttributes redirectAttributes, @RequestParam("id") Long id)  {

        try {
            ModelAndView mav = new ModelAndView("mageScrolls");
            mav.addObject("mage", mageService.getMageById(id));
            return mav;
        }catch(NoItemFoundException e) {
            LOGGER.debug(e);
            redirectAttributes.addFlashAttribute( "message", e.getMessage());
            return new ModelAndView("redirect:/mage/");
        }
    }


    @RequestMapping(value={"/update"}, method = RequestMethod.GET)
    public ModelAndView showChangeFormMage(RedirectAttributes redirectAttributes, @RequestParam("id") Long id) {

        try {
            ModelAndView mav = new ModelAndView("changeMage");
            mav.addObject("mage", mageService.getMageById(id));
            return mav;
        }catch(NoItemFoundException e) {
            LOGGER.debug(e);
            redirectAttributes.addFlashAttribute( "message", e.getMessage());
            return new ModelAndView("redirect:/mage/");
        }
    }

    @RequestMapping(value={"/update"}, method = RequestMethod.POST)
    public ModelAndView updateMage(RedirectAttributes redirectAttributes,
                                   @RequestParam("id") Long id,
                                   @RequestParam("name") String name,
                                   @RequestParam("level") Long level,
                                   @RequestParam("exp") Long exp)  {

        try {
            mageService.updateMage(new Mage(id, name, level, exp));
            return new ModelAndView("redirect:/mage/");
        }catch(BadUpdateException e) {
            LOGGER.debug(e);
            redirectAttributes.addFlashAttribute( "message", e.getMessage());
            return new ModelAndView("redirect:/mage/update", "id", id);
        }
    }


}
