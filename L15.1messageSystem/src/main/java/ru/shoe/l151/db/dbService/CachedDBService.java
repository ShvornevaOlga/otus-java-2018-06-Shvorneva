package ru.shoe.l151.db.dbService;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.shoe.l151.db.base.DBService;
import ru.shoe.l151.db.base.datasets.UserDataSet;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Service("cachedDbService")
public class CachedDBService implements DBService {
    private static final String USER_CACHE_NAME = "userCache";
    @Autowired
    @Qualifier("hibernate")
    private DBService dbService;

    @Autowired
    private FactoryBean<CacheManager> cacheManagerFactory;
    private Cache cache;

    @Override
    public String getMetaData() {
        return dbService.getMetaData();
    }

    @PostConstruct
    public void init() {
        if (cacheManagerFactory != null) {
            CacheManager cacheManager = null;
            try {
                cacheManager = cacheManagerFactory.getObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (cacheManager != null) {
                cache = cacheManager.getCache(USER_CACHE_NAME);
            }
        }
    }

    @Override
    public void save(UserDataSet dataSet) {
        dbService.save(dataSet);
        if (cache != null) {
            cache.put(new Element(dataSet.getId(), dataSet));
        }
    }

    @Override
    public UserDataSet read(long id) {
        UserDataSet user;
        if (cache != null && cache.isKeyInCache(id)) {
            user = (UserDataSet) cache.get(id).getObjectValue();
        } else {
            user = dbService.read(id);
            if (cache != null && user != null) {
                cache.put(new Element(user.getId(), user));
            }
        }
        return user;
    }

    @Override
    public UserDataSet readByName(String name) {
        Map<Object, Element> allCashedUsers = cache.getAll(cache.getKeys());
        UserDataSet user;
        for (Element element : allCashedUsers.values()) {
            user = (UserDataSet) element.getObjectValue();
            if (user.getName().equals(name)) {
                return user;
            }
        }
        user = dbService.readByName(name);
        if (user != null) {
            cache.put(new Element(user.getId(), user));
        }
        return user;
    }

    @Override
    public List<UserDataSet> readAll() {
        return dbService.readAll();
    }

    @Override
    public void shutdown() {
        dbService.shutdown();
    }

    @Override
    public void close() throws Exception {
        dbService.close();
    }
}
