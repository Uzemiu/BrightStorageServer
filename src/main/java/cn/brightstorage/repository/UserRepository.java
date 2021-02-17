package cn.brightstorage.repository;

import cn.brightstorage.model.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, String>{

    Optional<User> getUserByPhone(String phone);
}
