package io.github.housingfinancial.bank.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class HousingFinancialYearMaxStat {
    @GeneratedValue
    @Id
    private Long id;
    private Integer year;
    private String bankName;
    private Long maxAmount;
}
