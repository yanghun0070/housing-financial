package io.github.housingfinancial.bank.repository.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class HousingFinancialStatCsvResponse {
    private int year; //년도
    private int month; //월
    private String housingCityFund; //주택도시기금1(억원)
    private String kookminBank; //국민은행(억원)
    private String wooriBank; //우리은행(억원)
    private String shinhanBank; //신한은행(억원)
    private String citibankKorea; //한국시티은행(억원)
    private String kebHanaBank; //하나은행(억원)
    private String nationalAgriculturalCooperativeFederationBank; //농협은행/수협은행(억원)
    private String exchangeBank; //외환은행(억원)
    private String etcBank; //기타은행(억원)

    public HousingFinancialStatCsvResponse(int year, int month, String housingCityFund, String kookminBank,
                                           String wooriBank, String shinhanBank, String citibankKorea,
                                           String kebHanaBank,
                                           String nationalAgriculturalCooperativeFederationBank,
                                           String exchangeBank, String etcBank) {
        this.year = year;
        this.month = month;
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
