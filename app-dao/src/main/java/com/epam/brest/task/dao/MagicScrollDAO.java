package com.epam.brest.task.dao;

import com.epam.brest.task.domain.Mage;
import com.epam.brest.task.domain.MagicScroll;
import org.joda.time.LocalDate;

import java.util.List;

/**
 * Created by fieldistor on 15.11.14.
 */
public interface MagicScrollDAO {

    /**
     * The method used for adding the scroll to base.
     *
     * @param magicScroll the scroll to add
     * @return id of added scroll
     */
    public Long addMagicScroll(MagicScroll magicScroll);

    /**
     * The method returns a list of scrolls that can then be processed.
     * This method always returns immediately, whether or not any scrolls exist.
     *
     * @return list of scrolls
     */
    public List<MagicScroll> getAllMagicScrolls();

    /**
     * The method returns a limited list of scrolls that can then be processed.
     * This method always returns immediately, whether or not any scrolls exist.
     *
     * @param n_from the number of lower bound
     * @param amt number scrolls to be got from base
     * @return limited list of scrolls
     */
    public List<MagicScroll> getLimitScrolls(Long amt, Long n_from);

    /**
     * The method returns a list of scrolls after specified date that can then be processed.
     * This method always returns immediately, whether or not any scrolls exist.
     *
     * @param afterDate date that determine list of scrolls that follow after this date
     * @return list of scrolls
     */
    public List<MagicScroll> getAllMagicScrollsAfterDate(LocalDate afterDate);

    /**
     * The method returns a list of scrolls before specified date that can then be processed.
     * This method always returns immediately, whether or not any scrolls exist.
     *
     * @param beforeDate date that determine list of scrolls that follow before this date
     * @return list of scrolls
     */
    public List<MagicScroll> getAllMagicScrollsBeforeDate(LocalDate beforeDate);

    /**
     * The method returns a list of scrolls between specified date that can then be processed.
     * This method always returns immediately, whether or not any scrolls exist.
     *
     * @param afterDate date that determine list of scrolls that follow after this date
     * @param beforeDate date that determine list of scrolls that follow before this date
     * @return list of scrolls
     */
    public List<MagicScroll> getAllMagicScrollsBetweenDates(LocalDate afterDate, LocalDate beforeDate);

    /**
     * The method returns number of scrolls.
     *
     * @return number of scrolls
     */
    public Long amountScrolls();

    /**
     * The method used for deleting the scroll from base by id.
     *
     * @param id id of scroll to remove
     */
    public void removeMagicScroll(Long id);

    /**
     * The method used for getting the scroll from base by id.
     *
     * @param id id of scroll to get
     * @return scroll with requested id
     */
    public MagicScroll getMagicScrollById(Long id);

    /**
     * The method used for getting the scroll from base by description.
     *
     * @param description id of getting scroll
     * @return scroll with requested description
     */

    public MagicScroll getMagicScrollByDescription(String description);

    /**
     * The method used for updating existing scroll.
     *
     * @param magicScroll data to update the specific scroll
     */
    public void updateMagicScroll(MagicScroll magicScroll);


    /**
     * The method used for breaking relation between scroll and mage.
     *
     * @param id id of mage to null all scrolls with this id
     */
    public void clearScrollsByMagicId(Long id);

    /**
     * The method returns mage's list of scrolls that can then be processed.
     * This method always returns immediately, whether or not any scrolls exist.
     *
     * @param id id of mage
     * @return list of scrolls
     */
    public List<MagicScroll> getMagicScrollsByMageId(Long id);

    /**
     * The method returns a limited mage's list of scrolls that can then be processed.
     * This method always returns immediately, whether or not any scrolls exist.
     *
     * @param id id of mage
     * @param n_from the number of lower bound
     * @param amt number scrolls to be got from base
     * @return limited list of scrolls
     */
    public List<MagicScroll> getLimitMagicScrollsByMageId(Long id, Long amt, Long n_from);

    /**
     * The method returns number of scrolls by id of mage.
     *
     * @return number of scrolls
     */
    public Long amountScrollsByMageId(Long id);

    /**
     * The method returns list of scrolls without mage that can then be processed.
     * This method always returns immediately, whether or not any scrolls exist.
     *
     * @return list of scrolls
     */
    public List<MagicScroll> getMagicScrollsWithoutMage();

    /**
     * The method returns a limited list of scrolls without mage that can then be processed.
     * This method always returns immediately, whether or not any scrolls exist.
     *
     * @param n_from the number of lower bound
     * @param amt number scrolls to be got from base
     * @return limited list of scrolls
     */
    public List<MagicScroll> getLimitMagicScrollsWithoutMage(Long amt, Long n_from);

    /**
     * The method returns number of scrolls without mage.
     *
     * @return number of scrolls
     */
    public Long amountScrollsWithoutMage();

}
