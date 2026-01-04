package com.ipaas.monitoringplstformsys.common.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.impl.PublicClaims;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ipaas.monitoringplstformsys.common.constant.DeipaasExceptionEnum;
import com.ipaas.monitoringplstformsys.common.constant.ExceptionMessage;
import com.ipaas.monitoringplstformsys.common.constant.TokenConstant;
import com.ipaas.monitoringplstformsys.common.exception.base.XDapSystemException;
import com.ipaas.monitoringplstformsys.common.exception.base.XdapWarningException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @author: 李绍刚
 * @since: 2021/5/26
 * @history: 2021/5/26 created by 李绍刚
 * @history: 2022/6/13 updated by dejun.fan
 */
@Slf4j
public class JwtUtils {

    public static String generalSecret() {
        return DigestUtils.md5DigestAsHex(TokenConstant.JWT_SECRET.getBytes());
    }

    /**
     * 生成jwt
     *
     * @param claims    用户信息
     * @param ttlMillis token有效时间
     * @return
     */
    public static String createJWT(Map<String, Object> claims, long ttlMillis) {
        Date issDate = new Date();
        String finalSecret = generalSecret();

        HashMap<String, Object> map = new HashMap<>();
        map.put("alg", "HS512");
        map.put("typ", "JWT");
        JWTCreator.Builder builder = JWT.create().withHeader(map).withIssuedAt(issDate);
        //过期时间为0时，JWT不设置过期时间
        if(ttlMillis != 0L){
            long expMillis = System.currentTimeMillis() + ttlMillis;
            builder.withExpiresAt(new Date(expMillis));
        }
        for (String key : claims.keySet()) {
            builder.withClaim(key, (String) claims.get(key));
        }
        return builder.sign(Algorithm.HMAC512(finalSecret));
    }

    /**
     * 解析jwt字符串 获得用户信息
     *
     * @param token
     * @return claim
     */
    public static Map<String, Object> parseJWT(String token) {
        Map<String, Object> map = new HashMap<>();
        JWTVerifier jwtVerifier;

        try {
            jwtVerifier = JWT.require(Algorithm.HMAC512(generalSecret())).build();
            DecodedJWT jwt = jwtVerifier.verify(token);
            Map<String, Claim> claims = jwt.getClaims();
            for (String k : claims.keySet()) {
                map.put(k, claims.get(k).asString());
            }
        } catch (RuntimeException e) {
            throw new XDapSystemException(e.getMessage());
        }
        return map;
    }

    /**
     * 校验token并解析用户信息
     *
     * @param token
     * @return
     */
    public static Map<String, Claim> verifyToken(String token) {

        Algorithm algorithm = Algorithm.HMAC512(generalSecret());
        DecodedJWT decodedJWT = JWT.decode(token);
        if (!algorithm.getName().equals(decodedJWT.getAlgorithm())) {
            throw new XDapSystemException(ExceptionMessage.DEPORTAL_TOKEN_INVALID);
        }
        algorithm.verify(decodedJWT);
        return decodedJWT.getClaims();
    }

    /**
     * 生成JwtToken
     */
    public static String createJwtToken(Algorithm algorithm, Map<String, Object> headerClaims, Map<String, Object> payloadClaims) {
        JWTCreator.Builder builder = JWT.create()
                // header
                .withHeader(headerClaims)
                // payload
                .withJWTId((String) payloadClaims.get(PublicClaims.JWT_ID))
                .withIssuer((String) payloadClaims.get(PublicClaims.ISSUER))
                .withIssuedAt(new Date())
                .withSubject((String) payloadClaims.get(PublicClaims.SUBJECT))
                .withAudience((String) payloadClaims.get(PublicClaims.AUDIENCE))
                .withNotBefore((Date) payloadClaims.get(PublicClaims.NOT_BEFORE))
                .withExpiresAt((Date) payloadClaims.get(PublicClaims.EXPIRES_AT));
        return builder.sign(algorithm);
    }

    /**
     * 生成HS256签名的JwtToken
     */
    public static String createHS256SignedJwtToken(String secret, Map<String, Object> headerClaims, Map<String, Object> payloadClaims) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return createJwtToken(algorithm, headerClaims, payloadClaims);
    }

    /**
     * 生成HS512签名的JwtToken
     */
    public static String createHS512SignedJwtToken(String secret, Map<String, Object> headerClaims, Map<String, Object> payloadClaims) {
        Algorithm algorithm = Algorithm.HMAC512(secret);
        return createJwtToken(algorithm, headerClaims, payloadClaims);
    }

    /**
     * 校验token并获取用户名
     *
     * @param token
     * @param tokenSecret
     * @param userName
     * @return
     */
    public static String parseToken(String token, String tokenSecret, String userName) {
        DecodedJWT decode1 = JWT.decode(token);
        String header = decode1.getHeader();
        JSONObject jsonObject = (JSONObject) JSON.parse(java.util.Base64.getDecoder().decode(header));
        String alg = jsonObject.getString("alg");
        // 不需要验签
        if(StringUtils.isBlank(tokenSecret)){
            return paresToken(token,userName);
        }
        if (alg.contains("RS256")) {
            return parseRS256(token, tokenSecret, userName);
        } else if (alg.contains("HS256")) {
            return parseHS256(token, tokenSecret, userName);
        }
        return null;
    }

    /**
     * RS256校验解析
     *
     * @param token
     * @param publicKey
     * @param userAccount
     * @return
     */
    private static String parseRS256(String token, String publicKey, String userAccount) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(x509EncodedKeySpec);
            Algorithm algorithm = Algorithm.RSA256(rsaPublicKey, null);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim(userAccount).asString();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error(e.getMessage(), e);
            throw new XdapWarningException(DeipaasExceptionEnum.SSO_SIGN_VERIFICATION_SECRET_KEY_ERROR);
        }
    }

    /**
     * JWT 获取 payload中字段信息
     *
     * @param token
     * @param userAccount
     * @return
     */
    private static String paresToken(String token, String userAccount) {
        try {
            DecodedJWT decode = JWT.decode(token);
            return decode.getClaim(userAccount).asString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new XdapWarningException(DeipaasExceptionEnum.SSO_SIGN_VERIFICATION_SECRET_KEY_ERROR);
        }
    }
    /**
     * HS256校验解析
     *
     * @param token
     * @param key
     * @param userAccount
     * @return
     */
    private static String parseHS256(String token, String key, String userAccount) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(key);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim(userAccount).asString();
        } catch (Exception e) {
            throw new XdapWarningException(DeipaasExceptionEnum.SSO_SIGN_VERIFICATION_SECRET_KEY_ERROR);
        }
    }

}
