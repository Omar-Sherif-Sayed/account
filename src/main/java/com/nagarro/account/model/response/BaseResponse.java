package com.nagarro.account.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nagarro.account.util.DateUtil;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@JsonInclude(JsonInclude.Include.ALWAYS)
public class BaseResponse<T> {

    public BaseResponse() {
        this.response = null;
    }

    public BaseResponse(T response) {
        this.response = response;
    }

    private final T response;

    private static boolean status = true;

    @JsonFormat(timezone = DateUtil.AFRICA_CAIRO_ZONE)
    private final Date currentDate = DateUtil.convertDateToCairoZone(new Date());

    private final String uuId = UUID.randomUUID().toString();

}
