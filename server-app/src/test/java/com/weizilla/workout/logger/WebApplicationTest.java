package com.weizilla.workout.logger;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WebApplicationTest
{
    private WebApplication webApplication;

    @Before
    public void setUp() throws Exception
    {
        webApplication = new WebApplication();
    }

    @Test
    public void addsCorsMapping() throws Exception
    {
        CorsRegistry registry = mock(CorsRegistry.class);
        CorsRegistration registration = mock(CorsRegistration.class);
        when(registry.addMapping(anyString())).thenReturn(registration);

        webApplication.corsConfigurer().addCorsMappings(registry);

        verify(registry).addMapping("/api/**");
    }
}