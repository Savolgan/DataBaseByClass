package com.company.model;

import com.company.annotation.Column;
import com.company.annotation.MaxLength;

import java.time.LocalDate;

public class Player {
    @Column("id_p")
    private Integer idP;

    @Column("name_p")
    @MaxLength(maxlength = 30)
    private String nameP;

    @Column("age")
    private Integer age;

    @Column("date_of_birth")
    private LocalDate dateOfBirth;

    @Column("id_fc")
    private Integer idFootballClub;

    @Column("footballClub")
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

        return String.format("Player: id_p = %s,name_p =%s, age =%s, date_of_birth=%s,  club_name=%s, year_birth_of_club=%s",
                idP, nameP, age, dateOfBirth,
                footballClub.getNameFc() != null ? footballClub.getNameFc() : null,
                footballClub.getYearBirth() != null ? footballClub.getYearBirth() : null);

    }

}
