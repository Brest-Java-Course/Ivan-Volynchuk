package com.epam.brest.task.clientservice;

import com.epam.brest.task.clientservice.Exception.*;
import com.epam.brest.task.domain.Mage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by fieldistor on 01.12.14.
 */
public class MageServiceClientImpl implements MageServiceClient {

    private static final Logger LOGGER = LogManager.getLogger(MageServiceClientImpl.class);
    private static final String NOT_NULL_ID = "Id should be specified.";
    private static final String NOT_NULL_NAME = "Name should be specified.";
    private static final String NOT_NULL_LEVEL = "Level should be specified.";
    private static final String NOT_NULL_EXP = "Experience should be specified.";
    private static final String NO_MAGES_TO_GET = "No mages to get.";
    private static final String NOT_NULL_MAGE = "Mage should be specified.";
    private static final String NULL_ID = "Mage's id should not be specified.";

    private String host;

    private RestTemplate restTemplate = new RestTemplate();

    public MageServiceClientImpl(String host) {
        this.host = host;
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        converters.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(converters);
        restTemplate.setErrorHandler(new RestResponseErrorHandler());
    }

    @Override
    public Mage getMageById(Long id) {

        LOGGER.debug("getMageById({})", id);

        Mage mage = null;
        try {
            Assert.notNull(id, NOT_NULL_ID);

            mage = restTemplate.getForObject(host + id, Mage.class);
        }catch(IllegalArgumentException e) {
            LOGGER.debug(e.getMessage());
            throw new NoItemFoundException(e.getMessage(), "Getting mage by id", id);
        }catch(NotFoundException e) {
            LOGGER.debug(e.getMessage(), id);
            throw new NoItemFoundException(e.getMessage(), "Getting mage by id", id);
        }
        return mage;
    }

    @Override
    public Mage getMageByName(String name) {

        LOGGER.debug("getMageByName({})", name);

        Mage mage=null;
        try{
            Assert.notNull(name, NOT_NULL_NAME);

            mage = restTemplate.getForObject(host + "name/" + name, Mage.class);
        }catch(NotFoundException e){
            LOGGER.debug(e.getMessage(), name);
            throw new NoItemFoundException(e.getMessage(), "Getting mage by name", name);
        }catch(IllegalArgumentException e){
            LOGGER.debug(e.getMessage());
            throw new NoItemFoundException(e.getMessage(), "Getting mage by name", name);
        }
        return mage;
    }

    @Override
    public List<Mage> getAllMages() {

        LOGGER.debug("getAllMages()");

        try {
            Mage[] mages = restTemplate.getForObject(host, Mage[].class);
            return Arrays.asList(mages);
        }catch(NotFoundException e) {
            LOGGER.debug(e.getMessage());
            throw new NoItemsFoundException(e.getMessage(), "Getting all mages.");
        }
    }

    @Override
    public Long addMage(Mage mage) {

        LOGGER.debug("addMage({})", mage);

        try {
            Assert.notNull(mage, NOT_NULL_MAGE);
            Assert.isNull(mage.getMage_id(), NULL_ID);
            Assert.notNull(mage.getName(), NOT_NULL_NAME);
            Assert.notNull(mage.getExp(), NOT_NULL_EXP);
            Assert.notNull(mage.getLevel(), NOT_NULL_LEVEL);

            Long id = restTemplate.postForObject(host, mage, Long.class);
            return id;
        }catch(IllegalArgumentException e) {
            LOGGER.debug(e.getMessage());
            throw new BadInsertException(e.getMessage(), "Adding mage", mage);
        }catch(BadDataException e) {
            LOGGER.debug(e.getMessage());
            throw new BadInsertException(e.getMessage(), "Adding mage", mage);
        }
    }

    @Override
    public void removeMageById(Long id) {

        LOGGER.debug("removeMageById({})", id);

        try {
            Assert.notNull(id, NOT_NULL_ID);

            restTemplate.delete(host + id);
        }catch(IllegalArgumentException e) {
            LOGGER.debug(e.getMessage());
            throw new BadRemoveException(e.getMessage(), "Removing mage.", id);
        }catch(BadDataException e) {
            LOGGER.debug(e.getMessage(), id);
            throw new BadRemoveException(e.getMessage(), "Removing mage", id);
        }
    }

    @Override
    public void updateMage(Mage mage) {

        LOGGER.debug("updateMage({})", mage);

        try {
            Assert.notNull(mage, NOT_NULL_MAGE);
            Assert.notNull(mage.getMage_id(), NOT_NULL_ID);
            Assert.notNull(mage.getName(), NOT_NULL_NAME);
            Assert.notNull(mage.getExp(), NOT_NULL_EXP);
            Assert.notNull(mage.getLevel(), NOT_NULL_LEVEL);

            restTemplate.put(host, mage);
        }catch(IllegalArgumentException e) {
            LOGGER.debug(e.getMessage());
            throw new BadUpdateException(e.getMessage(), "Updating mage", mage);
        }catch(BadDataException e) {
            LOGGER.debug(e.getMessage(), mage);
            throw new BadUpdateException(e.getMessage(), "Removing mage", mage);
        }
    }
}
