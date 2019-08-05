package dy.springboot.demo1.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * SpringBoot获取SpringBean容器的方式
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/4/17
 */
@Component
public class SpringIocUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (null == SpringIocUtil.applicationContext) {
            SpringIocUtil.applicationContext = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
