package josehomenhuck.wheaterapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import josehomenhuck.wheaterapi.repository.CityRepository;
import josehomenhuck.wheaterapi.entity.City;
import josehomenhuck.wheaterapi.entity.Weather;
import josehomenhuck.wheaterapi.dto.CityResponse;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private CityService cityService;

    @Test
    void testFindAll() {
        List<City> mockCities = Arrays.asList(new City(), new City());
        when(cityRepository.findAll()).thenReturn(mockCities);

        List<CityResponse> responses = cityService.findAll();

        assertNotNull(responses);
        assertEquals(2, responses.size());
        verify(cityRepository).findAll();
    }

    @Test
    void testSaveNewCity() throws Exception {
        City newCity = new City();
        newCity.setCity("porto alegre");

        // Mock weather API request
        ObjectNode weatherNode = JsonNodeFactory.instance.objectNode();
        weatherNode.putObject("0")
                .put("main", "Clear")
                .put("description", "clear sky");

        ObjectNode mainNode = JsonNodeFactory.instance.objectNode();
        mainNode.put("temp", 300)
                .put("temp_max", 302)
                .put("temp_min", 298)
                .put("feels_like", 301);

        ObjectNode mockRoot = JsonNodeFactory.instance.objectNode();
        mockRoot.set("weather", weatherNode);
        mockRoot.set("main", mainNode);

        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn("mock response");
        when(objectMapper.readTree(anyString())).thenReturn(mockRoot);
        when(cityRepository.save(any(City.class))).thenReturn(newCity);

        CityResponse response = cityService.save(newCity);

        assertNotNull(response);
        assertEquals("Created new city: porto alegre", response.getMessage());
        verify(cityRepository).save(newCity);
    }

    @Test
    void testSaveWithNullWeather() {
        City newCity = new City();
        newCity.setCity("invalidcity");

        when(restTemplate.getForObject(anyString(), eq(String.class))).thenThrow(new RuntimeException());

        assertThrows(IllegalArgumentException.class, () -> {
            cityService.save(newCity);
        });
    }
}