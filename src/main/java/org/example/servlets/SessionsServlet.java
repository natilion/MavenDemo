package org.example.servlets;

import com.google.gson.Gson;
import org.example.accounts.AccountService;
import org.example.accounts.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SessionsServlet extends HttpServlet {
    private final AccountService accountService;

    public SessionsServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        UserProfile userProfile = accountService.getUserBySessionId(sessionId);
        resp.setContentType("application/json;charset=utf-8");
        if (userProfile == null){
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else {
            Gson gson = new Gson();
            String json = gson.toJson(userProfile);
            resp.getWriter().write(json);
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        resp.setContentType("application/json;charset=utf-8");
        if (login == null || password == null){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        UserProfile userProfile = accountService.getUserByLogin(login);
        if (userProfile == null || !userProfile.getPassword().equals(password)){
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        accountService.addSession(req.getSession().getId(), userProfile);
        Gson gson = new Gson();
        String json = gson.toJson(userProfile);
        resp.getWriter().write(json);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        UserProfile userProfile = accountService.getUserBySessionId(sessionId);
        resp.setContentType("application/json;charset=utf-8");
        if (userProfile == null){
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else {
            accountService.deleteSession(sessionId);
            resp.getWriter().write("GoodBye");
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
