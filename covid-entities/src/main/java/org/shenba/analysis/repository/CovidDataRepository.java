package org.shenba.analysis.repository;

import org.shenba.analysis.dto.CovidDailyDTO;
import org.shenba.analysis.entity.CovidDailyData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CovidDataRepository extends CrudRepository<CovidDailyData, UUID> {

    static final String DAILY_SUMMARY_QUERY = "select updated_time as updatedTime, country as country, state as state, district as district, postal_code as postalCode, sum(cumulative_cases) as cumulativeCases, sum(dead_cases) as deaths, sum(recovered_cases) as recovered from t_covid_daily where updated_time > '2020-03-22 00:00:00' ";
    static final String DAILY_SUMMARY_AGGREGATE =  " group by updated_time, country, state, district, postal_code order by updated_time desc";
    static final String DAILY_SUMMARY_COUNTRY =  " and country = ?1 ";
    static final String DAILY_SUMMARY_STATE =  " and state = ?2 ";
    static final String DAILY_SUMMARY_DISTRICT =  " and district = ?3 ";
    static final String DAILY_SUMMARY_P0ST_CODE =  " and postal_code = ?4 ";


    @Query(value = DAILY_SUMMARY_QUERY + DAILY_SUMMARY_AGGREGATE,
            nativeQuery = true)
    List<CovidDailyDTO> findWorldDailyCases();

    @Query(value = DAILY_SUMMARY_QUERY
            + DAILY_SUMMARY_COUNTRY
            + DAILY_SUMMARY_AGGREGATE,
            nativeQuery = true)
    List<CovidDailyDTO> findCountryDailyCases(String country);

    @Query(value = DAILY_SUMMARY_QUERY
            + DAILY_SUMMARY_COUNTRY
            + DAILY_SUMMARY_STATE
            + DAILY_SUMMARY_AGGREGATE,
            nativeQuery = true)
    List<CovidDailyDTO> findStateDailyCases(String country, String state);

    @Query(value = DAILY_SUMMARY_QUERY
            + DAILY_SUMMARY_COUNTRY
            + DAILY_SUMMARY_STATE
            + DAILY_SUMMARY_DISTRICT
            + DAILY_SUMMARY_AGGREGATE,
            nativeQuery = true)
    List<CovidDailyDTO> findDistrictDailyCases(String country, String state, String district);

    @Query(value = DAILY_SUMMARY_QUERY
            + DAILY_SUMMARY_COUNTRY
            + DAILY_SUMMARY_STATE
            + DAILY_SUMMARY_DISTRICT
            + DAILY_SUMMARY_P0ST_CODE
            + DAILY_SUMMARY_AGGREGATE,
            nativeQuery = true)
    List<CovidDailyDTO> findPostalCodeDailyCases(String country, String state, String district, String postalCode);

}
