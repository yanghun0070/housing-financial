package io.github.housingfinancial.bank.service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;
import io.github.housingfinancial.bank.domain.HousingFinancialName;
import io.github.housingfinancial.bank.domain.HousingFinancialStat;
import io.github.housingfinancial.bank.domain.HousingFinancialYearAvg;
import io.github.housingfinancial.bank.domain.HousingFinancialYearMaxStat;
import io.github.housingfinancial.bank.domain.HousingFinancialYearSumStat;
import io.github.housingfinancial.bank.repository.HousingFinancialJpaRepository;
import io.github.housingfinancial.bank.repository.HousingFinancialNameJpaRepository;
import io.github.housingfinancial.bank.repository.HousingFinancialStatJpaRepository;
import io.github.housingfinancial.bank.repository.HousingFinancialStatSupport;
import io.github.housingfinancial.bank.repository.HousingFinancialSupport;
import io.github.housingfinancial.bank.repository.dto.HousingFinancialStatConverter;
import io.github.housingfinancial.common.exception.DataNotFoundException;

@Service
public class BankService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private HousingFinancialJpaRepository housingFinancialJpaRepository;
    private HousingFinancialNameJpaRepository housingFinancialNameJpaRepository;
    private HousingFinancialStatJpaRepository housingFinancialStatJpaRepository;
    private HousingFinancialStatSupport housingFinancialStatSupport;
    private HousingFinancialSupport housingFinancialSupport;
    private HousingFinancialStatConverter housingFinancialStatConverter;

    public BankService(HousingFinancialJpaRepository housingFinancialJpaRepository,
                       HousingFinancialNameJpaRepository housingFinancialNameJpaRepository,
                       HousingFinancialStatJpaRepository housingFinancialStatJpaRepository,
                       HousingFinancialSupport housingFinancialSupport,
                       HousingFinancialStatSupport housingFinancialStatSupport
                       ) {
        this.housingFinancialJpaRepository = housingFinancialJpaRepository;
        this.housingFinancialNameJpaRepository = housingFinancialNameJpaRepository;
        this.housingFinancialStatJpaRepository = housingFinancialStatJpaRepository;
        this.housingFinancialSupport = housingFinancialSupport;
        this.housingFinancialStatSupport = housingFinancialStatSupport;
        this.housingFinancialStatConverter = new HousingFinancialStatConverter();
    }

    @Transactional
    public void saveHousingFinancialStatistics() {
        try (CSVReader reader = new CSVReader(
                new FileReader(BankService.class.getResource("/data.csv").getPath()), ',', '"', 0)) {
            List<String[]> allRows = reader.readAll();
            String[] titles = allRows.remove(0);
            // 년,월 제거
            for(int i = 2; i < titles.length; i++) {
                if("".equals(titles[i])){
                    continue;
                }
                housingFinancialNameJpaRepository.save(new HousingFinancialName(i, titles[i].replace("(억원)", "")));
            }
            List<HousingFinancialStat> housingFinancialStat = housingFinancialStatConverter
                    .convertCsvToStat(allRows);
            housingFinancialStatJpaRepository.saveAll(housingFinancialStat);

            housingFinancialJpaRepository.saveAll(housingFinancialStatConverter.convertCsvToHousingFinancial(allRows));
        } catch (IOException e) {
            throw new IllegalArgumentException("Data I/O Error ", e);
        }

    }

    public List<HousingFinancialName> findHousingFinancialNames() {
        List<HousingFinancialName> names =  housingFinancialNameJpaRepository.findAll();
        if(null == names || names.isEmpty()) {
            throw new DataNotFoundException("HousingFinancialName Data Not Found Exception");
        }
        return names;
    }

    public List<HousingFinancialYearSumStat> findHousingFinancialSumAmount() {
        List<HousingFinancialYearSumStat> stat = housingFinancialStatSupport.selectHousingFinancialStatisticsSumAmount();
        if(null == stat || stat.isEmpty()) {
            throw new DataNotFoundException("housingFinancialStat Data Not Found Exception");
        }
        return stat;
    }

    public HousingFinancialYearMaxStat findHousingFinancialMaxAmount() {
        HousingFinancialYearMaxStat stat = housingFinancialSupport.selectHousingFinancialStatisticsMaxAmount();
        if(null == stat) {
            throw new DataNotFoundException("HousingFinancialYearMaxStat Data Not Found Exception");
        }
        return stat;
    }

    public List<HousingFinancialYearAvg> findHousingFinancialExchangeBankMinAndMaxAmount() {
        List<HousingFinancialYearAvg> housingFinancialYearAvgs = housingFinancialSupport.selectHousingFinancialBankAvgAmount(9);
        if(null == housingFinancialYearAvgs || housingFinancialYearAvgs.isEmpty()) {
            throw new DataNotFoundException("HousingFinancialYearAvgs Data Not Found Exception");
        }
        List<HousingFinancialYearAvg> housingFinancialYearMinAndMaxAvgs =
                Arrays.asList(housingFinancialYearAvgs.get(0),
                              housingFinancialYearAvgs.get(housingFinancialYearAvgs.size() - 1));
        return housingFinancialYearMinAndMaxAvgs;
    }
}
