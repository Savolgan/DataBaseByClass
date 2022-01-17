package com.company.model;

public class FootballClub {
    private Integer id_fc;
    private String name_fc;
    private Integer year_birth;

    public Integer getId_fc() {
        return id_fc;
    }

    public void setId_fc(Integer id_fc) {
        this.id_fc = id_fc;
    }

    public String getName_fc() {
        return name_fc;
    }

    public void setName_fc(String name_fc) {
        this.name_fc = name_fc;
    }

    public Integer getYear_birth() {
        return year_birth;
    }

    public void setYear_birth(Integer year_birth) {
        this.year_birth = year_birth;
    }

    @Override
    public String toString() {
        return String.format("Footbal club: id_fc = %s,name_fc=%s, year_birth =%s", id_fc, name_fc, year_birth);
    }
}
