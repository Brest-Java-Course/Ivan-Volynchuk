package com.epam.brest.task.domain;

import java.util.Date;

/**
 * Created by fieldistor on 15.11.14.
 */
public class MagicScroll {

    private Long scroll_id;

    private String description;

    private Long durability;

    private Date creation_date;

    private Long mana_cost;





    public MagicScroll() {

    }

    public MagicScroll(Long scroll_id, String description, Long durability,
                       Date creation_date, Long mana_cost) {
        this.scroll_id = scroll_id;
        this.description = description;
        this.durability = durability;
        this.creation_date = creation_date;
        this.mana_cost = mana_cost;
    }

    public Long getScroll_id() {
        return scroll_id;
    }

    public void setScroll_id(Long scroll_id) {
        this.scroll_id = scroll_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDurability() {
        return durability;
    }

    public void setDurability(Long durability) {
        this.durability = durability;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public Long getMana_cost() {
        return mana_cost;
    }

    public void setMana_cost(Long mana_cost) {
        this.mana_cost = mana_cost;
    }

    @Override
    public String toString() {
        return "{" +
                "scroll_id:" + scroll_id +
                ", description:'" + description + '\'' +
                ", durability:" + durability +
                ", creation_date:" + creation_date +
                ", mana_cost:" + mana_cost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MagicScroll)) return false;

        MagicScroll that = (MagicScroll) o;

        if (!creation_date.equals(that.creation_date)) return false;
        if (!description.equals(that.description)) return false;
        if (!durability.equals(that.durability)) return false;
        if (!mana_cost.equals(that.mana_cost)) return false;
        if (!scroll_id.equals(that.scroll_id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = scroll_id.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + durability.hashCode();
        result = 31 * result + creation_date.hashCode();
        result = 31 * result + mana_cost.hashCode();
        return result;
    }
}
