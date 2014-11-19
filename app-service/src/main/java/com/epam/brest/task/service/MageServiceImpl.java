package com.epam.brest.task.service;

import com.epam.brest.task.dao.MageDAO;
import com.epam.brest.task.dao.MagicScrollDAO;
import com.epam.brest.task.domain.Mage;
import com.epam.brest.task.domain.MagicScroll;
import com.epam.brest.task.service.Exception.BadInsertException;
import com.epam.brest.task.service.Exception.BadRemoveException;
import com.epam.brest.task.service.Exception.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by fieldistor on 17.11.14.
 */
public class MageServiceImpl implements MageService {

    private static final Logger LOGGER = LogManager.getLogger(MageServiceImpl.class);
    private static final String NOT_NULL_ID = "Id should be specified.";
    private static final String NOT_NULL_NAME = "Name should be specified.";
    private static final String NO_MAGES_TO_GET = "No mages to get";
    private static final String NOT_NULL_MAGE = "Mage should be specified";
    private static final String NULL_ID = "Mage should not be specified";

    @Autowired
    private MageDAO mageDAO;

    @Autowired
    private MagicScrollDAO magicScrollDAO;

    @Override
    public Mage getMageById(Long id) {

        LOGGER.debug("getMageById({})", id);

        Mage mage=null;
        try{
            Assert.notNull(id, NOT_NULL_ID);

            mage = mageDAO.getMageById(id);
            mage.setMagicScrollList( magicScrollDAO.getMagicScrollsByMageId(mage.getMage_id()));
        }catch(EmptyResultDataAccessException e) {

            LOGGER.debug("Mage with ({}) id doesn't exist", id);
            throw new NotFoundException("Mage with such id doesn't exist", "Getting mage by id", id);
        }catch(IllegalArgumentException e) {

            LOGGER.debug(e.getMessage());
            throw new NotFoundException(e.getMessage(), "Getting mage by id", id);
        }catch(NotFoundException e) {

            LOGGER.debug("No scrolls for ({})", mage);
        }
        return mage;
    }

    @Override
    public Mage getMageByName(String name) {

        LOGGER.debug("getMageByName({})", name);

        Mage mage=null;
        try{
            Assert.notNull(name, NOT_NULL_NAME);

            mage = mageDAO.getMageByName(name);
            mage.setMagicScrollList( magicScrollDAO.getMagicScrollsByMageId(mage.getMage_id()));
        }catch(EmptyResultDataAccessException e){

            LOGGER.debug("Mage with ({}) name doesn't exist", name);
            throw new NotFoundException("Mage with such name doesn't exist", "Getting mage by name", name);
        }catch(IllegalArgumentException e){

            LOGGER.debug(e.getMessage());
            throw new NotFoundException(e.getMessage(), "Getting mage by name", name);
        }catch(NotFoundException e) {

            LOGGER.debug("No scrolls for ({})", mage);
        }
        return mage;
    }

    @Override
    public List<Mage> getAllMages() {

        LOGGER.debug("getAllMages()");

        List<Mage> mages = null;
        try{
            mages = mageDAO.getAllMages();
            Assert.notEmpty(mages, NO_MAGES_TO_GET);
        }catch(IllegalArgumentException e){

            LOGGER.debug(e.getMessage());
            throw new NotFoundException(e.getMessage(), "Getting all mages.", null);
        }
        return mages;
    }

    @Override
    public List<Mage> getLimitMages(Long page, Long per_page) {

        LOGGER.debug("getLimitMages({})","Page:"+page+",Amout:"+per_page);

        List<Mage> mages = null;
        try{
            Long n_from = page*per_page;
            mages = mageDAO.getLimitMages(per_page, n_from);
            Assert.notEmpty(mages, NO_MAGES_TO_GET);
        }catch(IllegalArgumentException e){

            LOGGER.debug(e.getMessage());
            throw new NotFoundException(e.getMessage(), "Getting limited mages.", null);
        }
        return mages;
    }

    @Override
    public Long addMage(Mage mage) {

        LOGGER.debug("addMage({})", mage);

        Long id;

        try {
            Assert.notNull(mage, NOT_NULL_MAGE);
            Assert.isNull(mage.getMage_id(), NULL_ID);
            Assert.notNull(mage.getName(), NOT_NULL_NAME);

            getMageByName(mage.getName());
            throw new BadInsertException("Mage with such description already exist",
                    "Adding mage", mage);
        }catch(IllegalArgumentException e) {

            LOGGER.debug(e.getMessage());
            throw new BadInsertException(e.getMessage(), "Adding mage", mage);
        }catch(NotFoundException e) {

            return mageDAO.addMage(mage);
        }
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public void removeMageById(Long id) {

        LOGGER.debug("removeMageById({})", id);

        try {
            Assert.notNull(id, NOT_NULL_ID);
            getMageById(id);
            mageDAO.removeMageById(id);
            magicScrollDAO.clearScrollsByMagicId(id);
        }catch(IllegalArgumentException e){

            LOGGER.debug(e.getMessage());
            throw new BadRemoveException(e.getMessage(), "Removing mage.", id);
        }catch(NotFoundException e){

            LOGGER.debug("No mage with such id ({})", id);
            throw new BadRemoveException(e.getMessage(), "Removing mage", id);
        }


    }
}
