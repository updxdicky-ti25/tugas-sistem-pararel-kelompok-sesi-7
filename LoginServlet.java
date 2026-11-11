package rmi.web;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final String USER = "admin", PASS = "admin123";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("logout".equals(req.getParameter("aksi"))) {
            req.getSession().invalidate();
            resp.sendRedirect(req.getContextPath() + "/mahasiswa");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (USER.equals(req.getParameter("username")) && PASS.equals(req.getParameter("password"))) {
            req.getSession().setAttribute("admin", Boolean.TRUE);
            resp.sendRedirect(req.getContextPath() + "/mahasiswa");
        } else {
            req.setAttribute("error", "Username atau password salah.");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
}
