package co.com.autenthication.r2dbc.helper;

import co.com.autenthication.r2dbc.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReactiveRepository extends ReactiveCrudRepository<UserEntity, String> {
}
