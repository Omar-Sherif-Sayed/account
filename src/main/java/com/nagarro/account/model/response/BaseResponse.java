package com.nagarro.account.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nagarro.account.util.DateUtil;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
@JsonInclude(JsonInclude.Include.ALWAYS)
public class BaseResponse<T> {

    public BaseResponse(T response) {
        this.response = response;
    }

    private final T response;

    private final boolean status = true;

    @JsonFormat(timezone = DateUtil.AFRICA_CAIRO_ZONE)
    private final Date currentDate = DateUtil.convertDateToCairoZone(new Date());

    private final String uuId = UUID.randomUUID().toString();

}
