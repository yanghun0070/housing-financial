package io.github.housingfinancial.bank.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
@Entity
public class HousingFinancial {
    @GeneratedValue
    @Id
    private Long id;
    private Integer year;
    private Integer month;
    @OneToOne
    @JoinColumn(name = "bank_id")
    private HousingFinancialName bank;
    private Long amount;

    public HousingFinancial(Integer year, Integer month, Integer bankId, Long amount) {
        this.year = year;
        this.month = month;
        this.bank = new HousingFinancialName(bankId);
        this.amount = amount;
    }
}
