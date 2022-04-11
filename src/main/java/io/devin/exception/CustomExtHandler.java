package io.devin.exception;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常处理类
 */
@RestControllerAdvice
public class CustomExtHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Map exceptionHandler(HttpServletRequest request, Exception exception) throws Exception {
        Map<String, Object> result = new HashMap<>(3);
        result.put("code", 500);
        result.put("msg", exception.getMessage());
        result.put("url", request.getRequestURL().toString());
        return result;
    }

}




