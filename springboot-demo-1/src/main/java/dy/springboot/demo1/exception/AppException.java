package dy.springboot.demo1.exception;

/**
 * 自定义Excpetion
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/7/21
 */
public class AppException extends RuntimeException{
    private String code;

    public AppException(String message) {
        super(message);
        this.code = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
