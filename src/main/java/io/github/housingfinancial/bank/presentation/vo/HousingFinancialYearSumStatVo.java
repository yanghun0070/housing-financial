package io.github.housingfinancial.bank.presentation.vo;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class HousingFinancialYearSumStatVo {
    private Integer year;
    private Long totalAmount;
    private Map<String, Long> detailAmount = new LinkedHashMap<>();

}
