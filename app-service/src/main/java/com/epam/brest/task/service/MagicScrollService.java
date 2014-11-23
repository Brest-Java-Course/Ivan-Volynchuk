package com.epam.brest.task.service;

import com.epam.brest.task.domain.MagicScroll;
import org.joda.time.LocalDate;

import java.util.List;

/**
 * Created by fieldistor on 16.11.14.
 */
public interface MagicScrollService {

    public Long addMagicScroll(MagicScroll magicScroll);

    public List<MagicScroll> getAllMagicScrolls();

    public List<MagicScroll> getLimitScrolls(Long page, Long per_page);

    public List<MagicScroll> getAllMagicScrollsAfterDate(LocalDate afterDate);

    public List<MagicScroll> getAllMagicScrollsBeforeDate(LocalDate beforeDate);

    public List<MagicScroll> getAllMagicScrollsBetweenDates(LocalDate afterDate, LocalDate beforeDate);

    public Long amountScrolls();

    public void removeMagicScroll(Long id);

    public MagicScroll getMagicScrollById(Long id);

    public MagicScroll getMagicScrollByDescription(String description);

    public void updateMagicScroll(MagicScroll magicScroll);

    public List<MagicScroll> getMagicScrollsByMageId(Long id);

    public List<MagicScroll> getLimitMagicScrollsByMageId(Long id, Long page, Long per_page);

    public Long amountScrollsByMageId(Long id);

    public List<MagicScroll> getMagicScrollsWithoutMage();

    public List<MagicScroll> getLimitMagicScrollsWithoutMage(Long page, Long per_page);

    public Long amountScrollsWithoutMage();
}
