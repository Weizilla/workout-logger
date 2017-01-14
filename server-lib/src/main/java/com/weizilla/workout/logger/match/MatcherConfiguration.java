package com.weizilla.workout.logger.match;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix="matcher")
public class MatcherConfiguration {
    /**
     * Matches the Garmin type (key) to manual entry type (value)
     */
    private final Map<String, String> typeMapping = new HashMap<>();

    public Map<String, String> getTypeMapping() {
        return typeMapping;
    }
}
