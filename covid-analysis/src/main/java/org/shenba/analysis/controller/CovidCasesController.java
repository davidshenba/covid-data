package org.shenba.analysis.controller;

import org.shenba.analysis.service.CovidCasesService;
import org.shenba.analysis.vo.CovidCasesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/covid-cases")
public class CovidCasesController {

    @Autowired
    private CovidCasesService covidCasesService;

    @GetMapping(path = {"/", "/{country}", "/{country}/{state}", "/{country}/{state}/{district}", "/{country}/{state}/{district}/{postalCode}"})
    public List<CovidCasesVO> getDailyData(@PathVariable(name="country", required = false) String country,
                                           @PathVariable(name="state", required = false) String state,
                                           @PathVariable(name="district", required = false) String district,
                                           @PathVariable(name="postalCode", required = false) String postalCode) {

        if(null == country) {
           return covidCasesService.getCovidCases();
        } else if(null == state) {
           return covidCasesService.getCovidCases(country);
        } else if(null == district) {
            return covidCasesService.getCovidCases(country, state);
        } else if(null == postalCode) {
            return covidCasesService.getCovidCases(country, state, district);
        }
        return covidCasesService.getCovidCases(country, state, district, postalCode);
    }

}
