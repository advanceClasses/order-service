package com.microservices.OrderService.external.decoder;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.OrderService.excepetion.CustomException;
import com.microservices.OrderService.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR;
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        if (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR){
            ObjectMapper objectMapper = new ObjectMapper();
            ErrorMessage errorMessage =
                    objectMapper.readValue(httpResponse.getBody(), ErrorMessage.class);
            throw new CustomException(
                    errorMessage.getMessage(),
                    errorMessage.getError(),
                    httpResponse.getRawStatusCode()
                    );
        }
    }
}

