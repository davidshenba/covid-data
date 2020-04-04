package org.shenba.analysis.config;

import org.shenba.analysis.entity.CovidDailyData;
import org.shenba.analysis.mapper.CovidDailyDataMapper;
import org.shenba.analysis.writer.CovidDataWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
public class CovidDataImporterConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private CovidDataWriter covidDataWriter;

    @Value("${csv.files.location}")
    private Resource[] csvFiles;

    @Bean
    public Job csvProcessingJob() {
        return jobBuilderFactory
                .get("csvProcessingJob")
                .incrementer(new RunIdIncrementer())
                .start(csvProcessingStep())
                .build();
    }

    @Bean
    public Step csvProcessingStep() {
        return stepBuilderFactory
                .get("csvProcessingStep")
                .<CovidDailyData, CovidDailyData>chunk(100)
                .reader(multiResourceItemReader())
                .writer(covidDataWriter)
                .build();
    }

    @Bean
    public MultiResourceItemReader<CovidDailyData> multiResourceItemReader() {
        MultiResourceItemReader<CovidDailyData> resourceItemReader = new MultiResourceItemReader<CovidDailyData>();
        resourceItemReader.setResources(csvFiles);
        resourceItemReader.setDelegate(csvReader());
        return resourceItemReader;
    }

    @Bean
    public FlatFileItemReader<CovidDailyData> csvReader() {
        FlatFileItemReader<CovidDailyData> csvReader = new FlatFileItemReader<CovidDailyData>();
        csvReader.setLinesToSkip(1);
        csvReader.setLineMapper(getCDDLineMapper());
        return csvReader;
    }

    private LineMapper<CovidDailyData> getCDDLineMapper() {
        DefaultLineMapper<CovidDailyData> cddLineMapper = new DefaultLineMapper<CovidDailyData>();
        cddLineMapper.setLineTokenizer(getCDDLineTokenizer());
        cddLineMapper.setFieldSetMapper(new CovidDailyDataMapper());
        return cddLineMapper;
    }

    private LineTokenizer getCDDLineTokenizer() {
        DelimitedLineTokenizer cddLineTokenizer = new DelimitedLineTokenizer();
        cddLineTokenizer.setDelimiter(",");
        cddLineTokenizer.setIncludedFields(2, 3, 4, 7, 8, 9);
        return cddLineTokenizer;
    }

}
