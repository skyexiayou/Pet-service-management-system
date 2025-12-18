package cn.edu.xaut.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * JWT工具类
 * 用于生成、解析和验证JWT令牌
 */
@Component
public class JwtUtils {

    private static String secret;
    private static Long expiration;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        JwtUtils.secret = secret;
    }

    @Value("${jwt.expiration}")
    public void setExpiration(Long expiration) {
        JwtUtils.expiration = expiration;
    }

    /**
     * 获取签名密钥
     */
    private static SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成JWT令牌
     * @param claims 声明信息（包含用户名、管理员标识等）
     * @return JWT令牌字符串
     */
    public static String generateToken(Map<String, Object> claims) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 解析JWT令牌
     * @param token JWT令牌
     * @return 声明信息
     */
    public static Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 验证JWT令牌是否有效
     * @param token JWT令牌
     * @return 是否有效
     */
    public static boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从令牌中获取用户名
     * @param token JWT令牌
     * @return 用户名
     */
    public static String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.get("username", String.class);
    }

    /**
     * 从令牌中获取管理员标识
     * @param token JWT令牌
     * @return 是否为管理员
     */
    public static Integer getIsAdminFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.get("isAdmin", Integer.class);
    }
}
