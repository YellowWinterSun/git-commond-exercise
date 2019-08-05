package dy.springboot.demo1.util;

import io.jsonwebtoken.*;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.constraints.NotNull;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 工具类
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/7/21
 */
public class JwtTokenUtil {

    public final static long TTL_TIMEOUT = 15 * 60 * 1000L; // token有效时间15分钟

    // 私钥
    private volatile static SecretKey secretKey = new SecretKeySpec(
            DatatypeConverter.parseBase64Binary("MY-DIY-SECRET-KEY"),
            SignatureAlgorithm.HS256.getJcaName());

    /**
     * 生成token
     */
    public static String createToken(@NotNull String userId, @NotNull String userName) {

        // 构建私有信息
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("userId", userId);
        claims.put("userName", userName);

        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)          // 必须放在第一行，设置私有信息
                .setId(userId)              // jwt唯一标识，保证唯一，防止重放攻击
                .setIssuedAt(new Date())    // jwt签发时间
                .setExpiration(new Date(System.currentTimeMillis() + TTL_TIMEOUT))  // 设置过期时间
                .signWith(SignatureAlgorithm.HS256, secretKey);  // 签名使用的签名算法及私钥

        return builder.compact();   // 开始压缩成 xxx.xxx.xxx 的jwt
    }

    /**
     * 解析 jwt
     * @param token
     * @return
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)   // 签名时使用的私钥
                .parseClaimsJws(token)  // 要解析的jwt内容
                .getBody();
    }

    /**
     * 验证 jwt
     * @param token
     */
    public static void validateToken(String token) throws ExpiredJwtException, InvalidClaimException {
        try{
            Claims claims = parseToken(token);
            String userName = claims.get("userName").toString();
            String userId = claims.get("userId").toString();
            String tokenid = claims.getId();

        } catch(ExpiredJwtException e) {
            //System.out.println("token expired");
            throw e;
        } catch (InvalidClaimException e) {
            //System.out.println("token invalid");
            throw e;
        } catch (Exception e) {
            //System.out.println("token error");
            throw e;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String token = createToken("1", "hdy");

        System.out.println("jwt : " + token);

        try {
            Claims claims = parseToken(token);
            System.out.println("验证jwt成功：" + claims.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread.sleep(11 * 1000L);

        try {
            Claims claims = parseToken(token);
            System.out.println("验证jwt成功：" + claims.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
