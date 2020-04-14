package com.potalab.testcase.servlet.chatting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ChatRoom {

    private static ChatRoom INSTANCE;
    public static ChatRoom getInstance() {

        if ( INSTANCE == null) {
            INSTANCE = new ChatRoom();
            INSTANCE.init();
        }


        return INSTANCE;
    }

    private static final Logger logger = LoggerFactory.getLogger(ChatRoom.class);
    private List<AsyncContext> clients = new LinkedList<>();
    private BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();

    private Thread messageHandlerThread;
    private boolean running;

    private ChatRoom() {

    }
    public void init() {

        running = true;
        Runnable handler = new Runnable() {
            @Override
            public void run() {
                logger.info("Started Message Handler.");
                while (running) {
                    try {
                        String message = messageQueue.take();
                        logger.info("Take message [" + message + "] from messageQueue");
                        sendMessageToAllInternal(message);
                    } catch (InterruptedException ex) {
                        break;
                    }
                }
            }
        };

        messageHandlerThread = new Thread(handler);
        messageHandlerThread.start();
    }



    public void enter(final AsyncContext asyncCtx) {
        asyncCtx.addListener(new AsyncListener() {
            @Override
            public void onTimeout(AsyncEvent event) throws IOException {
                logger.info("onTimeout");
                clients.remove(asyncCtx);
            }

            @Override
            public void onError(AsyncEvent event) throws IOException {
                logger.info("onError");
                clients.remove(asyncCtx);
            }

            @Override
            public void onStartAsync(AsyncEvent event) throws IOException {}

            @Override
            public void onComplete(AsyncEvent event) throws IOException {}

        });

        try {
            sendMessageTo(asyncCtx, "Welcome!!! Async Servlet world!!!!");
            clients.add(asyncCtx);
        } catch (IOException e) {
        }

    }


    public void sendMessageToAll(String message) {

        try {
            messageQueue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.info("Add message [" + message + "] to messageQueue");

    }



    private void sendMessageToAllInternal(String message) {

        for (AsyncContext ac : clients) {
            try {
                sendMessageTo(ac, message);
            } catch (IOException e) {
                clients.remove(ac);
            }
        }
        logger.info("Send message [" + message + "] to all clients");
    }



    private void sendMessageTo(AsyncContext ac, String message)
            throws IOException {
        PrintWriter acWriter = ac.getResponse().getWriter();
        acWriter.println(toJSAppendCommand(message));
        acWriter.flush();

    }



    private String toJSAppendCommand(String message) {

        return "<script type='text/javascript'>\n"
                + "window.parent.chatapp.append({ message: \""
                + escape(message) + "\" });\n" + "</script>\n";

    }

    public void close() {

        running = false;
        messageHandlerThread.interrupt();
        logger.info("Stopped Message Handler.");

        for (AsyncContext ac : clients) {
            ac.complete();
        }

        logger.info("Complete All Client AsyncContext.");
    }

    public static String escape(String orig) {
        StringBuilder builder = new StringBuilder((int) (orig.length() * 1.2f));

        for (int i = 0; i < orig.length(); i++) {
            char c = orig.charAt(i);
            switch (c) {
                case '<':
                    builder.append("&lt;");
                    break;
                case '>':
                    builder.append("&gt;");
                    break;
                case '&':
                    builder.append("&amp;");
                    break;
                default:
                    builder.append(c);
            }
        }
        return builder.toString();
    }

}
