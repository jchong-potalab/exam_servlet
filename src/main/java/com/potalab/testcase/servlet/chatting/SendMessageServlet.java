package com.potalab.testcase.servlet.chatting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/sendMessage")
public class SendMessageServlet extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(SendMessageServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        logger.info("Receive SEND request");

        res.setContentType("text/plain");
        res.setHeader("Cache-Control", "private");
        res.setHeader("Pragma", "no-cache");
        req.setCharacterEncoding("UTF-8");

        String msg = req.getParameter("message");

        if (msg.equals("@exit")) {
            ChatRoom.getInstance().close();
        } else {
            ChatRoom.getInstance().sendMessageToAll(msg);
        }

        res.getWriter().print("OK");
    }

}