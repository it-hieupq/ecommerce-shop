package com.capstone.client.order.domain.service;

import com.capstone.client.order.domain.common.Stringify;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WebClientService {
    private WebClient webClient = WebClient.create();

    public boolean userExist(Integer userId) {
        return existById(Stringify.USER_SERVICE_URL +"/admin/users/exists/"+userId);
    }

    public boolean productExist(Integer productId) {
        return existById(Stringify.PRODUCT_SERVICE_URL +"/admin/products/exists/"+productId);
    }

    public boolean existById(String endpoint) {
        ResultObject obj = webClient.get()
                .uri(endpoint)
                .retrieve()
                .bodyToMono(ResultObject.class)
                .block();
        return obj.result == true;
    }
}

class ResultObject {
    @JsonProperty("result")
    public boolean result;
}