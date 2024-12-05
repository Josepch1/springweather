package josehomenhuck.wheaterapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.lang.Nullable;
import josehomenhuck.wheaterapi.dto.CityResponse;
import josehomenhuck.wheaterapi.entity.City;
import josehomenhuck.wheaterapi.entity.Weather;
import josehomenhuck.wheaterapi.dto.mapper.CityMapper;
import josehomenhuck.wheaterapi.repository.CityRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.Normalizer;
import java.util.List;

@Service
@Transactional
public class CityService {
    @Value("${weather_api.key}")
    private String API_KEY;

    private final CityRepository cityRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public CityService(CityRepository cityRepository, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.cityRepository = cityRepository;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public List<CityResponse> findAll() {
        List<City> cities = cityRepository.findAll();

        return cities.stream()
                .map(CityMapper::toResponse)
                .toList();
    }

    public CityResponse findByCity(String city) {
        String cityToLower = Normalizer.normalize(city, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .toLowerCase();
        City findCity = cityRepository.findByCity(cityToLower);

        if (findCity != null) {
            // Capture the last temp
            Weather lastWeather = findCity.getWeather();
            String lastTempStr = lastWeather.getTemperature().replace(" °C", "").replace(",", ".");
            double lastTemp = Double.parseDouble(lastTempStr);

            // Update the weather
            CityResponse updatedCity = save(findCity);

            // Capture the new temp
            Weather newWeather = updatedCity.getWeather();
            String newTempStr = newWeather.getTemperature().replace(" °C", "").replace(",", ".");
            double newTemp = Double.parseDouble(newTempStr);

            // Calculate the difference
            double tempDifference = Math.abs(newTemp - lastTemp);

            String message;

            if (tempDifference > 1) {
                message = String.format("A temperatura de %s mudou de: %s -> %s", city, lastWeather.getTemperature(), newWeather.getTemperature());
            } else {
                message = String.format("A temperatura de %s não mudou: %s", city, newWeather.getTemperature());
            }

            updatedCity.setMessage(message);

            return updatedCity;
        } else {
            City newCity = new City();
            newCity.setCity(cityToLower);

            return save(newCity);
        }

    }

    public CityResponse save(City city) {
    Weather weather = requestWeather(city.getCity());
    if (weather == null) {
        throw new IllegalArgumentException("Weather data is required to save the city.");
    }

    city.setWeather(weather);

    City savedCity = cityRepository.save(city);

    CityResponse response = CityMapper.toResponse(savedCity);

    response.setMessage("Created new city: " + city.getCity());

    return response;
}

    @Nullable
    private Weather requestWeather(String city) {
        String url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s", city, API_KEY);

        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);

            return new Weather(
                    root.at("/weather/0/main").asText(),
                    root.at("/weather/0/description").asText(),
                    formatTemperature(root.at("/main/temp").asDouble()),
                    formatTemperature(root.at("/main/temp_max").asDouble()),
                    formatTemperature(root.at("/main/temp_min").asDouble()),
                    formatTemperature(root.at("/main/feels_like").asDouble())
            );
        } catch (Exception e) {
            return null;
        }
    }

    private String formatTemperature(double temp) {
        double formattedTemp = temp / 10.0;
        return String.format("%.1f °C", formattedTemp);
    }
}
