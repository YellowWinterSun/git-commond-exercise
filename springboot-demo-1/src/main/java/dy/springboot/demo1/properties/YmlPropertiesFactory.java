package dy.springboot.demo1.properties;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

/**
 * 该类是扩展{@link org.springframework.core.io.support.DefaultPropertySourceFactory}的，
 * 用于{@link org.springframework.context.annotation.PropertySource}的factory属性。
 *
 * @PropertySource的facotroy属性，默认是使用 DefaultPropertySourceFactory 实现。该实现只能解析.properties配置文件，
 * 因此.yml文件无法解析。
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/7/23
 */
public class YmlPropertiesFactory extends DefaultPropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource)
            throws IOException {
        String sourceName = Optional.ofNullable(name).orElse(resource.getResource().getFilename());
        if (!resource.getResource().exists()) {
            // return an empty Properties
            return new PropertiesPropertySource(sourceName, new Properties());
        } else if (sourceName.endsWith(".yml") || sourceName.endsWith(".yaml")) {
            Properties propertiesFromYaml = loadYaml(resource);
            return new PropertiesPropertySource(sourceName, propertiesFromYaml);
        } else {
            return super.createPropertySource(name, resource);
        }
    }

    /**
     * load yaml file to properties
     *
     * @param resource
     * @return
     * @throws IOException
     */
    private Properties loadYaml(EncodedResource resource) throws IOException {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(resource.getResource());
        factory.afterPropertiesSet();
        return factory.getObject();
    }
}
