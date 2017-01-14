package com.sap.jnc.marketing.persistence.config.auditor;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "modificatoryAuditorAware")
public class ModificatoryAuditorConfig {

}
