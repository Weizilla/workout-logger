package com.weizilla.workout.logger.git;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = GitConfiguration.class)
public class GitConfigurationTest
{
    @Autowired
    private GitConfiguration configuration;

    @Test
    public void configurationIsCreated() throws Exception
    {
        assertThat(configuration).isNotNull();
    }

    @Test
    public void valuesAreSet() throws Exception
    {
        assertThat(configuration.getBuildTime()).isEqualTo("2017-01-22T17:06:46-0600");
        assertThat(configuration.getCommitIdAbbrev()).isEqualTo("GIT COMMIT ID");
    }
}
