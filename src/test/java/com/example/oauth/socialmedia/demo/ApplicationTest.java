package com.example.oauth.socialmedia.demo;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTest
{

    @Autowired DataSource dataSource;
    @Test
    public void test()
    {
        assertThat(dataSource).isNotNull();
    }

}
