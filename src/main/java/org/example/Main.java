package org.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.example.servlet.AllRequestsServlet;

public class Main {
    public static void main(String[] args) throws Exception {
        AllRequestsServlet allRequestsServlet = new AllRequestsServlet();
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(allRequestsServlet), "/mirror");
        Server server = new Server(8080);
        server.setHandler(servletContextHandler);
        server.start();
        java.util.logging.Logger.getGlobal().info("Server started");
        server.join();
    }
}