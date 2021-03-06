package com.h3c.idcloud.infra.security;

import com.h3c.idcloud.core.pojo.dto.security.UserToken;
import com.h3c.idcloud.core.pojo.dto.user.User;

/**
 * RESTFul authenticate service
 *
 * Created by qct on 2016/2/3.
 */
public interface AuthService {
    /**
     * 生成JWT令牌
     *
     * @param id JWT提供的独一无二的JWT请求ID
     * @param issuer 发起人
     * @param subject 令牌的主题
     * @param ttlMillis 令牌生存周期
     * @return JWT令牌
     */
    String createJWT(String id, String issuer, String subject, long ttlMillis);

    /**
     * 生成某个用户的JWT令牌, 具体生成算法对用户透明
     * @param user 用户
     * @return JWT令牌
     */
    UserToken createJWT(User user);

    /**
     * 验证token是否合法，在redis和db中分别查询。
     * @param token
     * @return
     */
    boolean verifyToken(String token);
}
