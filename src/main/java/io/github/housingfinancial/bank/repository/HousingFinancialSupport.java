package io.github.housingfinancial.bank.repository;

import static io.github.housingfinancial.bank.domain.QHousingFinancial.housingFinancial;
import static io.github.housingfinancial.bank.domain.QHousingFinancialName.housingFinancialName;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.github.housingfinancial.bank.domain.HousingFinancial;
import io.github.housingfinancial.bank.domain.HousingFinancialYearAvg;
import io.github.housingfinancial.bank.domain.HousingFinancialYearMaxStat;

@Repository
public class HousingFinancialSupport extends QuerydslRepositorySupport implements HousingFinancialRepository {

    private final JPAQueryFactory queryFactory;

    public HousingFinancialSupport(JPAQueryFactory queryFactory) {
        super(HousingFinancial.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public HousingFinancialYearMaxStat selectHousingFinancialStatisticsMaxAmount() {
        List<HousingFinancialYearMaxStat> housingFinancialYearMaxStat = queryFactory
                .select(Projections.fields(
                        HousingFinancialYearMaxStat.class,
                        ExpressionUtils.as(
                                JPAExpressions.select(housingFinancialName.name)
                                              .from(housingFinancialName)
                                              .where(housingFinancialName.id.eq(housingFinancial.bank.id))
                        , "bankName"),
                        housingFinancial.year.as("year"),
                        housingFinancial.amount.max().as("maxAmount")
                        )
                )
                .from(housingFinancial)
                .groupBy(housingFinancial.year).groupBy(housingFinancial.bank.id)
                .orderBy(housingFinancial.amount.max().desc())
                .fetch();
        return (housingFinancialYearMaxStat.size() > 0) ? housingFinancialYearMaxStat.get(0) : null;
    }

    @Override
    public List<HousingFinancialYearAvg> selectHousingFinancialBankAvgAmount(Integer bankId) {
        List<HousingFinancialYearAvg> housingFinancialYearAvg = queryFactory.select(Projections.fields(
                HousingFinancialYearAvg.class,
                housingFinancial.bank.id.as("bankId"),
                housingFinancial.year.as("year"),
                housingFinancial.amount.avg().round().as("amount")
            )
        )
        .from(housingFinancial)
        .groupBy(housingFinancial.year)
        .where(housingFinancial.bank.id.eq(bankId))
        .orderBy(housingFinancial.amount.avg().asc())
        .fetch();
        return housingFinancialYearAvg;
    }

}
