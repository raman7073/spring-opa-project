package com.fiftyfive.springboot.dtos;

import lombok.Data;

@Data
public class OPADataResponse {

    private OPAResult result;
    @Data
    public static class OPAResult{
        private Boolean allow;
    }


}
