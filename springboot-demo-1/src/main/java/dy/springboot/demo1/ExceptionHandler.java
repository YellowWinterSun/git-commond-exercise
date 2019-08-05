package dy.springboot.demo1;

import dy.springboot.demo1.exception.AppException;
import dy.springboot.demo1.vo.Msg;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理器
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/7/21
 */
@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseBody
    public Msg handle(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {

        if (e instanceof AppException) {
            AppException exception = (AppException) e;

            return Msg.error(exception.getMessage());
        }
        else {
            e.printStackTrace();
            return Msg.error(e.getMessage());
        }

    }
}
