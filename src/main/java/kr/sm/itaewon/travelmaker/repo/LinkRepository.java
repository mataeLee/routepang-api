package kr.sm.itaewon.travelmaker.repo;

import kr.sm.itaewon.travelmaker.model.Link;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends CrudRepository<Link, Long> {

}
