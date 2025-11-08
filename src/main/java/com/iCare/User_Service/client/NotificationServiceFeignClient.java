package com.iCare.User_Service.client;

import com.iCare.User_Service.dto.EmailDTO;
import com.iCare.User_Service.interceptor.GlobalProfileFeignInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Notification-Service", url = "${NOTIFICATION_SERVICE_URL}", configuration = GlobalProfileFeignInterceptor.class)
public interface NotificationServiceFeignClient {

    @PostMapping("/api/mail/sendMail")
    public void sendMail(@RequestBody EmailDTO emailDTO);
}
