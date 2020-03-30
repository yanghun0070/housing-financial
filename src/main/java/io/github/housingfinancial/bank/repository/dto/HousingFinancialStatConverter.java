package io.github.housingfinancial.bank.repository.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.github.housingfinancial.bank.domain.HousingFinancial;
import io.github.housingfinancial.bank.domain.HousingFinancialStat;

public class HousingFinancialStatConverter {

    private Long removeCsvComma(String csvValue) {
        return Long.valueOf(csvValue.replace(",", ""));
    }

    public List<HousingFinancialStat> convertCsvToStat(final List<String[]> csvRows) {
        List<HousingFinancialStatCsvResponse> housingFinancialStatCsvResponses = csvRows.stream().map(rows ->
                                                                          new HousingFinancialStatCsvResponse(
                                                                                  Integer.valueOf(rows[0]), Integer.valueOf(rows[1]),
                                                                                  rows[2], rows[3], rows[4], rows[5], rows[6], rows[7],
                                                                                  rows[8], rows[9], rows[10]
                                                                          )).collect(Collectors.toList());
        return housingFinancialStatCsvResponses.stream().map(statResponse ->
                                                              new HousingFinancialStat(
                                                                   statResponse.getYear(), statResponse.getMonth(),
                                                                   removeCsvComma(statResponse.getHousingCityFund()),
                                                                   removeCsvComma(statResponse.getKookminBank()),
                                                                   removeCsvComma(statResponse.getWooriBank()),
                                                                   removeCsvComma(statResponse.getShinhanBank()),
                                                                   removeCsvComma(statResponse.getCitibankKorea()),
                                                                   removeCsvComma(statResponse.getKebHanaBank()),
                                                                   removeCsvComma(statResponse.getNationalAgriculturalCooperativeFederationBank()),
                                                                   removeCsvComma(statResponse.getExchangeBank()),
                                                                   removeCsvComma(statResponse.getEtcBank())
                                                           )).collect(Collectors.toList());
    }

    public List<HousingFinancial> convertCsvToHousingFinancial(final List<String[]> csvRows) {
        List<HousingFinancial> housingFinancials = new ArrayList<>();
        csvRows.stream().forEach(rows -> {
            Integer year = Integer.valueOf(rows[0]);
            Integer month = Integer.valueOf(rows[1]);
            for(int i = 2; i < rows.length; i++) {
                if("".equals(rows[i])){
                    continue;
                }
                Long price = Long.valueOf(removeCsvComma(rows[i]));
                housingFinancials.add(new HousingFinancial(year, month, i, price));
            }
        });
        return housingFinancials;
    }

}
