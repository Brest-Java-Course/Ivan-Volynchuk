package com.epam.brest.task.clientservice;

import com.epam.brest.task.domain.Mage;
import com.epam.brest.task.domain.MagicScroll;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by fieldistor on 01.12.14.
 */
public class MagicScrollServiceClientImpl implements MagicScrollServiceClient {

    private static final Logger LOGGER = LogManager.getLogger(MagicScrollServiceClientImpl.class);

    private String host;

    private RestTemplate restTemplate = new RestTemplate();

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public MagicScrollServiceClientImpl(String host) {

        this.host = host;
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        converters.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(converters);
    }

    @Override
    public Long addMagicScroll(MagicScroll magicScroll) {

        LOGGER.debug("addMagicScroll({})", magicScroll);

        Long id = restTemplate.postForObject(host, magicScroll, Long.class);
        return id;
    }

    @Override
    public List<MagicScroll> getAllMagicScrolls() {

        LOGGER.debug("getAllMagicScrolls()");

        MagicScroll[] scrolls = restTemplate.getForObject(host, MagicScroll[].class);
        return Arrays.asList(scrolls);
    }

    @Override
    public List<MagicScroll> getAllMagicScrollsAfterDate(LocalDate afterDate) {

        LOGGER.debug("getAllMagicScrollsAfterDate({})", afterDate);

        MagicScroll[] scrolls = restTemplate.getForObject(host + "filter/after/" + afterDate, MagicScroll[].class);
        return Arrays.asList(scrolls);
    }

    @Override
    public List<MagicScroll> getAllMagicScrollsBeforeDate(LocalDate beforeDate) {

        LOGGER.debug("getAllMagicScrollsBeforeDate({})", beforeDate);

        MagicScroll[] scrolls = restTemplate.getForObject(host + "filter/before/" + beforeDate, MagicScroll[].class);
        return Arrays.asList(scrolls);
    }

    @Override
    public List<MagicScroll> getAllMagicScrollsBetweenDates(LocalDate afterDate, LocalDate beforeDate) {

        LOGGER.debug("getAllMagicScrollsBetweenDates({}:{})", afterDate, beforeDate);

        MagicScroll[] scrolls = restTemplate.getForObject(host + "filter/between/" + afterDate +'/' + beforeDate, MagicScroll[].class);
        return Arrays.asList(scrolls);
    }

    @Override
    public void removeMagicScroll(Long id) {

        LOGGER.debug("removeMagicScroll({})", id);

        restTemplate.delete(host + id);
    }

    @Override
    public MagicScroll getMagicScrollById(Long id) {

        LOGGER.debug("getMagicScrollById({})", id);

        MagicScroll scroll = restTemplate.getForObject(host + id, MagicScroll.class);
        return scroll;
    }

    @Override
    public MagicScroll getMagicScrollByDescription(String description) {

        LOGGER.debug("getMagicScrollByDescription({})", description);

        MagicScroll scroll = restTemplate.getForObject(host + "description/" + description, MagicScroll.class);
        return scroll;
    }

    @Override
    public void updateMagicScroll(MagicScroll magicScroll) {

        LOGGER.debug("updateMagicScroll({})", magicScroll);

        restTemplate.put(host, magicScroll);
    }

    @Override
    public List<MagicScroll> getMagicScrollsByMageId(Long id) {

        LOGGER.debug("getMagicScrollsByMageId({})", id);

        MagicScroll[] scrolls = restTemplate.getForObject(host + "mage/" + id, MagicScroll[].class);
        return Arrays.asList(scrolls);
    }
}
