package kr.sm.itaewon.routepang.repo;

import kr.sm.itaewon.routepang.model.redis.Session;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Session, String> {
}
