package com.timemanager.api.config;

import com.timemanager.core.annotation.EnableRoleChecking;

import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

@Configuration
@ComponentScan
@Import(MongoAutoConfiguration.class)
/**
 * Config for role annotation
 */
public class RoleConfig implements ImportAware  {

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(importMetadata.getAnnotationAttributes(EnableRoleChecking.class.getName()));
		Assert.notNull(annotationAttributes,
				"No " + EnableRoleChecking.class.getSimpleName()
						+ " annotation found on  '" + importMetadata.getClassName() + "'.");

    }

}