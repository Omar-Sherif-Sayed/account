package com.nagarro.account.entity.auth;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@NoArgsConstructor
@RedisHash(value = "UserCacheEntity", timeToLive = 300)
public class UserCacheEntity {

    @Id
    private String username;
    private String token;

}
