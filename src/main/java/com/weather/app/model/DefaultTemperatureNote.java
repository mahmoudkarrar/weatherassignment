package com.weather.app.model;

import javax.persistence.*;

/**
 * Created by Mahmoud.Fathy on 4/11/2017.
 */
@Entity
@Table(name = "Default_Temperature_Note")
public class DefaultTemperatureNote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Integer fromTemp;

    private Integer toTemp;

    private String note;

    public DefaultTemperatureNote(Integer fromTemp, Integer toTemp, String note) {
        this.fromTemp = fromTemp;
        this.toTemp = toTemp;
        this.note = note;
    }

    public DefaultTemperatureNote() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getFromTemp() {
        return fromTemp;
    }

    public void setFromTemp(Integer fromTemp) {
        this.fromTemp = fromTemp;
    }

    public Integer getToTemp() {
        return toTemp;
    }

    public void setToTemp(Integer toTemp) {
        this.toTemp = toTemp;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
