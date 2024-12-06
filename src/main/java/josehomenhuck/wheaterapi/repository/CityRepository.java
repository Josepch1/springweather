package josehomenhuck.wheaterapi.repository;

import josehomenhuck.wheaterapi.entity.City;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends MongoRepository<City, String> {
    Optional<City> findByCity(String city);

    default City findByCityOrElseNull(String city) {
        return findByCity(city).orElse(null);
    }
}
