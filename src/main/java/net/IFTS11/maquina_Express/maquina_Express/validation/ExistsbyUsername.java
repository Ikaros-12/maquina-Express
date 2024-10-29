package net.IFTS11.maquina_Express.maquina_Express.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;


@Constraint( validatedBy = ExistbyUsernameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsbyUsername {

    String message() default "ya existe en la base de datos!, escoja otro username";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
