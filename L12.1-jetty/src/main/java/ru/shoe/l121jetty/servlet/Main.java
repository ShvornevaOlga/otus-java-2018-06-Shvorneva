package ru.shoe.l121jetty.servlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.shoe.l111hibernate.base.DBService;
import ru.shoe.l111hibernate.dbService.hibernate.DBServiceHibernateImpl;
import ru.shoe.l111hibernate.dbService.myDbService.DBServiceImpl;

public class Main {
    private final static int PORT = 8090;
    private final static String PUBLIC_HTML = "public_html";

    public static void main(String[] args) throws Exception {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        TemplateProcessor templateProcessor = new TemplateProcessor();

        try (DBService dbService = new DBServiceHibernateImpl()) {
            context.addServlet(new ServletHolder(new AddUserServlet(templateProcessor, dbService)), "/addUser");
            context.addServlet(new ServletHolder(new GetNameByIdServlet(templateProcessor, dbService)), "/getName");
            context.addServlet(new ServletHolder(new CountUsersServlet(templateProcessor, dbService)), "/countUsers");

            Server server = new Server(PORT);
            server.setHandler(new HandlerList(resourceHandler, context));

            server.start();
            server.join();
        }
    }
}
