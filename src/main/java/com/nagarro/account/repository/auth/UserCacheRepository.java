package com.nagarro.account.repository.auth;

import com.nagarro.account.entity.auth.UserCacheEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCacheRepository extends CrudRepository<UserCacheEntity, String> {

}
