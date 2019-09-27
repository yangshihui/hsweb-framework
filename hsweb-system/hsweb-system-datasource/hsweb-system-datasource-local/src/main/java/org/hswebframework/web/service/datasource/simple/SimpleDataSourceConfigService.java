package org.hswebframework.web.service.datasource.simple;

import org.hswebframework.web.entity.datasource.DataSourceConfigEntity;
import org.hswebframework.web.id.IDGenerator;
import org.hswebframework.web.service.EnableCacheGenericEntityService;
import org.hswebframework.web.service.datasource.DataSourceConfigService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * 默认的服务实现
 *
 * @author hsweb-generator-online
 */
@Service("dataSourceConfigService")
@CacheConfig(cacheNames = "data-source")
public class SimpleDataSourceConfigService extends EnableCacheGenericEntityService<DataSourceConfigEntity, String>
        implements DataSourceConfigService {

    @Override
    protected IDGenerator<String> getIDGenerator() {
        return IDGenerator.MD5;
    }

}
