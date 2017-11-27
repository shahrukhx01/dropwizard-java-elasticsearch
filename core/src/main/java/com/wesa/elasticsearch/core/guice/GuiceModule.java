package com.wesa.elasticsearch.core.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.wesa.elasticsearch.core.configuration.Configuration;
import com.wesa.elasticsearch.core.shiro.RestAuthenticationStrategy;
import io.dropwizard.setup.Environment;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;

public class GuiceModule<T extends Configuration> extends AbstractModule {
	protected T configuration;
	protected String serviceName;
	protected Environment environment;

	public void setEnvironmentData(T configuration, Environment environment, String serviceName) {
		this.configuration = configuration;
		this.environment = environment;
		this.serviceName = serviceName;
	}

	@Override
	public void configure() {
		// Service Name
		bind(String.class).annotatedWith(Names.named("hostService")).toInstance(serviceName);
		bind(Configuration.class).toInstance(configuration);

		ModularRealmAuthenticator modularRealmAuthenticator = new ModularRealmAuthenticator();
		RestAuthenticationStrategy restAuthenticationStrategy = new RestAuthenticationStrategy();
		modularRealmAuthenticator.setAuthenticationStrategy(restAuthenticationStrategy);
		bind(Authenticator.class).toInstance(modularRealmAuthenticator);
		bind(AuthenticationStrategy.class).toInstance(restAuthenticationStrategy);

	}

	@Provides
	@Singleton
	public JestClient providesJestClient() {
		JestClientFactory factory = new JestClientFactory();
		factory.setHttpClientConfig(new HttpClientConfig.Builder(configuration.getElasticsearch()).multiThreaded(true).build());
		return factory.getObject();
	}

}
