package io.github.housingfinancial.bank.presentation.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.housingfinancial.bank.presentation.dto.HousingFinancialYearAvgConverter;
import io.github.housingfinancial.bank.presentation.dto.HousingFinancialYearSumStatConverter;
import io.github.housingfinancial.bank.presentation.vo.HousingFinancialExchangeBankMinMaxAvgVo;
import io.github.housingfinancial.bank.presentation.vo.HousingFinancialNameVo;
import io.github.housingfinancial.bank.presentation.vo.HousingFinancialYearMaxStatVo;
import io.github.housingfinancial.bank.presentation.vo.HousingFinancialYearSumStatAggregationVo;
import io.github.housingfinancial.bank.service.BankService;
import io.github.housingfinancial.common.presentation.vo.GlobalMessage;
import io.github.housingfinancial.common.presentation.vo.Result;

@RestController
@RequestMapping("/bank")
public class BankController {

    private final BankService bankService;
    private final MessageSourceAccessor messageSourceAccessor;
    private final HousingFinancialYearSumStatConverter housingFinancialYearSumStatConverter;
    private final HousingFinancialYearAvgConverter housingFinancialYearAvgConverter;

    public BankController(BankService bankService,
                          MessageSourceAccessor messageSourceAccessor) {
        this.bankService = bankService;
        this.messageSourceAccessor = messageSourceAccessor;
        this.housingFinancialYearSumStatConverter = new HousingFinancialYearSumStatConverter();
        this.housingFinancialYearAvgConverter = new HousingFinancialYearAvgConverter();
    }

    /**
     * 주택금융 공급현황 분석 데이터를 DB 에 저장
     * @throws Exception
     */
    @PostMapping("housingfinancial/statistics/save")
    public Result<GlobalMessage> saveHousingFinancialStatistics() {
        bankService.saveHousingFinancialStatistics();
        GlobalMessage globalMessage = new GlobalMessage(HttpStatus.OK.value(),
                                                        messageSourceAccessor.getMessage(String.valueOf(HttpStatus.OK.value())));
        Result<GlobalMessage> result = new Result<>(globalMessage);
        result.add(
                linkTo(methodOn(BankController.class).saveHousingFinancialStatistics()).withSelfRel());
        return result;
    }

    /**
     * 주택금융 공급 금융기관(은행) 목록
     */
    @GetMapping("housingfinancial/list")
    public Result<List<HousingFinancialNameVo>> getHousingFinancialNames() {

        List<HousingFinancialNameVo> housingFinancialNames =
                new ModelMapper().map(bankService.findHousingFinancialNames(),
                new TypeToken<List<HousingFinancialNameVo>>(){}.getType());

        Result<List<HousingFinancialNameVo>> result = new Result<>(housingFinancialNames);
        result.add(linkTo(methodOn(BankController.class).getHousingFinancialNames()).withSelfRel());
        return result;
    }

    /**
     * 각 년도별 금융기관의 지원금액 합계
     */
    @GetMapping("housingfinancial/year/sumamount")
    public Result<HousingFinancialYearSumStatAggregationVo> getHousingFinancialSumAmount() {
        HousingFinancialYearSumStatAggregationVo housingFinancialYearSumStatAggregation
                = housingFinancialYearSumStatConverter.convert(bankService.findHousingFinancialSumAmount());
        Result<HousingFinancialYearSumStatAggregationVo> result = new Result<>(housingFinancialYearSumStatAggregation);
        result.add(linkTo(methodOn(BankController.class).getHousingFinancialSumAmount()).withSelfRel());
        return result;
    }

    /**
     * 각 년도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관명을 출력
     */
    @GetMapping("housingfinancial/year/maxamount")
    public Result<HousingFinancialYearMaxStatVo> getHousingFinancialMaxAmount() {
        Result<HousingFinancialYearMaxStatVo> result = new Result<>(
                new ModelMapper().map(bankService.findHousingFinancialMaxAmount(),
                                      HousingFinancialYearMaxStatVo.class));
        result.add(linkTo(methodOn(BankController.class).getHousingFinancialMaxAmount()).withSelfRel());
        return result;
    }

    /**
     * 전체 년도에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액
     */
    @GetMapping("housingfinancial/year/exchangebank/avg/minmaxamount")
    public Result<HousingFinancialExchangeBankMinMaxAvgVo> getHousingFinancialExchangeBankMinAndMaxAmount() {
        Result<HousingFinancialExchangeBankMinMaxAvgVo> result = new Result<>(
                housingFinancialYearAvgConverter.convert( bankService.findHousingFinancialExchangeBankMinAndMaxAmount()));
        result.add(linkTo(methodOn(BankController.class).getHousingFinancialExchangeBankMinAndMaxAmount()).withSelfRel());
        return result;
    }
}
