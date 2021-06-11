package ru.job4j.todo.servlets;

import org.apache.commons.fileupload.util.LimitedInputStream;
import org.json.JSONArray;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.HbmItem;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ItemsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        String desc = req.getParameter("desc");
        int idUser = Integer.parseInt(req.getParameter("idUser"));
        String[] cIds = req.getParameterValues("cIds");
        User user = HbmItem.instOf().findByIdUser(idUser);
        if (id != null) {
            HbmItem.instOf().replace(Integer.parseInt(id));
        } else {
            HbmItem.instOf().add(Item.of(desc, false, user), cIds);
        }
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();
        JSONArray json  = new JSONArray(HbmItem.instOf().findAll());
        writer.println(json);
        writer.flush();
    }
}
