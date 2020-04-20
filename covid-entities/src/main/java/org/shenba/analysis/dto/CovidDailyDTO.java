package org.shenba.analysis.dto;

import java.util.Date;

public interface CovidDailyDTO {

    String getPostalCode();

    String getDistrict();

    String getState();

    String getCountry();

    Date getUpdatedTime();

    Integer getCumulativeCases();

    Integer getRecovered();

    Integer getDeaths();

}
