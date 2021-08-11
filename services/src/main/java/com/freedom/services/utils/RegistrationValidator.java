package com.freedom.services.utils;

import com.freedom.services.dommain.User;
import com.freedom.services.dommain.dto.CaptchaResponseDto;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static com.freedom.services.utils.ControllersUtil.getErrors;

public class RegistrationValidator {

    public static Map<String,String> collectionOfErrors(CaptchaResponseDto captchaResponse, String confirmPassword, @Valid User user, BindingResult bindingResult){
        Map<String,String> errors = new HashMap<>();
        if (!captchaResponse.isSuccess()) {
            errors.put("captchaError", "Підтвердіть що ви не робот");
        }
        boolean isConfirmEmpty = StringUtils.isEmpty(confirmPassword);
        if (isConfirmEmpty) {
            errors.put("password2Error", "Поле підтвердження паролю маєбути заповненим!");
        }
        if (user.getPassword() != null
                && !user.getPassword().equals(confirmPassword)) {
            errors.put("passwordError", "Паролі не співпадають");
            errors.put("password2Error", "Паролі не співпадають");
        }
        if (bindingResult.hasErrors() || !captchaResponse.isSuccess()) {
            Map<String, String> bindingResultErrors = getErrors(bindingResult);
            errors.forEach(errors::put);
        }
        return errors;
    }

}
