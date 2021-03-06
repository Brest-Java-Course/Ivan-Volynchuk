package com.epam.brest.task.domain;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.util.Date;

/**
 * Created by fieldistor on 15.11.14.
 */
public class MagicScroll {

    private Long scroll_id;

    private String description;

    private Long durability;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private LocalDate creation_date;

    private Long mana_cost;

    private Long mage_id;

    public Long getMage_id() {
        return mage_id;
    }

    public void setMage_id(Long mage_id) {
        this.mage_id = mage_id;
    }

    public MagicScroll() {

    }

    public MagicScroll(String description, Long durability, LocalDate creation_date, Long mana_cost, Long mage_id) {
        this.description = description;
        this.durability = durability;
        this.creation_date = creation_date;
        this.mana_cost = mana_cost;
        this.mage_id = mage_id;
    }

    public MagicScroll(String description, Long durability, LocalDate creation_date, Long mana_cost) {
        this.mana_cost = mana_cost;
        this.creation_date = creation_date;
        this.durability = durability;
        this.description = description;
    }

    public MagicScroll(Long scroll_id, String description, Long durability,
                       LocalDate creation_date, Long mana_cost) {
        this.scroll_id = scroll_id;
        this.description = description;
        this.durability = durability;
        this.creation_date = creation_date;
        this.mana_cost = mana_cost;
    }

    public MagicScroll(Long scroll_id, String description, Long durability, LocalDate creation_date,
                       Long mana_cost, Long mage_id) {
        this.scroll_id = scroll_id;
        this.description = description;
        this.durability = durability;
        this.creation_date = creation_date;
        this.mana_cost = mana_cost;
        this.mage_id = mage_id;
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

    public LocalDate getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(LocalDate creation_date) {
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
        return "MagicScroll{" +
                "scroll_id=" + scroll_id +
                ", description='" + description + '\'' +
                ", durability=" + durability +
                ", creation_date=" + creation_date +
                ", mana_cost=" + mana_cost +
                ", mage_id=" + mage_id +
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

class CustomDateSerializer extends JsonSerializer<LocalDate> {

    private static DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

    @Override
    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider arg2)
            throws IOException, JsonProcessingException {

        gen.writeString(formatter.print(value));
    }
}
class CustomDateDeserializer extends JsonDeserializer<LocalDate> {

    private static DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

    @Override
    public LocalDate deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext)
                                throws IOException, JsonProcessingException {

        String date = jsonparser.getText();
        return formatter.parseLocalDate(date);

    }

}