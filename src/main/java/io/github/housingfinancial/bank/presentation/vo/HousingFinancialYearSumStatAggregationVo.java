package io.github.housingfinancial.bank.presentation.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HousingFinancialYearSumStatAggregationVo {
    private String name;
    private List<HousingFinancialYearSumStatVo> results = new ArrayList<>();

}
