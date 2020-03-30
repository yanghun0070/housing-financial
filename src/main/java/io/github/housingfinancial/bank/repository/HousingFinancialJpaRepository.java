package io.github.housingfinancial.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.housingfinancial.bank.domain.HousingFinancial;

public interface HousingFinancialJpaRepository extends JpaRepository<HousingFinancial, Long> {
}
