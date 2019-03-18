package server.servlet;

import server.TemplateProcessor;

import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Igor on 27.02.19.
 */
public class AdminServlet extends HttpServlet {
    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";
    private static final int EXPIRE_INTERVAL = 60;

    private final TemplateProcessor templateProcessor;

    public AdminServlet(TemplateProcessor templateProcessor) {
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();

        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, pageVariables));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        if (authenticateAdmin(name, password)) {
            setSession(request, name);
            setCookie(response, name);
            Map<String, Object> pageVariables = new HashMap<>();
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, pageVariables));
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            PrintWriter out = response.getWriter();
            out.println("Either user name or password is wrong.");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    private void setSession(HttpServletRequest request, String name) {
        HttpSession session = request.getSession();
        session.setAttribute("name", name);
        session.setMaxInactiveInterval(EXPIRE_INTERVAL);
    }

    private void setCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie("name", name);
        cookie.setMaxAge(EXPIRE_INTERVAL);
        response.addCookie(cookie);

    }

    private boolean authenticateAdmin(String name, String password) {
        return "admin".equals(name) && "admin".equals(password);
    }
}
