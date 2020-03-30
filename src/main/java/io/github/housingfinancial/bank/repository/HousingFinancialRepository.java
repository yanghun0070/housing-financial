package io.github.housingfinancial.bank.repository;

import java.util.List;

import io.github.housingfinancial.bank.domain.HousingFinancialYearAvg;
import io.github.housingfinancial.bank.domain.HousingFinancialYearMaxStat;

public interface HousingFinancialRepository {

    /**
     * 각 년도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관명 조회
     */
    HousingFinancialYearMaxStat selectHousingFinancialStatisticsMaxAmount();

    /**
     * 전체 년도에서 외환은행의 지원금액 평균 금액
     */
    List<HousingFinancialYearAvg> selectHousingFinancialBankAvgAmount(Integer bankId);
}
