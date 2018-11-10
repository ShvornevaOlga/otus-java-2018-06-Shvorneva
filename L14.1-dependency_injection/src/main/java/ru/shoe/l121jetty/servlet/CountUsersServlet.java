package ru.shoe.l121jetty.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.shoe.l111hibernate.base.DBService;
import ru.shoe.l111hibernate.base.datasets.UserDataSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
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
    @Autowired
    private TemplateProcessor templateProcessor;
    @Autowired
    @Qualifier("hibernate")
    private DBService dbService;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
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
