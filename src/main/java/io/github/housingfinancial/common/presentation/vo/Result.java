package io.github.housingfinancial.common.presentation.vo;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Result<E> extends RepresentationModel {

    private E result;

    @JsonCreator
    public Result(@JsonProperty("result") E result) {
        this.result = result;
    }

    public E getResult() {
        return result;
    }
}
