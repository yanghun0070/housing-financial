package io.github.housingfinancial.bank.repository;


import static io.github.housingfinancial.bank.domain.QHousingFinancialStat.housingFinancialStat;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.github.housingfinancial.bank.domain.HousingFinancialStat;
import io.github.housingfinancial.bank.domain.HousingFinancialYearSumStat;

@Repository
public class HousingFinancialStatSupport extends QuerydslRepositorySupport implements HousingFinancialStatRepository {

    private final JPAQueryFactory queryFactory;

    public HousingFinancialStatSupport(JPAQueryFactory queryFactory) {
        super(HousingFinancialStat.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<HousingFinancialYearSumStat> selectHousingFinancialStatisticsSumAmount() {
        return queryFactory
                .select(Projections.fields(
                       HousingFinancialYearSumStat.class,
                       housingFinancialStat.year.as("year"),
                       housingFinancialStat.citibankKorea.sum().as("citibankKoreaAmount"),
                       housingFinancialStat.etcBank.sum().as("etcBankAmount"),
                       housingFinancialStat.exchangeBank.sum().as("exchangeBankAmount"),
                       housingFinancialStat.housingCityFund.sum().as("housingCityFundAmount"),
                       housingFinancialStat.kebHanaBank.sum().as("kebHanaBankAmount"),
                       housingFinancialStat.kookminBank.sum().as("kookminBankAmount"),
                       housingFinancialStat.nationalAgriculturalCooperativeFederationBank.sum().as("nationalAgriculturalCooperativeFederationBankAmount"),
                       housingFinancialStat.shinhanBank.sum().as("shinhanBankAmount"),
                       housingFinancialStat.wooriBank.sum().as("wooriBankAmount"),
                       housingFinancialStat.citibankKorea.sum()
                       .add(housingFinancialStat.etcBank.sum())
                       .add(housingFinancialStat.exchangeBank.sum())
                       .add(housingFinancialStat.housingCityFund.sum())
                       .add(housingFinancialStat.kebHanaBank.sum())
                       .add(housingFinancialStat.kookminBank.sum())
                       .add(housingFinancialStat.nationalAgriculturalCooperativeFederationBank.sum())
                       .add(housingFinancialStat.shinhanBank.sum())
                       .add(housingFinancialStat.wooriBank.sum())
                       .add(housingFinancialStat.citibankKorea.sum()).as("totalAmount"))
                )
                .from(housingFinancialStat)
                .groupBy(housingFinancialStat.year)
                .orderBy(housingFinancialStat.year.asc())
                .fetch();
    }
}
