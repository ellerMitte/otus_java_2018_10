package servlet;

import model.User;
import model.UserDao;
import server.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Igor on 27.02.19.
 */
public class UserServlet extends HttpServlet {
    private static final String USER_PAGE_TEMPLATE = "user.html";
    private final TemplateProcessor templateProcessor;
    private final UserDao userDao;

    public UserServlet(UserDao userDao) throws IOException {
        this.userDao = userDao;
        this.templateProcessor = new TemplateProcessor();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("users", userDao.getUsers());
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(templateProcessor.getPage(USER_PAGE_TEMPLATE, pageVariables));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        userDao.save(new User(req.getParameter("name"), req.getParameter("password")));
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("users", userDao.getUsers());
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(templateProcessor.getPage(USER_PAGE_TEMPLATE, pageVariables));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        userDao.deleteUserByName(req.getParameter("name"));
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("users", userDao.getUsers());
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(templateProcessor.getPage(USER_PAGE_TEMPLATE, pageVariables));
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
