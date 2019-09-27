package com.timemanager.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.timemanager.api.config.RoleConfig;

import org.springframework.context.annotation.Import;

/**
 * Interface for Role config
 */
@Inherited
@Import(RoleConfig.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface EnableRoleChecking {

}