package dy.springboot.demo1;

import dy.springboot.demo1.util.ClassesUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 扫包代码，用于识别包下所有controller的url
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/8/16
 */
public class ScanUrlMain {

    public static void main(String[] args) throws Exception{
        // FIXME 修改你要扫的包名
        List<Class<?>> classes = ClassesUtils.searchClass("dy.springboot.demo1.controller");

        for (Class<?> cas : classes) {
            try {
                Controller annoController = cas.getAnnotation(Controller.class);
                RestController annoRestController = cas.getAnnotation(RestController.class);
                if (annoController != null || annoRestController != null) {
                    RequestMapping annoMapping = cas.getAnnotation(RequestMapping.class);
                    String headerUrl = "";
                    if (null != annoMapping && null != annoMapping.value() && annoMapping.value().length > 0) {
                        headerUrl = annoMapping.value()[0];
                    }

                    // 获取头部url
                    System.out.println("\n");
                    System.out.println("--------------------------------");
                    System.out.println("控制器：" + cas.getName());
                    System.out.println("控制器Url:" + headerUrl);

                    // 读取所有方法url
                    for (Method method : cas.getDeclaredMethods()) {
                        try {
                            RequestMapping methodAnno = method.getAnnotation(RequestMapping.class);
                            if (null != methodAnno && null != methodAnno.value()) {
                                String methodUrl = (methodAnno.value().length > 0) ? methodAnno.value()[0] : "";
                                System.out.printf("方法:%-30s\t\t\turl:%s\n", method.getName(), headerUrl + methodUrl);
                            }
                        } catch (Exception e) {
                            System.out.printf("XXX 方法:%-30s 出错\n", method.getName());
                            e.printStackTrace();
                            continue;
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("#### 扫描" + cas.getName() + "出错:" + e.getMessage());
                e.printStackTrace();
                continue;
            }
        }
    }
}
