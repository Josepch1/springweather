package josehomenhuck.wheaterapi.repository;

import josehomenhuck.wheaterapi.entity.City;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends MongoRepository<City, String> {
    City findByCity(String city);
}
