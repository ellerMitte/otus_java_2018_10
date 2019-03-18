package server;

import DbService.UserDaoHibImpl;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import server.filter.AuthFilter;
import server.servlet.AdminServlet;
import server.servlet.UserServlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author Igor on 18.03.19.
 */
public class Server {
    private final int port;
    private final String PUBLIC_HTML = "/static";
    private final TemplateProcessor templateProcessor;

    Server(int port) throws IOException {
        this.port = port;
        this.templateProcessor = new TemplateProcessor();
    }

    void start() throws Exception {
        resourcesExample();

        ResourceHandler resourceHandler = new ResourceHandler();
        Resource resource = Resource.newClassPathResource(PUBLIC_HTML);
        resourceHandler.setBaseResource(resource);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(new AdminServlet(templateProcessor)), "/admin");
        context.addServlet(new ServletHolder(new UserServlet(templateProcessor, new UserDaoHibImpl())), "/admin/user");
        context.addFilter(new FilterHolder(new AuthFilter()), "/admin/*", null);

        org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(port);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();
        server.join();
    }

    private void resourcesExample() {
        URL url = Main.class.getResource(PUBLIC_HTML + "/index.html"); //local path starts with '/'
        System.out.println(url);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            System.out.println(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
