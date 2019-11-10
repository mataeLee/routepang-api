package kr.sm.itaewon.routepang.repo;

import kr.sm.itaewon.routepang.model.Link;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends CrudRepository<Link, Long> {

    Link findByLinkId(long linkId);
}
