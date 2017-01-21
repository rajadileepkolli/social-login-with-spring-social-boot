package com.example.oauth.socialmedia.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.github.connect.GitHubConnectionFactory;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.live.connect.LiveConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;

import com.example.oauth.socialmedia.demo.dao.UsersDao;
import com.example.oauth.socialmedia.demo.services.AccountConnectionSignUpService;

/**
 * @author rajakolli
 *
 */
@Configuration
@EnableSocial
public class SocialConfig implements SocialConfigurer
{

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UsersDao usersDao;

    @Override
    public void addConnectionFactories(
            ConnectionFactoryConfigurer connectionFactoryConfigurer,
            Environment environment)
    {
        connectionFactoryConfigurer.addConnectionFactory(new GoogleConnectionFactory(
                environment.getProperty("spring.social.google.appId"),
                environment.getProperty("spring.social.google.appSecret")));
        connectionFactoryConfigurer.addConnectionFactory(new GitHubConnectionFactory(
                environment.getProperty("spring.social.github.appId"),
                environment.getProperty("spring.social.github.appSecret")));
        connectionFactoryConfigurer.addConnectionFactory(new LiveConnectionFactory(
                environment.getProperty("spring.social.live.appId"),
                environment.getProperty("spring.social.live.appSecret")));
    }

    @Override
    public UserIdSource getUserIdSource()
    {
        return new AuthenticationNameUserIdSource();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(
            ConnectionFactoryLocator connectionFactoryLocator)
    {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(
                dataSource, connectionFactoryLocator, Encryptors.noOpText());
        repository.setConnectionSignUp(new AccountConnectionSignUpService(usersDao));
        return repository;
    }
}
