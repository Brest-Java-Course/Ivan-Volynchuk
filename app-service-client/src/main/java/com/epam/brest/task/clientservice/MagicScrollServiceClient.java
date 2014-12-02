package com.epam.brest.task.clientservice;

import com.epam.brest.task.domain.MagicScroll;
import org.joda.time.LocalDate;

import java.util.List;

/**
 * Created by fieldistor on 01.12.14.
 */
public interface MagicScrollServiceClient {

    public Long addMagicScroll(MagicScroll magicScroll);

    public List<MagicScroll> getAllMagicScrolls();

    public List<MagicScroll> getAllMagicScrollsAfterDate(LocalDate afterDate);

    public List<MagicScroll> getAllMagicScrollsBeforeDate(LocalDate beforeDate);

    public List<MagicScroll> getAllMagicScrollsBetweenDates(LocalDate afterDate, LocalDate beforeDate);

    public void removeMagicScroll(Long id);

    public MagicScroll getMagicScrollById(Long id);

    public MagicScroll getMagicScrollByDescription(String description);

    public void updateMagicScroll(MagicScroll magicScroll);

    public List<MagicScroll> getMagicScrollsByMageId(Long id);


}
