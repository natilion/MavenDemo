package org.example.servlet;

import org.example.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AllRequestsServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVatiables = createPageVariablesMap(req);
        pageVatiables.put("message", "");
//        resp.getWriter().println(PageGenerator.instance().getPage("page.html", pageVatiables));
        resp.getWriter().println(req.getParameter("key"));

        resp.setContentType("text/html;charset=UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVatiables = createPageVariablesMap(req);

        String message = req.getParameter("message");

        resp.setContentType("text/html;charset=UTF-8");

        if (message == null || message.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            resp.setStatus(HttpServletResponse.SC_OK);
        }
        pageVatiables.put("message", message == null ? "" : message);
        resp.getWriter().println(PageGenerator.instance().getPage("page.html", pageVatiables));
    }

    private Map<String, Object> createPageVariablesMap(HttpServletRequest req) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("method", req.getMethod());
        pageVariables.put("URL", req.getRequestURL().toString());
        pageVariables.put("pathInfo", req.getPathInfo());
        pageVariables.put("sessionId", req.getSession().getId());
        pageVariables.put("parameters", req.getParameterMap().toString());
        return pageVariables;
    }
}
