package org.shenba.analysis.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter @Setter
public class CovidCasesVO implements Serializable {

    private Date date;
    private String state;
    private String country;
    private String district;
    private String postalCode;
    private int newRecovered;
    private int newDeaths;
    private int newConfirmed;
    private int activeCases;
    private double growthFactor;
    private int totalCases;
    private int totalDeaths;
    private int totalRecovered;
    private double cfrDaily;
    private double cfrClosed;
    private double cfrTotal;

}
