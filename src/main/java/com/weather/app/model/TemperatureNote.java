package com.weather.app.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Mahmoud.Fathy on 4/11/2017.
 */
@Entity
@Table(name = "Temperature_Note")
public class TemperatureNote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Date creationDate;

    private String note;

    private Long userId;

    public TemperatureNote(Date creationDate, String note, Long userId) {
        this.creationDate = creationDate;
        this.note = note;
        this.userId = userId;
    }

    public TemperatureNote() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "TemperatureNote{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", note='" + note + '\'' +
                ", userId=" + userId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TemperatureNote that = (TemperatureNote) o;

        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;
        return userId != null ? userId.equals(that.userId) : that.userId == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }
}
