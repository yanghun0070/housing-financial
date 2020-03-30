package io.github.housingfinancial.common.presentation.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(Include.NON_EMPTY)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GlobalMessage {
    private int status;
    private String message;
    private String detail;

    public GlobalMessage(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public GlobalMessage(int status, String message, String detail) {
        this.status = status;
        this.message = message;
        this.detail = detail;
    }
}
