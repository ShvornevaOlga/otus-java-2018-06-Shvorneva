package ru.shoe.l121jetty.servlet;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.shoe.l111hibernate.base.DBService;
import ru.shoe.l111hibernate.base.datasets.AdressDataSet;
import ru.shoe.l111hibernate.base.datasets.PhoneDataSet;
import ru.shoe.l111hibernate.base.datasets.UserDataSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddUserServlet extends HttpServlet {

    private static final String ID_USER_VARIABLE_NAME = "idAddedUser";
    private static final String ADD_USER_PAGE_TEMPLATE = "addUser.html";
    private static final String NAME_PARAMETR_NAME = "name";
    private static final String AGE_PARAMETR_NAME = "age";
    private static final String PHONE_PARAMETR_NAME = "phoneNumber";
    private static final String ADRESS_PARAMETR_NAME = "adress";
    private static final String USER_CACHE_NAME = "userCache";
    @Autowired
    @Qualifier("myService")
    private DBService dbService;

    @Autowired
    private TemplateProcessor templateProcessor;

    @Autowired
    private FactoryBean<CacheManager> cacheManagerFactory;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    private String getPage(long idUser) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put(ID_USER_VARIABLE_NAME, idUser == -1 ? "" : idUser);
        return templateProcessor.getPage(ADD_USER_PAGE_TEMPLATE, pageVariables);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        String page = getPage(-1);
        response.getWriter().println(page);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        String nameUser = request.getParameter(NAME_PARAMETR_NAME);
        String ageUser = request.getParameter(AGE_PARAMETR_NAME);
        String phoneNumber = request.getParameter(PHONE_PARAMETR_NAME);
        String adress = request.getParameter(ADRESS_PARAMETR_NAME);

        PhoneDataSet phoneDataSet = new PhoneDataSet(phoneNumber);
        AdressDataSet adressDataSet = new AdressDataSet(adress);
        UserDataSet userDataSet = new UserDataSet(nameUser, Integer.parseInt(ageUser), adressDataSet);
        userDataSet.addPhone(phoneDataSet);
        dbService.save(userDataSet);
        long addedUserId = userDataSet.getId();
        if (cacheManagerFactory != null) {
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
            if (cache != null) {
                cache.put(new Element(userDataSet.getId(), userDataSet));
            }
        }
        String page = getPage(addedUserId);
        response.getWriter().println(page);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
