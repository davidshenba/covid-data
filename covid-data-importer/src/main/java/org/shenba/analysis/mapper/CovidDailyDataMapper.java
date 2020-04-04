package org.shenba.analysis.mapper;

import org.shenba.analysis.entity.CovidDailyData;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class CovidDailyDataMapper implements FieldSetMapper<CovidDailyData> {

    @Override
    public CovidDailyData mapFieldSet(FieldSet fieldSet) throws BindException {
        CovidDailyData covidDailyData = new CovidDailyData();
        covidDailyData.setCumulativeCases(fieldSet.readInt(3));
        covidDailyData.setCountry(fieldSet.readString(1));
        covidDailyData.setDeaths(fieldSet.readInt(4));
        covidDailyData.setRecovered(fieldSet.readInt(5));
        covidDailyData.setState(fieldSet.readString(0));
        covidDailyData.setUpdatedTime(fieldSet.readDate(2, "yyyy-MM-dd"));
        return covidDailyData;
    }
}
