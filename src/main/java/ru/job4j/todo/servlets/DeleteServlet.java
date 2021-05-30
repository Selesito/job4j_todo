package ru.job4j.todo.servlets;

import ru.job4j.todo.store.HbmItem;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        req.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(req.getParameter("id"));
        HbmItem.instOf().delete(id);
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }
}
