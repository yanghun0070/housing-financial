package io.github.housingfinancial.bank.repository;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

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

import io.github.housingfinancial.bank.domain.HousingFinancialStat;
import io.github.housingfinancial.bank.domain.HousingFinancialYearSumStat;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class HousingFinancialStatSupportTest {
    @Autowired
    private EntityManager em;

    private HousingFinancialStatSupport housingFinancialStatSupport;
    private JPAQueryFactory queryFactory;

    @Autowired
    private HousingFinancialStatJpaRepository housingFinancialStatJpaRepository;

    @BeforeEach
    public void init() {
        queryFactory = new JPAQueryFactory(em);
        housingFinancialStatSupport = new HousingFinancialStatSupport(queryFactory);
    }

    @AfterEach
    public void tearDown()  {
        housingFinancialStatJpaRepository.deleteAll();
    }

    @Test
    public void selectHousingFinancialStatisticsSumAmount_YearAscAndTotalAmount_True() {

        housingFinancialStatJpaRepository.saveAll(
                Arrays.asList(new HousingFinancialStat(2019, 1,
                                                       20L, 30L, 40L, 50L, 60L,
                                                       50L, 30L, 40L, 20L),
                              new HousingFinancialStat(2019, 2,
                                                       20L, 30L, 40L, 50L, 60L,
                                                       50L, 30L, 40L, 20L),
                              new HousingFinancialStat(2019, 3,
                                                       20L, 30L, 40L, 50L, 60L,
                                                       50L, 30L, 40L, 20L),
                              new HousingFinancialStat(2020, 1,
                                                       20L, 30L, 40L, 50L, 60L,
                                                       50L, 30L, 40L, 20L),
                              new HousingFinancialStat(2020, 2,
                                                     20L, 30L, 40L, 50L, 60L,
                                                     50L, 30L, 40L, 20L)));
        List<HousingFinancialYearSumStat> stat
                = housingFinancialStatSupport.selectHousingFinancialStatisticsSumAmount();
        assertThat(stat.get(0).getYear(), is(2019));
        assertThat(stat.get(0).getTotalAmount(), is(1200L));
        assertThat(stat.get(1).getYear(), is(2020));
        assertThat(stat.get(1).getTotalAmount(), is(800L));
    }
}
