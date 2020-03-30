package io.github.housingfinancial.bank.presentation.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HousingFinancialYearMaxStatVo {
    @JsonIgnore
    private Long id;
    private Integer year;
    private String bankName;
    private Long maxAmount;
}
