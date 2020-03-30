package io.github.housingfinancial.bank.presentation.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HousingFinancialMinMaxAvgVo {
    private Integer year;
    private double amount;

    public HousingFinancialMinMaxAvgVo(Integer year, double amount) {
        this.year = year;
        this.amount = amount;
    }
}
