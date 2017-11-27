package com.wesa.elasticsearch.core.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.wesa.elasticsearch.core.model.RestError;
import com.wesa.elasticsearch.core.shiro.EmptyAuthenticationToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 */
public class RestAuthenticationFilter extends AuthenticatingFilter {

  private final static Logger logger = LoggerFactory.getLogger(RestAuthenticationFilter.class);

  @Override
  public void afterCompletion(ServletRequest request, ServletResponse response, Exception exception) throws Exception {
    super.afterCompletion(request, response, exception);
    Subject subject = ThreadContext.unbindSubject();
    if (subject != null) {
      Session session = subject.getSession(false);
      if (session != null) session.stop();
    }
  }

  protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
    return new EmptyAuthenticationToken();
  }

  protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
    return executeLogin(request, response);
  }

  protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e,
                                   ServletRequest request, ServletResponse response) {
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    httpResponse.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
    try {
      ObjectMapper mapper = new ObjectMapper();
      RestError user = new RestError(Response.Status.UNAUTHORIZED.getStatusCode(), e.getMessage());
      httpResponse.addHeader("Content-Type", "application/json");
      httpResponse.addHeader("Access-Control-Allow-Origin", "*");
      mapper.writeValue(httpResponse.getWriter(), user);
      httpResponse.getWriter().flush();
    } catch (IOException e1) {
      logger.error(e1.getMessage(), e1);
    }
    return false;
  }
}