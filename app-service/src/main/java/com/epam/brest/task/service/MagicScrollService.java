package com.epam.brest.task.service;

import com.epam.brest.task.domain.MagicScroll;

import java.util.List;

/**
 * Created by fieldistor on 16.11.14.
 */
public interface MagicScrollService {

    public Long addMagicScroll(MagicScroll magicScroll);

    public List<MagicScroll> getAllMagicScrolls();

    public void removeMagicScroll(Long id);

    public MagicScroll getMagicScrollById(Long id);

    public MagicScroll getMagicScrollByDescription(String description);

    public void updateMagicScroll(MagicScroll magicScroll);

    public List<MagicScroll> getMagicScrollsByMageId(Long id);
}
