package com.epam.brest.task.clientservice;

import com.epam.brest.task.clientservice.Exception.*;
import com.epam.brest.task.domain.Mage;
import com.epam.brest.task.domain.MagicScroll;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by fieldistor on 01.12.14.
 */
public class MagicScrollServiceClientImpl implements MagicScrollServiceClient {

    private static final String NOT_NULL_ID = "Id should be specified.";
    private static final String NOT_NULL_DESCRIPTION = "Description should be specified.";
    private static final String NOT_NULL_SCROLL = "Scroll should be specified.";
    private static final String NO_SCROLLS_TO_GET = "No scrolls to get.";
    private static final String NOT_NULL_DATE = "Date should be specified." ;
    private static final String NOT_NULL_DURABILITY = "Durability should be specified.";
    private static final String NO_MAGE_SCROLLS = "Scrolls with such mage's id doesn't exist";
    private static final String NULL_ID = "Id should not be specified." ;
    private static final String NOT_NULL_MANACOST = "Mana cost should be specified.";

    private static final Logger LOGGER = LogManager.getLogger(MagicScrollServiceClientImpl.class);

    private String host;

    private RestTemplate restTemplate = new RestTemplate();

    public MagicScrollServiceClientImpl(String host) {

        this.host = host;
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        converters.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(converters);
        restTemplate.setErrorHandler(new RestResponseErrorHandler());

    }

    @Override
    public Long addMagicScroll(MagicScroll magicScroll) {

        LOGGER.debug("addMagicScroll({})", magicScroll);

        try {
            Assert.notNull(magicScroll, NOT_NULL_SCROLL);
            Assert.isNull(magicScroll.getScroll_id(), NULL_ID);
            Assert.notNull(magicScroll.getCreation_date(), NOT_NULL_DATE);
            Assert.notNull(magicScroll.getDescription(), NOT_NULL_DESCRIPTION);
            Assert.notNull(magicScroll.getDurability(), NOT_NULL_DURABILITY);
            Assert.notNull(magicScroll.getMana_cost(), NOT_NULL_MANACOST);

            Long id = restTemplate.postForObject(host, magicScroll, Long.class);
            return id;
        }catch(IllegalArgumentException e) {

            LOGGER.debug(e.getMessage());
            throw new BadInsertException(e.getMessage(), "Adding scroll", magicScroll);
        }catch(BadDataException e) {

            LOGGER.debug(e.getMessage());
            throw new BadInsertException(e.getMessage(), "Adding scroll", magicScroll);
        }
    }

    @Override
    public List<MagicScroll> getAllMagicScrolls() {

        LOGGER.debug("getAllMagicScrolls()");

        try {
            MagicScroll[] scrolls = restTemplate.getForObject(host, MagicScroll[].class);
            return Arrays.asList(scrolls);
        }catch(NotFoundException e){
            LOGGER.debug(e.getMessage());
            throw new NoItemsFoundException(e.getMessage(), "Getting all scrolls.");
        }
    }

    @Override
    public List<MagicScroll> getAllMagicScrollsAfterDate(LocalDate afterDate) {

        LOGGER.debug("getAllMagicScrollsAfterDate({})", afterDate);

        try {
            Assert.notNull(afterDate, NOT_NULL_DATE);

            MagicScroll[] scrolls = restTemplate.getForObject(host + "filter/after/" + afterDate, MagicScroll[].class);
            return Arrays.asList(scrolls);
        }catch(IllegalArgumentException e){
            LOGGER.debug(e.getMessage());
            throw new NoItemsFoundException(e.getMessage(), "Getting all scrolls after Date.");
        }catch(NotFoundException e){
            LOGGER.debug(e.getMessage());
            throw new NoItemsFoundException(e.getMessage(), "Getting all scrolls after Date.");
        }
    }

    @Override
    public List<MagicScroll> getAllMagicScrollsBeforeDate(LocalDate beforeDate) {

        LOGGER.debug("getAllMagicScrollsBeforeDate({})", beforeDate);

        try {
            Assert.notNull(beforeDate, NOT_NULL_DATE);

            MagicScroll[] scrolls = restTemplate.getForObject(host + "filter/before/" + beforeDate, MagicScroll[].class);
            return Arrays.asList(scrolls);
        }catch(IllegalArgumentException e){
            LOGGER.debug(e.getMessage());
            throw new NoItemsFoundException(e.getMessage(), "Getting all scrolls before Date.");
        }catch(NotFoundException e){
            LOGGER.debug(e.getMessage());
            throw new NoItemsFoundException(e.getMessage(), "Getting all scrolls before Date.");
        }
    }

    @Override
    public List<MagicScroll> getAllMagicScrollsBetweenDates(LocalDate afterDate, LocalDate beforeDate) {

        LOGGER.debug("getAllMagicScrollsBetweenDates({}-{})", afterDate, beforeDate);

        try {
            Assert.notNull(beforeDate, NOT_NULL_DATE);
            Assert.notNull(afterDate, NOT_NULL_DATE);

            MagicScroll[] scrolls = restTemplate.getForObject(host + "filter/between/" + afterDate +'/' + beforeDate, MagicScroll[].class);
            return Arrays.asList(scrolls);
        }catch(IllegalArgumentException e){
            LOGGER.debug(e.getMessage());
            throw new NoItemsFoundException(e.getMessage(), "Getting all scrolls between dates.");
        }catch(NotFoundException e){
            LOGGER.debug(e.getMessage());
            throw new NoItemsFoundException(e.getMessage(), "Getting all scrolls between dates.");
        }
    }

    @Override
    public void removeMagicScroll(Long id) {

        LOGGER.debug("removeMagicScroll({})", id);

        try {
            Assert.notNull(id, NOT_NULL_ID);

            restTemplate.delete(host + id);
        }catch(IllegalArgumentException e){
            LOGGER.debug(NOT_NULL_ID);
            throw new BadRemoveException(e.getMessage(), "Removing scroll.", id);
        }catch(BadDataException e){
            LOGGER.debug(e.getMessage(), id);
            throw new BadRemoveException(e.getMessage(), "Removing scroll", id);
        }
    }

    @Override
    public MagicScroll getMagicScrollById(Long id) {

        LOGGER.debug("getMagicScrollById({})", id);

        try {
            Assert.notNull(id,NOT_NULL_ID);

            MagicScroll scroll = restTemplate.getForObject(host + id, MagicScroll.class);
            return scroll;
        }catch(NotFoundException e){
            LOGGER.debug(e.getMessage(), id);
            throw new NoItemFoundException(e.getMessage(), "Getting scroll by id", id);
        }catch(IllegalArgumentException e){
            LOGGER.debug(e.getMessage());
            throw new NoItemFoundException(e.getMessage(), "Getting scroll by id", id);
        }
    }

    @Override
    public MagicScroll getMagicScrollByDescription(String description) {

        LOGGER.debug("getMagicScrollByDescription({})", description);

        try {
            Assert.notNull(description, NOT_NULL_DESCRIPTION);

            MagicScroll scroll = restTemplate.getForObject(host + "description/" + description, MagicScroll.class);
            return scroll;
        }catch(NotFoundException e){
            LOGGER.debug(e.getMessage(), description);
            throw new NoItemFoundException(e.getMessage(), "Getting scroll by description", description);
        }catch(IllegalArgumentException e){
            LOGGER.debug(e.getMessage());
            throw new NoItemFoundException(e.getMessage(), "Getting scroll by description", description);
        }
    }

    @Override
    public void updateMagicScroll(MagicScroll magicScroll) {

        LOGGER.debug("updateMagicScroll({})", magicScroll);

        try {
            Assert.notNull(magicScroll, NOT_NULL_SCROLL);
            Assert.notNull(magicScroll.getScroll_id(), NOT_NULL_ID);
            Assert.notNull(magicScroll.getCreation_date(), NOT_NULL_DATE);
            Assert.notNull(magicScroll.getDescription(), NOT_NULL_DESCRIPTION);
            Assert.notNull(magicScroll.getDurability(), NOT_NULL_DURABILITY);
            Assert.notNull(magicScroll.getMana_cost(), NOT_NULL_MANACOST);

            restTemplate.put(host, magicScroll);
        }catch(IllegalArgumentException e) {
            LOGGER.debug(e.getMessage());
            throw new BadUpdateException(e.getMessage(), "Updating scroll", magicScroll);
        }catch(BadDataException e) {
            LOGGER.debug(e.getMessage());
            throw new BadUpdateException(e.getMessage(), "Updating scroll", magicScroll);
        }
    }
}
