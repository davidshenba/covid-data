package org.shenba.analysis.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "T_COVID_DAILY")
@Getter @Setter @ToString
public class CovidDailyData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "REC_ID")
    private UUID recordId;

    @Column(name = "STATE")
    private String state;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "UPDATED_TIME", columnDefinition="timestamp default current_timestamp")
    private Date updatedTime;

    @Column(name = "CUMULATIVE_CASES", columnDefinition = "integer default 0")
    private Integer cumulativeCases;

    @Column(name = "RECOVERED_CASES", columnDefinition = "integer default 0")
    private Integer recovered;

    @Column(name = "DEAD_CASES", columnDefinition = "integer default 0")
    private Integer deaths;

}
