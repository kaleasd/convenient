package com.gyh.internalcommon.util;

import io.jsonwebtoken.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author gyh
 * */
@Slf4j
public class JwtUtil {
    /**
     * 秘钥，仅服务端存储
     * */
    private static String secret = "ko346134h_we]rg3in_yip1!";
    /**
     * @param subject
     * @param issueDate 签发时间
     * @return
     * */
    public static String createToken(String subject, Date issueDate) {
        String compactJws = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(issueDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        return compactJws;
    }
    /**
     * 解密 jwt
     * @param token
     * @return
     * @throws Exception
     * */
    public static JwtInfo parseToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            if (claims != null) {
                JwtInfo ji = new JwtInfo();
                ji.setSubject(claims.getSubject());
                ji.setIssueDate(claims.getIssuedAt().getTime());
                return ji;
            }
        } catch (ExpiredJwtException | IllegalArgumentException | SignatureException | MalformedJwtException | UnsupportedJwtException e) {
            e.printStackTrace();
            log.info("jwt过期了");
        }
        return null;
    }

}
