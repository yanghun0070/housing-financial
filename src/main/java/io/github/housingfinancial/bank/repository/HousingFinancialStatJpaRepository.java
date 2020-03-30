package io.github.housingfinancial.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.housingfinancial.bank.domain.HousingFinancialStat;

public interface HousingFinancialStatJpaRepository extends JpaRepository<HousingFinancialStat, Long> {
}
