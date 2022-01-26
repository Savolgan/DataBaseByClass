package com.company.model;

import java.sql.SQLException;
import java.time.LocalDate;

public class Player {
    private Integer idP;
    private String nameP;
    private Integer age;
    private Integer idFootballClub;
    private LocalDate dateOfBirth;
    private FootballClub footballClub;

    public Integer getIdP() {
        return idP;
    }

    public void setIdP(Integer idP) {
        this.idP = idP;
    }

    public String getNameP() {
        return nameP;
    }

    public void setNameP(String nameP) {
        this.nameP = nameP;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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

    public Integer getIdFootballClub() {
        return idFootballClub;
    }

    public void setIdFootballClub(Integer idFootballClub) {
        this.idFootballClub = idFootballClub;
    }

    @Override
    public String toString() {
        if (footballClub != null) {
            return String.format("Player: id_p = %s,name_p =%s, age =%s,  date_of_birth =%s, club_name=%s", idP, nameP, age, dateOfBirth, footballClub.getNameFc());
        } else {
            return "FootballClus is not exist!";
        }
    }
}
