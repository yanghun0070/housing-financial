package io.github.housingfinancial.bank.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
@Entity
public class HousingFinancialYearAvg {
    @GeneratedValue
    @Id
    private Long id;
    private Integer bankId;
    private Integer year;
    private Double amount;

    public HousingFinancialYearAvg(Integer bankId, Integer year, Double amount) {
        this.bankId = bankId;
        this.year = year;
        this.amount = amount;
    }
}
