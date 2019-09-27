package org.hswebframework.web.service.form.simple.dict;

import org.hswebframework.ezorm.core.DictionaryCodec;
import org.hswebframework.ezorm.core.ValueCodec;
import org.hswebframework.web.entity.form.DictConfig;

/**
 * @author zhouhao
 * @since 3.0
 */
public interface OptionalConvertBuilderStrategy {

    /**
     * @param type 类型是否支持
     */
    boolean support(String type);

    /**
     * 根据配置创建选项转换器
     *
     * @param dictConfig 配置内容
     * @return 转换器对象
     */
    DictionaryCodec build(DictConfig dictConfig);

    ValueCodec buildValueConverter(DictConfig dictConfig);
}
