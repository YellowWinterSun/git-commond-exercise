package dy.springboot.demo1.interceptor;

import dy.springboot.demo1.exception.AppException;
import dy.springboot.demo1.util.JwtTokenUtil;
import dy.springboot.demo1.util.StringMatchUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.InvalidClaimException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 登陆拦截器
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/7/21
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * http进入controller handler前
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("---------------------------- in LoginInterceptor.preHandler() -----------------------------");

        String requestUri = request.getRequestURI();
        System.out.println("本次请求的url: " + requestUri);

        // 验证有没有token
        String token = null;
        if (null == request.getCookies()) {
            throw new AppException("还未登陆");
        }
        for (Cookie cookie : request.getCookies()){
            if ("TOKEN".equals(cookie.getName())) {
                token = cookie.getValue();
                break;
            }
        }
        if (null == token) {
            System.out.println("还未登陆");
            throw new AppException("还未登陆");
            //return false;
        }

        // 校验token信息
        try{
            Claims claims = JwtTokenUtil.parseToken(token);
            String userName = claims.get("userName").toString();
            String userId = claims.get("userId").toString();
            String tokenid = claims.getId();
            System.out.println("登陆中：" + userId + ":" + userName);

            // 续期规则：如果在剩余的1/3有效时间内，仍然保持活跃，则为用户续期
            if ((claims.getExpiration().getTime() - System.currentTimeMillis()) <= JwtTokenUtil.TTL_TIMEOUT/3) {
                System.out.println("触发续期");
                String newToken = JwtTokenUtil.createToken(userId, userName);
                Cookie newCookie = new Cookie("TOKEN", newToken);
                newCookie.setPath("/");
                response.addCookie(newCookie);
                System.out.println("续期成功");
            }

            // 向后续handler传递用户状态信息
            request.setAttribute("loginingUserId", userId);

            return true;
        } catch(ExpiredJwtException e) {
            System.out.println("token过期");
            throw new AppException("token过期");
            //return false;
        } catch (InvalidClaimException e) {
            System.out.println("token信息有误");
            throw new AppException("token信息有误");
            //return false;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
            //return false;
        }


    }

    /**
     * 响应Controller方法后，在进行View视图渲染前。记录控制器处理完请求所消耗的时间
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println(" ---- controller调用结束 ----- ");
    }

    /**
     * 整个HTTP请求处理结束之后。记录整个请求处理完所消耗的时间，内存情况。如果有异常，则会记录本次请求的报错信息
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println(" ---------------------------------- Http请求结束 ---------------------------------- ");
    }
}
