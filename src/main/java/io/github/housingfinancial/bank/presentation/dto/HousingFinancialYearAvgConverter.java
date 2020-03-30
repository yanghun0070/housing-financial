package io.github.housingfinancial.bank.presentation.dto;

import java.util.List;
import java.util.stream.Collectors;

import io.github.housingfinancial.bank.domain.HousingFinancialYearAvg;
import io.github.housingfinancial.bank.presentation.vo.HousingFinancialExchangeBankMinMaxAvgVo;
import io.github.housingfinancial.bank.presentation.vo.HousingFinancialMinMaxAvgVo;

public class HousingFinancialYearAvgConverter {

    public HousingFinancialExchangeBankMinMaxAvgVo convert(List<HousingFinancialYearAvg> exchangeBankMinAndMaxAvg) {
        HousingFinancialExchangeBankMinMaxAvgVo housingFinancialExchangeBankMinMaxAvg
                = new HousingFinancialExchangeBankMinMaxAvgVo();
        housingFinancialExchangeBankMinMaxAvg.setBank("외환은행");
        housingFinancialExchangeBankMinMaxAvg.setSupportAmount(
                exchangeBankMinAndMaxAvg.stream().map(avg -> new HousingFinancialMinMaxAvgVo(avg.getYear(),
                                                                                     avg.getAmount()))
                                        .collect(Collectors.toList()));
        return housingFinancialExchangeBankMinMaxAvg;
    }
}

