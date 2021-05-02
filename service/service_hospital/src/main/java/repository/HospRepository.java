package repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import xyz.klenkiven.yygh.model.hosp.Hospital;

/**
 * 医院的MongoRepository
 *
 * @author klenkiven
 */
@Repository
public interface HospRepository extends MongoRepository<Hospital, String> {
}
