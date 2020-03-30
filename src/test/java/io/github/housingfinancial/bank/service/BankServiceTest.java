package io.github.housingfinancial.bank.service;

import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.housingfinancial.bank.domain.HousingFinancialYearAvg;
import io.github.housingfinancial.bank.repository.HousingFinancialSupport;

@ExtendWith(MockitoExtension.class)
public class BankServiceTest {

    @Mock
    HousingFinancialSupport housingFinancialSupport;

    @InjectMocks
    private BankService bankService;

    @Test
    public void findHousingFinancialExchangeBankMinAndMaxAmount_Equals_True() throws Exception {
        List<HousingFinancialYearAvg> housingFinancialYearAvgs = Arrays.asList(
                new HousingFinancialYearAvg(9, 2000, 20.0),
                new HousingFinancialYearAvg(9, 2001, 33.3),
                new HousingFinancialYearAvg(9, 2002, 34.4),
                new HousingFinancialYearAvg(9, 2003, 88.7),
                new HousingFinancialYearAvg(9, 2004, 100.0));
        given(housingFinancialSupport.selectHousingFinancialBankAvgAmount(9))
                .willReturn(housingFinancialYearAvgs);
        final List<HousingFinancialYearAvg> results = bankService.findHousingFinancialExchangeBankMinAndMaxAmount();
        assertEquals(results.get(0).getYear(), Integer.valueOf(2000));
        assertEquals(results.get(0).getAmount(), Double.valueOf(20.0));
        assertEquals(results.get(1).getYear(), Integer.valueOf(2004));
        assertEquals(results.get(1).getAmount(), Double.valueOf(100.0));
    }
}
