package com.company.model;

public class FootballClub {
    private Integer idFc;
    private String nameFc;
    private Integer yearBirth;

    public Integer getIdFc() {
        return idFc;
    }

    public void setIdFc(Integer idFc) {
        this.idFc = idFc;
    }

    public String getNameFc() {
        return nameFc;
    }

    public void setNameFc(String nameFc) {
        this.nameFc = nameFc;
    }

    public Integer getYearBirth() {
        return yearBirth;
    }

    public void setYearBirth(Integer yearBirth) {
        this.yearBirth = yearBirth;
    }

    @Override
    public String toString() {
        return String.format("Footbal club: idFc  = %s,name_fc=%s, year_birth =%s", idFc , nameFc, yearBirth);
    }
}
