package ru.shoe.l121jetty.servlet;

import ru.shoe.l111hibernate.base.datasets.UserDataSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GetNameByIdServlet extends ServletWithDependency {
    private static final String NAME_VARIABLE_NAME = "name";
    private static final String GET_NAME_PAGE_TEMPLATE = "getName.html";
    private static final String ID_PARAMETR_NAME = "idUser";

    private String getPage(String nameUser) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put(NAME_VARIABLE_NAME, nameUser);
        return templateProcessor.getPage(GET_NAME_PAGE_TEMPLATE, pageVariables);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        String page = getPage("");
        response.getWriter().println(page);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        long userID = Long.parseLong(request.getParameter(ID_PARAMETR_NAME));
        UserDataSet user;
        user = dbService.read(userID);
        String page = getPage(user != null ? user.getName() : "user not found");
        response.getWriter().println(page);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
