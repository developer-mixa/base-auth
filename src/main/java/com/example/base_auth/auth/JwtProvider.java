package com.example.base_auth.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
@Log //Генерирует логгер для класса
public class JwtProvider {

    @Value("$(jwt.secret)")
    private String jwtSecret;

    public String generateToken(String username){
        Date date = Date.from(LocalDate.now()
                .plusDays(15)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()); //Создаём дату на 15 дней вперёд
        return Jwts.builder()
                .setSubject(username) //С помощью subject иденфицируем пользователя
                .setExpiration(date) //Дата истечения срока токена
                .signWith(SignatureAlgorithm.HS512, jwtSecret) //Подписывает JWT с использованием алгоритма HS512 и секретного ключа jwtSecret.
                .compact(); //Вовзращаем токен в виде строки

        /*
        * HS512 использует SHA-512,
        *  что обеспечивает высокий уровень безопасности,
        *  но может быть менее эффективным по сравнению с алгоритмами
        *  с меньшим размером хеша, такими как HS256 (SHA-256).
        Другие алгоритмы, такие как HS256 (SHA-256), HS384 (SHA-384) и HS512 (SHA-512),
        *  также используют алгоритмы хеширования SHA,
        *  но с разным размером хеша.
        * Выбор между ними зависит от баланса между безопасностью и производительностью.
        * */
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);

            /*
            Создает парсер JWT, устанавливает секретный ключ для проверки
            подписи и пытается разобрать переданный JWT.
            Если JWT валиден, выполнение продолжается
            */

            return true;
        } catch (Exception e){
            log.severe("invalid token");
        }
        return false;
    }

    public String getLoginFromToken(String token){
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

        /*
        Создает парсер JWT, устанавливает секретный ключ для проверки подписи,
        разбирает переданный JWT, извлекает тело JWT (payload)
        и получает из него утверждения (claims)
        */
        return claims.getSubject();
    }

}
