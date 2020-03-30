package io.github.housingfinancial.bank.repository;

import java.util.List;

import io.github.housingfinancial.bank.domain.HousingFinancialYearSumStat;

public interface HousingFinancialStatRepository {

    /**
     * 각 년도별 금융기관의 지원금액 합계
     */
    List<HousingFinancialYearSumStat> selectHousingFinancialStatisticsSumAmount();

}
