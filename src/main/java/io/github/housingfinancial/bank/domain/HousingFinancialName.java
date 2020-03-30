package io.github.housingfinancial.bank.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import io.github.housingfinancial.bank.config.EnumMapperType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity
public class HousingFinancialName {
    @Id
    @Column(name = "bank_id")
    private Integer id;
    @Column
    private String name;

    public HousingFinancialName(Integer id) {
        this.id = id;
    }

    public HousingFinancialName(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
