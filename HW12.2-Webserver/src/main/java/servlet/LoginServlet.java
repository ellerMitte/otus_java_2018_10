package servlet;

import model.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {

    UserService userService;

    public LoginServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) {
        if (userService.authenticate(request.getParameter("name"), request.getParameter("password"))) {
            request.getSession();
            response.setStatus(HttpServletResponse.SC_OK);
        }else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

}
