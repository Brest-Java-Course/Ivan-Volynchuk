package com.epam.brest.task.domain;

import java.util.Date;
import java.util.List;

/**
 * Created by fieldistor on 15.11.14.
 */
public class Mage {

    private Long mage_id;

    private String name;

    private Long scroll_amount;

    private Long average_manacost;

    private List<MagicScroll> magicScrollList;

    public Mage() {

    }

    public Mage(String name) {
        this.name = name;
    }


    public Mage(Long mage_id, String name) {
        this.mage_id = mage_id;
        this.name = name;
    }

    public List<MagicScroll> getMagicScrollList() {
        return magicScrollList;
    }

    public void setMagicScrollList(List<MagicScroll> magicScrollList) {
        this.magicScrollList = magicScrollList;
    }

    public Long getMage_id() {
        return mage_id;
    }

    public void setMage_id(Long mage_id) {
        this.mage_id = mage_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getScroll_amount() {
        return scroll_amount;
    }

    public void setScroll_amount(Long scroll_amount) {
        this.scroll_amount = scroll_amount;
    }

    public Long getAverage_manacost() {
        return average_manacost;
    }

    public void setAverage_manacost(Long average_manacost) {
        this.average_manacost = average_manacost;
    }

    @Override
    public String toString() {
        return "{" +
                "mage_id:" + mage_id +
                ", name:'" + name + '\'' +
                ", scroll_amount:" + scroll_amount +
                ", average_manacost:" + average_manacost +
                ", magicScrollList:" + magicScrollList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mage)) return false;

        Mage mage = (Mage) o;

        if (!mage_id.equals(mage.mage_id)) return false;
        if (!name.equals(mage.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mage_id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
