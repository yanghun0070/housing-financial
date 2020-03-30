package io.github.housingfinancial.bank.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import io.github.housingfinancial.bank.domain.HousingFinancialName;

public interface HousingFinancialNameJpaRepository extends JpaRepository<HousingFinancialName, Integer> {


}
