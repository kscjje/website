package com.hisco.cmm.modules.extend;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.ehcache.EhCacheCacheManager;

import com.hisco.cmm.modules.Sleep;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

// public abstract class CacheService { 원본 2021. 06. 21

public class CacheService {

    protected String CACHE_NAME = this.getClass().getName().replaceAll("\\.", "__");

    //// @Resource(name="cacheManager")
    protected EhCacheCacheManager ehcacheManager;

    protected CacheManager getCacheManager() {
        return ehcacheManager.getCacheManager();
    }

    protected Cache getCache() {

        Cache cache = getCacheManager().getCache(CACHE_NAME);

        if (cache == null) {
            getCacheManager().removeCache(CACHE_NAME);
            getCacheManager().addCache(CACHE_NAME);

            cache = getCacheManager().getCache(CACHE_NAME);
        } else if (cache.isDisabled()) {
            cache.setDisabled(false);
        }

        return cache;
    }

    protected boolean hasCacheData(String key) {

        Cache cache = getCache();
        if (cache == null)
            return false;
        Element element = cache.get(key);
        if (element == null || element.isExpired())
            return false;

        return cache.isElementInMemory(key);
    }

    protected Object getCacheData(String key) {

        Cache cache = getCache();
        if (cache == null)
            return null;
        Element element = cache.get(key);
        if (element == null || element.isExpired())
            return null;

        return element.getObjectValue();
    }

    protected long getCacheLastUpdatetime(String key) {
        Cache cache = getCache();
        if (cache == null)
            return 0;
        Element element = cache.get(key);
        if (element == null || element.isExpired())
            return 0;

        return element.getLastUpdateTime();
    }

    protected boolean setCacheData(String key, Object data) {
        Cache cache = getCache();
        if (cache == null)
            return false;
        cache.remove(key);

        Element element = new Element(key, data);
        cache.put(element);
        // cache.flush();

        return hasCacheData(key);
    }

    protected void removeAll() {
        getCacheManager().removeCache(CACHE_NAME);
    }

    protected void remove(String key) {
        Cache cache = getCache();
        if (cache == null)
            return;
        cache.remove(key);
    }

    @SuppressWarnings("rawtypes")
    protected void removeStartsWith(String key) {

        Cache cache = getCache();
        if (cache == null)
            return;
        List keys = cache.getKeys();

        if (keys != null && keys.size() > 0) {
            for (Object k : keys) {
                try {
                    String kk = String.valueOf(k);
                    if (kk.startsWith(key)) {
                        cache.remove(k);
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    protected void sleep() {
        this.sleep(100);
    }

    protected void sleep(int millisecond) {
        Sleep.Call(millisecond);
    }

}
