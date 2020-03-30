package io.github.housingfinancial.bank.presentation.dto;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.github.housingfinancial.bank.domain.HousingFinancialYearSumStat;
import io.github.housingfinancial.bank.presentation.vo.HousingFinancialYearSumStatAggregationVo;
import io.github.housingfinancial.bank.presentation.vo.HousingFinancialYearSumStatVo;

public class HousingFinancialYearSumStatConverter {

    public HousingFinancialYearSumStatAggregationVo convert(final List<HousingFinancialYearSumStat> housingFinancialYearSumStat) {

        HousingFinancialYearSumStatAggregationVo housingFinancialYearSumStatAggregationVo
                = new HousingFinancialYearSumStatAggregationVo();
        housingFinancialYearSumStatAggregationVo.setName("주택금융 공급현황");
        List<HousingFinancialYearSumStatVo> housingFinancialYearSumStatVo = housingFinancialYearSumStat.stream().map(housingFinancialYearSum -> {
                                                     HousingFinancialYearSumStatVo housingFinancialYearSumVo = new HousingFinancialYearSumStatVo();
                                                     housingFinancialYearSumVo.setYear(housingFinancialYearSum.getYear());
                                                     housingFinancialYearSumVo.setTotalAmount(housingFinancialYearSum.getTotalAmount());

                                                     Map<String, Long> detailAmount = new LinkedHashMap<>();
                                                     detailAmount.put("주택도시기금", housingFinancialYearSum.getHousingCityFundAmount());
                                                     detailAmount.put("국민은행", housingFinancialYearSum.getKebHanaBankAmount());
                                                     detailAmount.put("우리은행", housingFinancialYearSum.getWooriBankAmount());
                                                     detailAmount.put("신한은행", housingFinancialYearSum.getShinhanBankAmount());
                                                     detailAmount.put("한국시티은행", housingFinancialYearSum.getCitibankKoreaAmount());
                                                     detailAmount.put("하나은행", housingFinancialYearSum.getKebHanaBankAmount());
                                                     detailAmount.put("농협은행/수협은행", housingFinancialYearSum.getNationalAgriculturalCooperativeFederationBankAmount());
                                                     detailAmount.put("외환은행", housingFinancialYearSum.getExchangeBankAmount());
                                                     detailAmount.put("기타은행", housingFinancialYearSum.getEtcBankAmount());
                                                     housingFinancialYearSumVo.setDetailAmount(detailAmount);
                                                     return housingFinancialYearSumVo;
        }).collect(Collectors.toList());
        housingFinancialYearSumStatAggregationVo.setResults(housingFinancialYearSumStatVo);
        return housingFinancialYearSumStatAggregationVo;
    }
}
