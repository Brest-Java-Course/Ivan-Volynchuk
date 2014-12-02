package com.epam.brest.task.clientservice;

import com.epam.brest.task.domain.Mage;

import java.util.List;

/**
 * Created by fieldistor on 01.12.14.
 */
public interface MageServiceClient {

    public Mage getMageById(Long id);

    public Mage getMageByName(String name);

    public List<Mage> getAllMages();

    public Long addMage(Mage mage);

    public void removeMageById(Long id);

    public void updateMage(Mage mage);
}
