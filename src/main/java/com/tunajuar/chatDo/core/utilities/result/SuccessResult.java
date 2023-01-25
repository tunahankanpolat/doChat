package com.tunajuar.chatDo.core.utilities.result;

import lombok.Getter;

@Getter
public class SuccessResult extends Result{

    public SuccessResult() {
        super(true);
    }

    public SuccessResult(String message) {
        super(true, message);
    }
}
