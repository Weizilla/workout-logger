package com.weizilla.workout.logger.test;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.net.URL;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEmptyString.isEmptyString;

public class TestUtils
{
    public static String readFile(String filename) throws Exception
    {
        URL url = Resources.getResource(filename);
        String contents = Resources.toString(url, Charsets.UTF_8);
        assertThat(contents, not(isEmptyString()));
        return contents;
    }
}
