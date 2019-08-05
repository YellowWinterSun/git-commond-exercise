package dy.springboot.demo1.conf;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatisConfig 这里没有过多复杂东西，只是设置一下方言
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/7/24
 */
@Configuration
public class MyBatisPlusConfig {
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        //设置方言
        page.setDialectType("mysql");
        return page;
    }
}
