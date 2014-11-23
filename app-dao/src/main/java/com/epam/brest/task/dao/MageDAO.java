package com.epam.brest.task.dao;

import com.epam.brest.task.domain.Mage;
import com.epam.brest.task.domain.MagicScroll;

import java.util.List;

/**
 * Created by fieldistor on 16.11.14.
 */
public interface MageDAO {

    public Mage getMageById(Long id);

    public Mage getMageByName(String name);

    public List<Mage> getAllMages();

    public List<Mage> getLimitMages(Long amt, Long n_from);

    public Long amountMages();

    public Long addMage(Mage mage);

    public void removeMageById(Long id);

    public void updateMage(Mage mage);
}
