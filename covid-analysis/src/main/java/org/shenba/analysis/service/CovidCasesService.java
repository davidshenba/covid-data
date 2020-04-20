package org.shenba.analysis.service;

import org.shenba.analysis.dto.CovidDailyDTO;
import org.shenba.analysis.repository.CovidDataRepository;
import org.shenba.analysis.vo.CovidCasesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CovidCasesService {

    @Autowired
    private CovidDataRepository covidDataRepository;

    public List<CovidCasesVO> getCovidCases(String... params) {
        List<CovidDailyDTO> dailyDataList;
        int i = 0;
        switch (params.length){
            case 1:
                dailyDataList = covidDataRepository.findCountryDailyCases(params[i]);
                break;
            case 2:
                dailyDataList = covidDataRepository.findStateDailyCases(params[i++], params[i]);
                break;
            case 3:
                dailyDataList = covidDataRepository.findDistrictDailyCases(params[i++], params[i++], params[i]);
                break;
            case 4:
                dailyDataList = covidDataRepository.findPostalCodeDailyCases(params[i++], params[i++], params[i++], params[i]);
                break;
            default:
                dailyDataList = covidDataRepository.findWorldDailyCases();
        }
        return mapCovidDailyToCases(dailyDataList);
    }

    private List<CovidCasesVO> mapCovidDailyToCases(List<CovidDailyDTO> dailyDataList) {
        List<CovidCasesVO> covidCasesList = new ArrayList<CovidCasesVO>();
        CovidDailyDTO dailyData;
        CovidDailyDTO prevCDD;
        CovidCasesVO covidCases;

        if(dailyDataList == null || dailyDataList.isEmpty()) {
            return covidCasesList;
        }

        dailyData = dailyDataList.get(0);
        covidCases = mapCommonFields(dailyData);
        covidCases.setNewConfirmed(dailyData.getCumulativeCases());
        covidCases.setNewRecovered(dailyData.getRecovered());
        covidCases.setNewDeaths(dailyData.getDeaths());
        covidCases.setGrowthFactor(1);
        covidCases.setCfrDaily((100 *covidCases.getNewDeaths()) / (covidCases.getNewDeaths() + covidCases.getNewRecovered()));
        covidCasesList.add(covidCases);

        for(int i = 1; i < dailyDataList.size(); i++) {
            prevCDD = dailyDataList.get(i - 1);
            dailyData = dailyDataList.get(i);
            covidCases = mapCommonFields(dailyData);
            covidCases.setNewConfirmed(dailyData.getCumulativeCases() - prevCDD.getCumulativeCases());
            covidCases.setNewRecovered(dailyData.getRecovered() - prevCDD.getRecovered());
            covidCases.setNewDeaths(dailyData.getDeaths() - prevCDD.getDeaths());
            if(prevCDD.getCumulativeCases() > 0) {
                covidCases.setGrowthFactor(dailyData.getCumulativeCases() / prevCDD.getCumulativeCases());
            } else {
                covidCases.setGrowthFactor(0);
            }
            if(covidCases.getNewDeaths() + covidCases.getNewRecovered() > 0) {
                covidCases.setCfrDaily((100 * covidCases.getNewDeaths()) / (covidCases.getNewDeaths() + covidCases.getNewRecovered()));
            } else {
                covidCases.setCfrDaily(0);
            }
            covidCasesList.add(covidCases);
        }
        return covidCasesList;
    }

    private CovidCasesVO mapCommonFields(CovidDailyDTO dailyData) {
        if(null == dailyData) {
            return null;
        }
        CovidCasesVO covidCases = new CovidCasesVO();
        covidCases.setActiveCases(dailyData.getCumulativeCases() - (dailyData.getDeaths() + dailyData.getRecovered()));
        if((dailyData.getDeaths() + dailyData.getRecovered()) > 0) {
            covidCases.setCfrClosed((100 * dailyData.getDeaths()) / (dailyData.getDeaths() + dailyData.getRecovered()));
        } else {
            covidCases.setCfrClosed(0);
        }
        if(dailyData.getCumulativeCases() > 0) {
            covidCases.setCfrTotal((100 * dailyData.getDeaths()) / dailyData.getCumulativeCases());
        } else {
            covidCases.setCfrTotal(0);
        }
        covidCases.setCountry(dailyData.getCountry());
        covidCases.setState(dailyData.getState());
        covidCases.setDistrict(dailyData.getDistrict());
        covidCases.setPostalCode(dailyData.getPostalCode());
        covidCases.setDate(dailyData.getUpdatedTime());
        covidCases.setTotalCases(dailyData.getCumulativeCases());
        covidCases.setTotalDeaths(dailyData.getDeaths());
        covidCases.setTotalRecovered(dailyData.getRecovered());
        return covidCases;
    }

}
