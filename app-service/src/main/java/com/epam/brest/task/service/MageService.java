package com.epam.brest.task.service;

import com.epam.brest.task.domain.Mage;

import java.util.List;

/**
 * Created by fieldistor on 17.11.14.
 */
public interface MageService {

        public Mage getMageById(Long id);

        public Mage getMageByName(String name);

        public List<Mage> getAllMages();

        public Long addMage(Mage mage);
}
