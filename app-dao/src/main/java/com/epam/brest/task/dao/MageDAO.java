package com.epam.brest.task.dao;

import com.epam.brest.task.domain.Mage;
import com.epam.brest.task.domain.MagicScroll;

import java.util.List;

/**
 * Created by fieldistor on 16.11.14.
 */
public interface MageDAO {

    /**
     * The method used for getting the mage from base by id.
     *
     * @param id id of getting mage
     * @return mage with requested id
     */
    public Mage getMageById(Long id);

    /**
     * The method used for getting the mage from base by name.
     *
     * @param name name of getting mage
     * @return mage with requested name
     */
    public Mage getMageByName(String name);

    /**
     * The method returns a list of mages that can then be processed.
     * This method always returns immediately, whether or not any mages exist.
     *
     * @return list of mages
     */
    public List<Mage> getAllMages();

    /**
     * The method returns a limit list of mages that can then be processed.
     * This method always returns immediately, whether or not any mages exist.
     *
     * @param n_from the lower bound number
     * @param amt number mages to be got from base
     * @return limited list of mages
     */
    public List<Mage> getLimitMages(Long amt, Long n_from);

    /**
     * The method returns number of mages.
     *
     * @return number of mages
     */
    public Long amountMages();

    /**
     * The method used for adding the mage to base.
     *
     * @param mage the mage to be added
     * @return id of mage
     */
    public Long addMage(Mage mage);

    /**
     * The method used for deleting the mage from base by id.
     *
     * @param id the id of mage for removing
     */
    public void removeMageById(Long id);

    /**
     * The method used for updating existing user.
     *
     * @param mage data to update the specific user
     */
    public void updateMage(Mage mage);
}
