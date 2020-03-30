package io.github.housingfinancial.bank.repository;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQueryFactory;

import io.github.housingfinancial.bank.domain.HousingFinancial;
import io.github.housingfinancial.bank.domain.HousingFinancialName;
import io.github.housingfinancial.bank.domain.HousingFinancialYearAvg;
import io.github.housingfinancial.bank.domain.HousingFinancialYearMaxStat;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class HousingFinancialSupportTest {

    @Autowired
    private HousingFinancialJpaRepository housingFinancialJpaRepository;

    @Autowired
    private HousingFinancialNameJpaRepository housingFinancialNameJpaRepository;

    private HousingFinancialSupport housingFinancialSupport;
    private JPAQueryFactory queryFactory;
    @Autowired
    private EntityManager em;

    @BeforeEach
    public void init() {
        queryFactory = new JPAQueryFactory(em);
        housingFinancialSupport = new HousingFinancialSupport(queryFactory);

        housingFinancialNameJpaRepository.saveAll(
                Arrays.asList(
                        new HousingFinancialName(1, "test1"),
                        new HousingFinancialName(2, "test2"),
                        new HousingFinancialName(3, "test3"),
                        new HousingFinancialName(4, "test4")
                )
        );
        housingFinancialJpaRepository.saveAll(
                Arrays.asList(
                        new HousingFinancial(2020, 1, 1, 1000L),
                        new HousingFinancial(2020, 1, 2,2000L),
                        new HousingFinancial(2020, 1, 3,3000L),
                        new HousingFinancial(2020, 2, 1,4000L),
                        new HousingFinancial(2021, 1, 1,5000L)
                )
        );
    }

    @AfterEach
    public void tearDown()  {
        housingFinancialJpaRepository.deleteAll();
    }

    @Test
    public void selectHousingFinancialStatisticsMaxAmount_MaxAmountIs5000_True() {
        HousingFinancialYearMaxStat stat = housingFinancialSupport.selectHousingFinancialStatisticsMaxAmount();
        assertThat(stat.getMaxAmount(), is(5000L));
    }

    @Test
    public void selectHousingFinancialBankAvgAmount_MinAvgIs2500AndMaxAvgtIs5000_True() {
        List<HousingFinancialYearAvg> avg = housingFinancialSupport.selectHousingFinancialBankAvgAmount(1);
        assertThat(avg.get(0).getAmount(), is(2500.0));
        assertThat(avg.get(1).getAmount(), is(5000.0));

    }
}