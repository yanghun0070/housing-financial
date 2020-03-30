package io.github.housingfinancial.bank.domain;

import java.time.LocalDate;

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
public class HousingFinancialStat {
    @GeneratedValue
    @Id
    private Long id;
    private LocalDate statDate; //통계 날짜(년, 월)
    private Integer year; //년
    private Integer month; //월
    private Long housingCityFund; //주택도시기금1(억원)
    private Long kookminBank; //국민은행(억원)
    private Long wooriBank; //우리은행(억원)
    private Long shinhanBank; //신한은행(억원)
    private Long citibankKorea; //한국시티은행(억원)
    private Long kebHanaBank; //하나은행(억원)
    private Long nationalAgriculturalCooperativeFederationBank; //농협은행/수협은행(억원)
    private Long exchangeBank; //외환은행(억원)
    private Long etcBank; //기타은행(억원)

    public HousingFinancialStat(Integer year, Integer month, Long housingCityFund, Long kookminBank,
                                Long wooriBank, Long shinhanBank, Long citibankKorea,
                                Long kebHanaBank, Long nationalAgriculturalCooperativeFederationBank,
                                Long exchangeBank, Long etcBank) {
        this.year = year;
        this.month = month;
        this.statDate = LocalDate.of(year, month, 1);
        this.housingCityFund = housingCityFund;
        this.kookminBank = kookminBank;
        this.wooriBank = wooriBank;
        this.shinhanBank = shinhanBank;
        this.citibankKorea = citibankKorea;
        this.kebHanaBank = kebHanaBank;
        this.nationalAgriculturalCooperativeFederationBank = nationalAgriculturalCooperativeFederationBank;
        this.exchangeBank = exchangeBank;
        this.etcBank = etcBank;
    }

}
