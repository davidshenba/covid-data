package org.shenba.analysis.repository;

import org.shenba.analysis.entity.CovidDailyData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CovidDataRepository extends CrudRepository<CovidDailyData, UUID> {
}
