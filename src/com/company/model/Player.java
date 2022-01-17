package com.company.model;

import java.time.LocalDate;

public class Player {
    private Integer id_p;
    private String name_p;
    private Integer age;
    private Integer id_fc;
    private LocalDate dateOfBirth;
    private FootballClub footballClub;

    public Integer getId_p() {
        return id_p;
    }

    public void setId_p(Integer id_p) {
        this.id_p = id_p;
    }

    public String getName_p() {
        return name_p;
    }

    public void setName_p(String name_p) {
        this.name_p = name_p;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getId_fc() {
        return id_fc;
    }

    public void setId_fc(Integer id_fc) {
        this.id_fc = id_fc;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public FootballClub getFootballClub() {
        return footballClub;
    }

    public void setFootballClub(FootballClub footballClub) {
        this.footballClub = footballClub;
    }

    @Override
    public String toString() {
        return String.format("Player: id_p = %s,name_p =%s, age =%s, id_fc =%s, date_of_birth =%s, club_name=%s", id_p, name_p, age, id_fc, dateOfBirth, footballClub.getName_fc());
    }
}
