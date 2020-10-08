package com.potalab.testcase.servlet.filter;

import com.potalab.testcase.servlet.dispatching.Entry;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilterExam02 implements Filter {

  private static final Logger logger = LoggerFactory.getLogger(Entry.class);

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    logger.debug("[FilterExam02] inbound Dispatcher type is >>> {}", request.getDispatcherType());

    chain.doFilter(request, response);

  }

  @Override
  public void destroy() {

  }
}
