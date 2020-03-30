package io.github.housingfinancial.bank.config;

public enum HousingFinancialNameType implements EnumMapperType{
    HOUSING_FINANCIAL("주택도시기금"),
    KOOKMIN_BANK("국민은행"),
    WOORI_BANK("우리은행"),
    SHINHAN_BANK("신한은행"),
    CITIBANK_KOREA("한국시티은행"),
    KEB_HANA_BANK("하나은행"),
    NATIONAL_AGRICULTURAL_COOPERATIVE_FEDERAION_BANK("농협은행/수협은행"),
    EXCHANGE_BANK("외환은행"),
    ETC_BANK("기타은행");

    private String name;
    HousingFinancialNameType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
