package com.epam.brest.task.dao;

import com.epam.brest.task.domain.Mage;
import com.epam.brest.task.domain.MagicScroll;

import java.util.List;

/**
 * Created by fieldistor on 15.11.14.
 */
public interface MagicScrollDAO {

    public Long addMagicScroll(MagicScroll magicScroll);

    public List<MagicScroll> getAllMagicScrolls();

    public List<MagicScroll> getLimitScrolls(Long amt, Long n_from);

    public void removeMagicScroll(Long id);

    public MagicScroll getMagicScrollById(Long id);

    public MagicScroll getMagicScrollByDescription(String description);

    public void updateMagicScroll(MagicScroll magicScroll);

    public void clearScrollsByMagicId(Long id);

    public List<MagicScroll> getMagicScrollsByMageId(Long id);

    public List<MagicScroll> getLimitMagicScrollsByMageId(Long id, Long amt, Long n_from);

    public List<MagicScroll> getMagicScrollsWithoutMage();

    public List<MagicScroll> getLimitMagicScrollsWithoutMage(Long amt, Long n_from);


    /*
    public Long getScrollAmountByMageId(Long id);

    public Long getAverageManacostByMageId(Long id);
    */
}
