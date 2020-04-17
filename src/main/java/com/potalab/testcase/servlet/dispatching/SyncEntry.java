package com.potalab.testcase.servlet.dispatching;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/dispatching/sync_entry")
public class SyncEntry  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String Operation = req.getParameter("opCode");

        switch(Operation.toUpperCase()) {
            case "EXCEPTION_CASE1":
                doSyncToAsync(req, resp);
                break;
            default:
                doSendError(req, resp);
        }
    }

    private void doSyncToAsync(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = req.getRequestDispatcher("/dispatching/async2");
        dispatcher.forward(req, resp);
    }

    private void doSendError(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendError(404, "error message for test!!!!");
    }
}
