package it.zancanela.peoplehub.utils;

import it.zancanela.peoplehub.exceptions.BadRequestBodyException;
import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

public class BindingResultUtils {
    private BindingResultUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static synchronized void verifyBindingResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestBodyException(
                    bindingResult.getFieldErrors().stream()
                            .map(e ->
                                    "field:[" + e.getField() +
                                            "] message:[" + e.getDefaultMessage()+"]")
                            .collect(Collectors.joining(" || ")));
        }
    }
}
