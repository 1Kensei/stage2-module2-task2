package com.example.servlet;

import com.example.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.example.Users.getInstance;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        } else {
            resp.sendRedirect(req.getContextPath() + "/user/hello.jsp");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        boolean validLogin = login != null && getInstance().getUsers().contains(login);
        boolean validPassword = password != null && !password.trim().isEmpty();

        if( validLogin && validPassword) {
            HttpSession session = req.getSession(true);
            session.setAttribute("user", login);
            resp.sendRedirect(req.getContextPath() + "/user/hello.jsp");
        } else {
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        }
    }
}
