package org.example.servlets;

import com.google.gson.Gson;
import org.example.accounts.AccountService;
import org.example.accounts.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class UsersServlet extends HttpServlet {
    private final AccountService accountService;
    public UsersServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String login = req.getParameter("login");
//        UserProfile userProfile = accountService.getUserByLogin(login);
        resp.setContentType("application/json;charset=utf-8");
//        if (userProfile == null){
//            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        }else {
            Map<String, UserProfile> loginToProfile = accountService.getListOfProfiles();
            Gson gson = new Gson();
            String json = gson.toJson(loginToProfile);
            resp.getWriter().write(json);
            resp.setStatus(HttpServletResponse.SC_OK);
//        }
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

        accountService.addNewUser(userProfile);
        Gson gson = new Gson();
        String json = gson.toJson(userProfile);
        resp.getWriter().write(json);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        resp.setContentType("application/json;charset=utf-8");

        if (login == null || password == null){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        UserProfile userProfile = accountService.getUserByLogin(login);
        if (userProfile == null){
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        accountService.editUser(login, userProfile);
        Gson gson = new Gson();
        String json = gson.toJson(userProfile);
        resp.getWriter().write(json);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login= req.getParameter("login");
        UserProfile userProfile = accountService.getUserByLogin(login);
        resp.setContentType("application/json;charset=utf-8");
        if (userProfile == null){
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else {
            accountService.deleteUserProfile(login);
            resp.getWriter().write("GoodBye");
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
