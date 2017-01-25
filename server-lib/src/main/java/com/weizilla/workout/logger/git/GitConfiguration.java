package com.weizilla.workout.logger.git;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/git.properties")
public class GitConfiguration
{
    private final String commitIdAbbrev;
    private final String buildTime;

    public GitConfiguration(
        @Value("${git.commit.id.abbrev}") String commitIdAbbrev,
        @Value("${git.build.time}") String buildTime)
    {
        this.commitIdAbbrev = commitIdAbbrev;
        this.buildTime = buildTime;
    }

    public String getCommitIdAbbrev() {
        return commitIdAbbrev;
    }

    public String getBuildTime() {
        return buildTime;
    }
}
