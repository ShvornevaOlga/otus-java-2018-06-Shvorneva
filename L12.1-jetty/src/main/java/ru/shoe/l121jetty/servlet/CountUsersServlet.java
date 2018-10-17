package ru.shoe.l121jetty.servlet;

import ru.shoe.l111hibernate.base.DBService;
import ru.shoe.l111hibernate.base.datasets.UserDataSet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountUsersServlet extends HttpServlet {

    private static final String COUNT_USERS_VARIABLE_NAME = "countUsers";
    private static final String COUNT_USERS_PAGE_TEMPLATE = "countUsers.html";

    private final TemplateProcessor templateProcessor;
    private final DBService dbService;

    CountUsersServlet(TemplateProcessor templateProcessor, DBService dbService) {
        this.templateProcessor = templateProcessor;
        this.dbService = dbService;
    }

    private String getPage(int countUsers) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put(COUNT_USERS_VARIABLE_NAME, countUsers);
        return templateProcessor.getPage(COUNT_USERS_PAGE_TEMPLATE, pageVariables);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        List<UserDataSet> list = dbService.readAll();
        String page = getPage(list.size());
        response.getWriter().println(page);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
