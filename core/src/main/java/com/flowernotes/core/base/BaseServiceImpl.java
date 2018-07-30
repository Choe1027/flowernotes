package com.flowernotes.core.base;

import com.flowernotes.common.base.BaseBean;
import com.flowernotes.common.bean.Page;
import com.flowernotes.common.exception.CommonException;
import com.flowernotes.common.exception.Error;
import com.flowernotes.common.utils.ObjectUtil;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

/**
 * 公共service基础接口实现
 */
public abstract class BaseServiceImpl<T extends BaseBean> implements BaseService<T> {

    @Autowired
    private CacheService cacheService;

    /**
     * 获取mapper对象
     */
    protected abstract BaseMapper<T> getMainMapper();

    @Override
    public Page<T> listPage(T t) {
        return listPage(t, null, null);
    }

    @Override
    public Page<T> listPage(T t, Long start_time, Long end_time) {

        List<T> ts = getMainMapper().listPage(t, start_time, end_time, false);
        Page<T> page = new Page<T>();
        if (t.getPage() != null) {
            page = t.getPage();
            page.setDatas(ts);
        }
        return page;
    }

    @Override
    public Page<T> listPageDesc(T t) {
        return listPageDesc(t, null, null);
    }

    @Override
    public Page<T> listPageDesc(T t, Long start_time, Long end_time) {
        List<T> ts = getMainMapper().listPage(t, start_time, end_time, true);
        Page<T> page = new Page<T>();
        if (t.getPage() != null) {
            page = t.getPage();
            page.setDatas(ts);
        }
        return page;
    }

    @Override
    public Integer update(T t) {
        if (t.getId() == null) {
            throw new CommonException(Error.id_must_not_be_null);
        }
        Integer update = getMainMapper().update(t);
        if (useLocalCache() && t != null) {
            T t1 = localCache.get(t.getId());
            if (t1 != null){
                ObjectUtil.insertObj(t1,t);
                localCache.put(t.getId(),t1);
            }
        }
        if (useRedisCache() && t != null) {
            T t1 = cacheService.getMapValue(t.getClass().getName(), t.getId().toString());
            if (t1 != null){
                ObjectUtil.insertObj(t1,t);
                cacheService.setMapValue(t.getClass().getName(), t.getId().toString(),t1);
            }
        }
        return update;
    }

    @Override
    public Integer add(T t) {
        Integer add = getMainMapper().add(t);
        if (useLocalCache()) {
            localCache.put(t.getId(), t);
        }
        if (useRedisCache()) {
            cacheService.setMapValue(t.getClass().getName(), t.getId().toString(), t);
        }
        return add;
    }

    @Override
    public T get(T t) {
        T result = null;
        if (useLocalCache() && t.getId() != null) {
            result = localCache.get(t.getId());
            if (result != null) {
                return result;
            }
        }
        if (useLocalCache() && t.getId() != null) {
            result = cacheService.getMapValue(t.getClass().getName(), t.getId().toString());
            if (result != null) {
                return result;
            }
        }
        return getMainMapper().get(t);
    }

    @Override
    public T getById(Long id) {
        if (id == null) {
            throw new CommonException(Error.id_must_not_be_null);
        }
        T t = ObjectUtil.createObject(ObjectUtil.getGeneric(this.getClass()));
        t.setId(id);
        return get(t);
    }

    @Override
    public Integer delete(T t) {
        if (t.getId() == null){
            throw  new CommonException(Error.id_must_not_be_null);
        }
        Integer delete = getMainMapper().delete(t);
        if (useLocalCache()){
            localCache.remove(t.getId());
        }
        if (useRedisCache()){
            cacheService.removeMapValue(t.getClass().getName(),t.getId().toString());
        }
        return delete;
    }

    @Override
    public List<T> select(T t) {
        return getMainMapper().select(t);
    }

    @Override
    public List<T> selectDesc(T t) {
        return getMainMapper().select(t, null, null, true);
    }

    @Override
    public List<T> select(T t, Long start_time, Long end_time) {
        return getMainMapper().select(t, start_time, end_time, false);
    }

    @Override
    public List<T> selectDesc(T t, Long start_time, Long end_time) {
        return getMainMapper().select(t, start_time, end_time, true);
    }

    private Map<Long, T> localCache = new ConcurrentHashMap<>();

    /**
     * 是否本地缓存所有数据
     *
     * @return
     */
    protected boolean isLocalCacheAll() {
        return false;
    }

    /**
     * 是否使用本地缓存
     *
     * @return
     */
    protected boolean useLocalCache() {

        return false;
    }

    /**
     * 是否使用Redis缓存
     *
     * @return
     */
    protected boolean useRedisCache() {

        return false;
    }

    @PostConstruct
    public void initLocalCache(){
        if (isLocalCacheAll()){
            List<T> select = select(null);
            for (T t:select){
                localCache.put(t.getId(),t);
            }
        }
   }
}
