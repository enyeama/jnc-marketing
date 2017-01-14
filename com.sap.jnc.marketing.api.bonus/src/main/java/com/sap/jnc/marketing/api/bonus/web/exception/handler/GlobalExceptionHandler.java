package com.sap.jnc.marketing.api.bonus.web.exception.handler;

import com.sap.jnc.marketing.service.exception.CommonBusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by dy on 16/7/5.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    ReloadableResourceBundleMessageSource messageSource;

    @ExceptionHandler({CommonBusinessException.class})
    protected ResponseEntity<Object> handleBussinessException(CommonBusinessException businessException, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        CommonBusinessException.ErrorMessage errorMessage = businessException.getErrorMessage();
        String errorCode = errorMessage.getErrorCode();
        if (StringUtils.isNotBlank(errorCode)) {
            errorMessage.setErrorMessage(messageSource.getMessage(errorCode, new Object[]{}, null));
        }
        return handleExceptionInternal(businessException, businessException.getErrorMessage(), headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }


}
