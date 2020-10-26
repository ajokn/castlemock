package com.castlemock.app.config;

import de.qaware.xff.filter.ForwardedHeaderFilter;
import de.qaware.xff.filter.HeaderProcessingStrategy;
import de.qaware.xff.filter.XForwardedPrefixStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
@ConditionalOnProperty(value = "de.qaware.xff.enabled", havingValue = "true")
public class FilterRegistrationConfiguration {

    @Configuration
    @ConfigurationProperties(prefix = "de.qaware.xff")
    static class ForwardedHeaderFilterConfiguration {

        private boolean enabled = true;
        private boolean enableRelativeRedirects = false;
        private HeaderProcessingStrategy headerProcessingStrategy = HeaderProcessingStrategy.EVAL_AND_KEEP;
        private XForwardedPrefixStrategy xForwardedPrefixStrategy = XForwardedPrefixStrategy.PREPEND;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public boolean isEnableRelativeRedirects() {
            return enableRelativeRedirects;
        }

        public void setEnableRelativeRedirects(boolean enableRelativeRedirects) {
            this.enableRelativeRedirects = enableRelativeRedirects;
        }

        public HeaderProcessingStrategy getHeaderProcessingStrategy() {
            return headerProcessingStrategy;
        }

        public void setHeaderProcessingStrategy(HeaderProcessingStrategy headerProcessingStrategy) {
            this.headerProcessingStrategy = headerProcessingStrategy;
        }

        public XForwardedPrefixStrategy getXForwardedPrefixStrategy() {
            return xForwardedPrefixStrategy;
        }

        public void setXForwardedPrefixStrategy(XForwardedPrefixStrategy xForwardedPrefixStrategy) {
            this.xForwardedPrefixStrategy = xForwardedPrefixStrategy;
        }
    }

    @Bean
    public FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter(ForwardedHeaderFilterConfiguration c) {
        FilterRegistrationBean<ForwardedHeaderFilter> frb = new FilterRegistrationBean<>();
        frb.setFilter(new ForwardedHeaderFilter());
        frb.setOrder(Ordered.HIGHEST_PRECEDENCE);
        frb.setEnabled(c.isEnabled());
        frb.addInitParameter(ForwardedHeaderFilter.ENABLE_RELATIVE_REDIRECTS_INIT_PARAM,
                Boolean.toString(c.isEnableRelativeRedirects()));
        frb.addInitParameter(ForwardedHeaderFilter.HEADER_PROCESSING_STRATEGY, c.getHeaderProcessingStrategy().name());
        frb.addInitParameter(ForwardedHeaderFilter.X_FORWARDED_PREFIX_STRATEGY, c.getXForwardedPrefixStrategy().name());
        return frb;
    }
}
