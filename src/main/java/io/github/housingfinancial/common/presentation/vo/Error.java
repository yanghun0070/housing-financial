package io.github.housingfinancial.common.presentation.vo;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Error<E> extends RepresentationModel {

    private GlobalMessage error;

    @JsonCreator
    public Error(@JsonProperty("error") GlobalMessage error) {
        this.error = error;
    }

    public GlobalMessage getError() {
        return error;
    }
}
