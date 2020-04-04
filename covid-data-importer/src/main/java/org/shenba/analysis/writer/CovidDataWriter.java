package org.shenba.analysis.writer;

import org.shenba.analysis.entity.CovidDailyData;
import org.shenba.analysis.repository.CovidDataRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class CovidDataWriter implements ItemWriter<CovidDailyData> {

    @Autowired
    private CovidDataRepository covidDataRepository;

    @Override
    @Transactional
    public void write(List<? extends CovidDailyData> covidDailies) throws Exception {
        covidDataRepository.saveAll(covidDailies);
    }
}
