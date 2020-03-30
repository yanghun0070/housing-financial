package io.github.housingfinancial.bank.presentation.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class HousingFinancialExchangeBankMinMaxAvgVo {
    private String bank;
    private List<HousingFinancialMinMaxAvgVo> supportAmount = new ArrayList<>();
}
