package com.fiftyfive.springboot.feign;

import com.fiftyfive.springboot.dtos.OPADataRequest;
import com.fiftyfive.springboot.dtos.OPADataResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(value = "opaAuthorization", url = "${app.opa.authz.url}")
public interface OpaClient {

    @PostMapping("/opa/authz")
    OPADataResponse authorizedToAccessAPI(@RequestBody OPADataRequest opaDataRequest);

}
