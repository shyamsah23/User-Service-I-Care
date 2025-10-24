package com.iCare.User_Service.client;

import com.iCare.User_Service.dto.UserDTO;
import com.iCare.User_Service.interceptor.ProfileFeignInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Profile-Service", url = "${PROFILE_SERVICE_URL}", configuration = ProfileFeignInterceptor.class)
public interface ProfileServiceFeignClient {

    @PostMapping("/profile/doctor/add")
    public Long addDoctor(@RequestBody UserDTO userDTO);

    @PostMapping("/profile/patient/add")
    public Long addPatient(@RequestBody UserDTO userDTO);
}
