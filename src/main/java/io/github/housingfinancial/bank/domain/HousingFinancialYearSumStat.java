package io.github.housingfinancial.bank.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@NoArgsConstructor
@Getter
@ToString
@Entity
public class HousingFinancialYearSumStat {
    @GeneratedValue
    @Id
    private Long id;
    private Integer year;
    private Long totalAmount; //전체 합계
    private Long citibankKoreaAmount;
    private Long etcBankAmount;
    private Long exchangeBankAmount;
    private Long housingCityFundAmount;
    private Long kebHanaBankAmount;
    private Long kookminBankAmount;
    private Long nationalAgriculturalCooperativeFederationBankAmount;
    private Long shinhanBankAmount;
    private Long wooriBankAmount;

    public HousingFinancialYearSumStat(Integer year, Long totalAmount, Long citibankKoreaAmount,
                                       Long etcBankAmount, Long exchangeBankAmount,
                                       Long housingCityFundAmount, Long kebHanaBankAmount,
                                       Long kookminBankAmount,
                                       Long nationalAgriculturalCooperativeFederationBankAmount,
                                       Long shinhanBankAmount, Long wooriBankAmount) {
        this.year = year;
        this.totalAmount = totalAmount;
        this.citibankKoreaAmount = citibankKoreaAmount;
        this.etcBankAmount = etcBankAmount;
        this.exchangeBankAmount = exchangeBankAmount;
        this.housingCityFundAmount = housingCityFundAmount;
        this.kebHanaBankAmount = kebHanaBankAmount;
        this.kookminBankAmount = kookminBankAmount;
        this.nationalAgriculturalCooperativeFederationBankAmount =
                nationalAgriculturalCooperativeFederationBankAmount;
        this.shinhanBankAmount = shinhanBankAmount;
        this.wooriBankAmount = wooriBankAmount;
    }
}
