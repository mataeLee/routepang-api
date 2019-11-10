package kr.sm.itaewon.routepang.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.sm.itaewon.routepang.exception.UnauthorizedException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class JwtService {
    private static final String SALT =  "luvookSecret";

    public <T> String create(String key, T data, String subject){
        System.out.println("create");
        String jwt = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("regDate", System.currentTimeMillis())
                .setSubject(subject)
                .claim(key, data)
                .signWith(SignatureAlgorithm.HS256, this.generateKey())
                .compact();
        return jwt;
    }

    public boolean isUsable(String jwt) {
        System.out.println("is usalbe");
        try{
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(this.generateKey())
                    .parseClaimsJws(jwt);
            return true;

        }catch (Exception e) {
            System.out.println("jwtService error : is un usable token!");
            return false;
        }
    }
    private byte[] generateKey(){
        System.out.println("generateKey");
        byte[] key = null;
        try {
            key = SALT.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
        }

        return key;
    }

}
