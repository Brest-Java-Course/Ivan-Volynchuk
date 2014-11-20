package com.epam.brest.task.service;

import com.epam.brest.task.dao.MagicScrollDAO;
import com.epam.brest.task.domain.MagicScroll;
import com.epam.brest.task.service.Exception.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.util.Assert;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * Created by fieldistor on 16.11.14.
 */
public class MagicScrollServiceImpl implements MagicScrollService {

    private static final Logger LOGGER = LogManager.getLogger(MagicScrollServiceImpl.class);
    private static final String NOT_NULL_ID = "Id should be specified.";
    private static final String NOT_NULL_DESCRIPTION = "Description should be specified.";
    private static final String NOT_NULL_SCROLL = "Scroll should be specified.";
    private static final String NO_SCROLLS_TO_GET = "No scrolls to get.";
    private static final String NOT_NULL_DATE = "Date should be specified." ;
    private static final String NOT_NULL_DURABILITY = "Durability should be specified.";
    private static final String NO_MAGE_SCROLLS = "Scrolls with such mage's id doesn't exist";
    private static final String NULL_ID = "Id should not be specified." ;
    private static final String NOT_NULL_MANACOST = "Mana cost should be specified.";


    @Autowired
    private MagicScrollDAO magicScrollDAO;

    @Override
    public Long addMagicScroll(MagicScroll magicScroll) {

        LOGGER.debug("addMagicScroll({})", magicScroll);

        Long id;

        try {
            Assert.notNull(magicScroll, NOT_NULL_SCROLL);
            Assert.isNull(magicScroll.getScroll_id(), NULL_ID);
            Assert.notNull(magicScroll.getCreation_date(), NOT_NULL_DATE);
            Assert.notNull(magicScroll.getDescription(), NOT_NULL_DESCRIPTION);
            Assert.notNull(magicScroll.getDurability(), NOT_NULL_DURABILITY);
            Assert.notNull(magicScroll.getMana_cost(), NOT_NULL_MANACOST);

            getMagicScrollByDescription(magicScroll.getDescription());
            throw new BadInsertException("Scroll with such description already exist",
                                                "Adding scroll", magicScroll);
        }catch(IllegalArgumentException e) {

            LOGGER.debug(e.getMessage());
            throw new BadInsertException(e.getMessage(), "Adding scroll", magicScroll);
        }catch(NotFoundException e) {

            return magicScrollDAO.addMagicScroll(magicScroll);
        }

    }

    @Override
    public List<MagicScroll> getAllMagicScrolls() {

        LOGGER.debug("getAllMagicScrolls()");

        List<MagicScroll> scrolls = null;
        try{
            scrolls = magicScrollDAO.getAllMagicScrolls();
            Assert.notEmpty(scrolls);
        }catch(IllegalArgumentException e){
            LOGGER.debug(NO_SCROLLS_TO_GET);
            throw new NotFoundException(NO_SCROLLS_TO_GET, "Getting all scrolls.", null);
        }
        return scrolls;
    }

    @Override
    public List<MagicScroll> getLimitScrolls(Long page, Long per_page) {

        LOGGER.debug("getLimitScrolls({})","Page:"+page+",Amout:"+per_page);

        List<MagicScroll> scrolls = null;
        try{
            Long n_from = page*per_page;
            scrolls = magicScrollDAO.getLimitScrolls(per_page, n_from);
            Assert.notEmpty(scrolls);
        }catch(IllegalArgumentException e){
            LOGGER.debug(NO_SCROLLS_TO_GET);
            throw new NotFoundException(NO_SCROLLS_TO_GET, "Getting limited scrolls.", null);
        }
        return scrolls;
    }

    @Override
    public void removeMagicScroll(Long id) {

        LOGGER.debug("removeMagicScroll({})", id);

        try {
            Assert.notNull(id, NOT_NULL_ID);
            getMagicScrollById(id);
        }catch(IllegalArgumentException e){

            LOGGER.debug(NOT_NULL_ID);
            throw new BadRemoveException(e.getMessage(), "Removing scroll.", id);
        }catch(NotFoundException e){

            LOGGER.debug("No scroll with such id ({})", id);
            throw new BadRemoveException(e.getMessage(), "Removing scroll", id);
        }

        magicScrollDAO.removeMagicScroll(id);


    }

    @Override
    public MagicScroll getMagicScrollById(Long id) {

        LOGGER.debug("getMagicScrollById({})", id);

        MagicScroll scroll=null;
        try{
            Assert.notNull(id,NOT_NULL_ID);
            scroll = magicScrollDAO.getMagicScrollById(id);
        }catch(EmptyResultDataAccessException e){

            LOGGER.debug("Scroll with ({}) id doesn't exist", id);
            throw new NotFoundException("Scroll with such id doesn't exist", "Getting scroll by id", id);
        }catch(IllegalArgumentException e){

            LOGGER.debug(NOT_NULL_ID);
            throw new NotFoundException(e.getMessage(), "Getting scroll by id", id);
        }
        return scroll;

    }

    @Override
    public MagicScroll getMagicScrollByDescription(String description) {

        LOGGER.debug("getMagicScrollByDescription({})", description);

        MagicScroll scroll=null;
        try{
            Assert.notNull(description, NOT_NULL_DESCRIPTION);
            scroll = magicScrollDAO.getMagicScrollByDescription(description);
        }catch(EmptyResultDataAccessException e){

            LOGGER.debug("No scroll with such description ({})", description);
            throw new NotFoundException("Scroll with such description doesn't exist", "Getting scroll by description", description );
        }catch(IllegalArgumentException e){

            LOGGER.debug(NOT_NULL_DESCRIPTION);
            throw new NotFoundException(NOT_NULL_DESCRIPTION, "Getting scroll by description", description );
        }
        return scroll;
    }

    @Override
    public void updateMagicScroll(MagicScroll magicScroll) {

        LOGGER.debug("updateMagicScroll({})", magicScroll);

        try {
            Assert.notNull(magicScroll, NOT_NULL_SCROLL);
            Assert.notNull(magicScroll.getScroll_id(), NOT_NULL_ID);
            Assert.notNull(magicScroll.getCreation_date(), NOT_NULL_DATE);
            Assert.notNull(magicScroll.getDescription(), NOT_NULL_DESCRIPTION);

            getMagicScrollById(magicScroll.getScroll_id());
            magicScrollDAO.updateMagicScroll(magicScroll);
        }catch(IllegalArgumentException e) {

            LOGGER.debug(e.getMessage());
            throw new BadUpdateException(e.getMessage(), "Updating scroll", magicScroll);
        }catch(NotFoundException e) {

            LOGGER.debug(e.getMessage());
            throw new BadUpdateException(e.getMessage(), "Updating scroll", magicScroll);
        }

    }

    @Override
    public List<MagicScroll> getMagicScrollsByMageId(Long id) {

        LOGGER.debug("getMagicScrollsByMageId({})", id);

        List<MagicScroll> scrolls = null;
        try {
            Assert.notNull(id, NOT_NULL_ID);

            scrolls = magicScrollDAO.getMagicScrollsByMageId(id);
            Assert.notEmpty(scrolls,NO_MAGE_SCROLLS);
        }catch(IllegalArgumentException e) {

            LOGGER.debug(e.getMessage());
            throw new NotFoundException(e.getMessage(), "Getting scrolls by mage's id", id);
        }
        return scrolls;
    }

    @Override
    public List<MagicScroll> getLimitMagicScrollsByMageId(Long id, Long page, Long per_page) {

        LOGGER.debug("getLimitMagicScrollsByMageId({})","MageId:"+id+"Page:"+page+",Amout:"+per_page);

        List<MagicScroll> scrolls = null;
        try{
            Long n_from = page*per_page;
            scrolls = magicScrollDAO.getLimitMagicScrollsByMageId(id, per_page, n_from);
            Assert.notEmpty(scrolls);
        }catch(IllegalArgumentException e){
            LOGGER.debug(NO_MAGE_SCROLLS);
            throw new NotFoundException(NO_MAGE_SCROLLS, "Getting limited scrolls by mage id.", id);
        }
        return scrolls;
    }

    @Override
    public List<MagicScroll> getMagicScrollsWithoutMage() {

        LOGGER.debug("getMagicScrollsWithoutMage()");

        List<MagicScroll> scrolls = null;
        try {
            scrolls = magicScrollDAO.getMagicScrollsWithoutMage();
            Assert.notEmpty(scrolls,NO_SCROLLS_TO_GET);
        }catch(IllegalArgumentException e) {

            LOGGER.debug(e.getMessage());
            throw new NotFoundException(e.getMessage(), "Getting scrolls without mage", null);
        }
        return scrolls;
    }

    @Override
    public List<MagicScroll> getLimitMagicScrollsWithoutMage(Long page, Long per_page) {

        LOGGER.debug("getLimitMagicScrollsWithoutMage({})","Page:"+page+",Amout:"+per_page);

        List<MagicScroll> scrolls = null;
        try{
            Long n_from = page*per_page;
            scrolls = magicScrollDAO.getLimitMagicScrollsWithoutMage(per_page, n_from);
            Assert.notEmpty(scrolls);
        }catch(IllegalArgumentException e){
            LOGGER.debug(NO_SCROLLS_TO_GET);
            throw new NotFoundException(NO_SCROLLS_TO_GET, "Getting limited scrolls without mage.", null);
        }
        return scrolls;
    }
}
