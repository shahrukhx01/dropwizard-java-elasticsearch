package com.wesa.elasticsearch.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.thetransactioncompany.cors.CORSFilter;
import com.wesa.elasticsearch.core.configuration.Configuration;
import com.wesa.elasticsearch.core.filter.RestAuthenticationFilter;
import com.wesa.elasticsearch.core.guice.GuiceModule;
import com.wesa.elasticsearch.core.healthcheck.SimpleHealthCheck;
import com.wesa.elasticsearch.core.provider.AuthenticationExceptionMapper;
import com.wesa.elasticsearch.core.provider.AuthorizationExceptionMapper;
import com.wesa.elasticsearch.core.provider.BadRequestExceptionMapper;
import com.wesa.elasticsearch.core.provider.NotFoundExceptionMapper;
import com.wesa.elasticsearch.core.shiro.EmptyAuthenticatingRealm;
import com.wesa.elasticsearch.core.shiro.RestAuthorizingRealm;
import com.wesa.elasticsearch.core.shiro.RestUsernamePasswordAuthenticatingRealm;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.servlets.tasks.Task;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.guice.aop.ShiroAopModule;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.web.filter.session.NoSessionCreationFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.knowm.dropwizard.sundial.SundialBundle;
import org.knowm.dropwizard.sundial.SundialConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@SuppressWarnings("rawtypes")
public class Application<T extends Configuration, M extends GuiceModule> extends io.dropwizard.Application<T> {
  @SuppressWarnings("unused")
private final static Logger log = LoggerFactory.getLogger(Application.class);

  public static String serviceName = "default";
  private final Class<? extends Resource>[] resources;
  private final M guiceModule;
  private final SimpleHealthCheck healthCheck;
  private Managed[] managedList;
  private Task[] taskList;
  public static String configName;

  public static Injector guiceInjector;

  protected Application(String serviceName, M guiceModule, SimpleHealthCheck healthCheck,
                        @SuppressWarnings("unchecked") Class<? extends Resource>... resources) {
    super();
    this.resources = resources;
    this.guiceModule = guiceModule;
    Application.serviceName = serviceName;
    this.healthCheck = healthCheck == null ? new SimpleHealthCheck() : healthCheck;
  }

  protected void setManagedList(Managed... managedList) {
    this.managedList = managedList;
  }

//  public void setTasks(Task... taskList) {
//    this.taskList = taskList;
//  }

  @Override
  public void run(String... arguments) throws Exception {
    if (arguments.length > 1)
      configName = arguments[1].toLowerCase();
    super.run(arguments);
  }

  @SuppressWarnings("unchecked")
public void run(T configuration, Environment environment) throws Exception {
    guiceModule.setEnvironmentData(configuration, environment, serviceName);
    guiceInjector = Guice.createInjector(guiceModule, new ShiroAopModule());
    
    if (managedList != null)
      for (Managed managed : managedList)
        environment.lifecycle().manage(managed);

    if (taskList != null)
      for (Task task : taskList)
        environment.admin().addTask(task);

//    environment.admin().addTask(new StartJobTask());
//    environment.admin().addTask(new UnlockSundialSchedulerTask());

    //security stuff
    DefaultSecurityManager securityManager = guiceInjector.getInstance(DefaultSecurityManager.class);
    securityManager.setAuthenticator(guiceInjector.getInstance(Authenticator.class));
    ((DefaultSessionStorageEvaluator) ((DefaultSubjectDAO) (securityManager).getSubjectDAO()).getSessionStorageEvaluator()).setSessionStorageEnabled(false);
    ((DefaultSessionManager) (securityManager).getSessionManager()).setSessionValidationSchedulerEnabled(false);
    securityManager.setRememberMeManager(null);
    List<Realm> realms = new ArrayList<>();

    EmptyAuthenticatingRealm emptyAuthenticatingRealm = guiceInjector.getInstance(EmptyAuthenticatingRealm.class);
    emptyAuthenticatingRealm.setName("emptyAuthenticatingRealm");
    emptyAuthenticatingRealm.setCredentialsMatcher(new AllowAllCredentialsMatcher());
    emptyAuthenticatingRealm.setCachingEnabled(false);
    emptyAuthenticatingRealm.setAuthenticationCachingEnabled(false);
    realms.add(emptyAuthenticatingRealm);

    RestUsernamePasswordAuthenticatingRealm
            restUsernamePasswordAuthenticatingRealm = guiceInjector.getInstance(RestUsernamePasswordAuthenticatingRealm.class);
    restUsernamePasswordAuthenticatingRealm.setName("restAuthenticatingRealm");
    restUsernamePasswordAuthenticatingRealm.setCredentialsMatcher(new AllowAllCredentialsMatcher());
    restUsernamePasswordAuthenticatingRealm.setCachingEnabled(false);
    restUsernamePasswordAuthenticatingRealm.setAuthenticationCachingEnabled(false);
    realms.add(restUsernamePasswordAuthenticatingRealm);

    RestAuthorizingRealm restAuthorizingRealm = guiceInjector.getInstance(RestAuthorizingRealm.class);
    restAuthorizingRealm.setName("restAuthorizingRealm");
    restAuthorizingRealm.setCredentialsMatcher(new AllowAllCredentialsMatcher());
    restAuthorizingRealm.setCachingEnabled(false);
    restAuthorizingRealm.setAuthenticationCachingEnabled(false);
    restAuthorizingRealm.setAuthorizationCachingEnabled(false);
    realms.add(restAuthorizingRealm);
    securityManager.setRealms(realms);

    SecurityUtils.setSecurityManager(securityManager);

    NoSessionCreationFilter noSessionCreationFilter = new NoSessionCreationFilter();
    noSessionCreationFilter.processPathConfig("/**", null);
    noSessionCreationFilter.setName("NoSessionCreationFilter");

    environment.servlets().addFilter("NoSessionCreationFilter", noSessionCreationFilter).addMappingForUrlPatterns
            (EnumSet.allOf(DispatcherType.class), true, "/*");

    RestAuthenticationFilter baseAuthenticationFilter = guiceInjector.getInstance(RestAuthenticationFilter.class);
    baseAuthenticationFilter.setName("RestAuthenticationFilter");
    baseAuthenticationFilter.processPathConfig("/**", null);
    environment.servlets().addFilter("RestAuthenticationFilter", baseAuthenticationFilter).addMappingForUrlPatterns
            (EnumSet.allOf(DispatcherType.class), true, "/*");


    final FilterRegistration.Dynamic crossOriginFilter =
            environment.servlets().addFilter("crossOriginRequests", CORSFilter.class);
    crossOriginFilter.setInitParameter("cors.supportedMethods", "GET, POST, HEAD, OPTIONS, PUT, DELETE");
   // crossOriginFilter.setInitParameter(CrossOriginFilter.CHAIN_PREFLIGHT_PARAM, Boolean.FALSE.toString());
    crossOriginFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
   // crossOriginFilter.setInitParameter("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, X-Codingpedia, Authorization");
   // crossOriginFilter.setInitParameter(CrossOriginFilter.CHAIN_PREFLIGHT_PARAM, Boolean.FALSE.toString());
    
    

    //register resources
    if (resources != null)
      for (Class<? extends Resource> resource : resources) {
//        Resource res = guiceInjector.getInstance(resource);
        environment.jersey().register((Resource) guiceInjector.getInstance(resource));
      }

  
    environment.jersey().register(guiceInjector.getInstance(AuthenticationExceptionMapper.class));
    environment.jersey().register(guiceInjector.getInstance(AuthorizationExceptionMapper.class));
    environment.jersey().register(guiceInjector.getInstance(BadRequestExceptionMapper.class));
    environment.jersey().register(guiceInjector.getInstance(NotFoundExceptionMapper.class));

    environment.jersey().register(MultiPartFeature.class);

    environment.healthChecks().register("healthcheck", healthCheck);

   
  }

  @Override
  public void initialize(Bootstrap<T> bootstrap) {
    super.initialize(bootstrap);

    /* swagger generated only in "test" environment */
    if (configName != null && configName.contains("test") && !configName.contains("local-test")) {
      bootstrap.addBundle(new SwaggerBundle<Configuration>() {
        @Override
        protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(Configuration configuration) {
          return configuration.swaggerBundleConfiguration;
        }
      });
    }

    bootstrap.addBundle(new SundialBundle<Configuration>() {
      @Override
      public SundialConfiguration getSundialConfiguration(Configuration configuration) {
        return configuration.getSundial();
      }
    });
  }

}
