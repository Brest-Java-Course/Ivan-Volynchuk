package com.epam.brest.task.clientservice;

import com.epam.brest.task.domain.Mage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
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
    private String host;

    private RestTemplate restTemplate = new RestTemplate();

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public MageServiceClientImpl(String host) {
        this.host = host;
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        converters.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(converters);

        restTemplate.setErrorHandler(new CustomResponseErrorHandler());

    }
    @Override
    public Mage getMageById(Long id) {

        LOGGER.debug("getMageById({})", id);

        Mage mage = restTemplate.getForObject(host + id, Mage.class);
        return mage;
    }

    @Override
    public Mage getMageByName(String name) {

        LOGGER.debug("getMageByName({})", name);

        Mage mage = restTemplate.getForObject(host + "name/" + name, Mage.class);
        return mage;
    }

    @Override
    public List<Mage> getAllMages() {

        LOGGER.debug("getAllMages()");

        Mage[] mages = restTemplate.getForObject(host, Mage[].class);
        return Arrays.asList(mages);
    }

    @Override
    public Long addMage(Mage mage) {

        LOGGER.debug("addMage({})", mage);

        Long id = restTemplate.postForObject(host, mage, Long.class);
        return id;
    }

    @Override
    public void removeMageById(Long id) {

        LOGGER.debug("removeMageById({})", id);

        restTemplate.delete(host + id);
    }

    @Override
    public void updateMage(Mage mage) {

        LOGGER.debug("updateMage({})", mage);

        restTemplate.put(host, mage);
    }
}
