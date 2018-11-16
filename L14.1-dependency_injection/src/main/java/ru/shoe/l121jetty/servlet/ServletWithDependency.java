package ru.shoe.l121jetty.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.shoe.l111hibernate.base.DBService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class ServletWithDependency extends HttpServlet {
    @Autowired
    TemplateProcessor templateProcessor;
    @Autowired
    @Qualifier("cachedDbService")
    DBService dbService;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

}
