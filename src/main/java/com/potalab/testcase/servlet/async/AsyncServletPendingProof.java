package com.potalab.testcase.servlet.async;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(value = "/AsyncServletPendingProof", asyncSupported = true)
public class AsyncServletPendingProof extends HttpServlet {

  private static final Logger logger = LoggerFactory.getLogger(AsyncServletPendingProof.class);

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

  }
}
