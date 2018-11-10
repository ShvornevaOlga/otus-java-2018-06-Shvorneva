package ru.shoe.l121jetty.servlet;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.FactoryBean;
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
import java.util.Map;

public class GetNameByIdServlet extends HttpServlet {
    private static final String NAME_VARIABLE_NAME = "name";
    private static final String GET_NAME_PAGE_TEMPLATE = "getName.html";
    private static final String ID_PARAMETR_NAME = "idUser";
    private static final String USER_CACHE_NAME = "userCache";
    @Autowired
    private TemplateProcessor templateProcessor;
    @Autowired
    @Qualifier("hibernate")
    private DBService dbService;
    @Autowired
    private FactoryBean<CacheManager> cacheManagerFactory;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

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

        CacheManager cacheManager = null;
        try {
            cacheManager = cacheManagerFactory.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cache cache = null;
        if (cacheManager != null) {
            cache = cacheManager.getCache(USER_CACHE_NAME);
        }
        if (cache != null && cache.isKeyInCache(userID)) {
            user = (UserDataSet) cache.get(userID).getObjectValue();
        } else user = dbService.read(userID);

        if (cache != null && user != null) {
            cache.put(new Element(user.getId(), user));
        }

        String page = getPage(user != null ? user.getName() : "user not found");
        response.getWriter().println(page);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
