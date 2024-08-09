package dev.harshaan.CornellHelpBot;

import net.dean.jraw.RedditClient;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.OAuthHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class RedditConfig {

    @Value("${USERNAME}")
    private String USERNAME;

    @Value("${PASSWORD}")
    private String PASSWORD;

    @Value("${CLIENT_ID}")
    private String CLIENT_ID;

    @Value("${CLIENT_SECRET}")
    private String CLIENT_SECRET;

    @Bean
    public RedditClient redditClient() {
        UserAgent userAgent = new UserAgent("bot", "dev.harshaan.CornellHelpBot", "v0.1", USERNAME);
        Credentials credentials = Credentials.script(USERNAME, PASSWORD, CLIENT_ID, CLIENT_SECRET);

        return OAuthHelper.automatic(new OkHttpNetworkAdapter(userAgent), credentials);
    }
}