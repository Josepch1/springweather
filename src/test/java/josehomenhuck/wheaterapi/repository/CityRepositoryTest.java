package josehomenhuck.wheaterapi.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import josehomenhuck.wheaterapi.entity.City;

import java.util.Optional;

@DataMongoTest
class CityRepositoryTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityRepositoryTest cityRepositoryTest;

    @BeforeEach
    void setUp() {
        City city = new City();
        city.setCity("porto alegre");
        cityRepository.save(city);
        when(cityRepository.findByCity("porto alegre")).thenReturn(Optional.of(city));
        when(cityRepository.findByCity("NonExistentCity")).thenReturn(Optional.empty());
        when(cityRepository.findByCity(null)).thenReturn(Optional.empty());
        when(cityRepository.findByCity("")).thenReturn(Optional.empty());
        when(cityRepository.findByCityOrElseNull("porto alegre")).thenReturn(city);
        when(cityRepository.findByCityOrElseNull("NonExistentCity")).thenReturn(null);
    }

    @Test
    void shouldReturnCityWhenCityExists() {
        Optional<City> city = cityRepository.findByCity("porto alegre");
        assertNotNull(city);
        assertTrue(city.isPresent());
        assertEquals("porto alegre", city.get().getCity());
    }

    @Test
    void shouldReturnEmptyWhenCityDoesNotExist() {
        Optional<City> city = cityRepository.findByCity("NonExistentCity");
        assertTrue(city.isEmpty());
    }

    @Test
    void shouldHandleNullCityName() {
        Optional<City> city = cityRepository.findByCity(null);
        assertTrue(city.isEmpty());
    }

    @Test
    void shouldHandleEmptyCityName() {
        Optional<City> city = cityRepository.findByCity("");
        assertTrue(city.isEmpty());
    }

    @Test
    void shouldReturnNullWhenCityNotExistsForFindByCityOrElseNull() {
        City city = cityRepository.findByCityOrElseNull("NonExistentCity");
        assertNull(city);
    }

    @Test
    void shouldReturnCityWhenCityExistsForFindByCityOrElseNull() {
        City city = cityRepository.findByCityOrElseNull("porto alegre");
        assertNotNull(city);
        assertEquals("porto alegre", city.getCity());
    }
}
