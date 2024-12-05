package josehomenhuck.wheaterapi.dto.mapper;

import josehomenhuck.wheaterapi.dto.CityRequest;
import josehomenhuck.wheaterapi.dto.CityResponse;
import josehomenhuck.wheaterapi.entity.City;

public class CityMapper {
    public static City toEntity(CityRequest request) {
        City city = new City();
        city.setCity(request.getCity());
        return city;
    }

    public static CityResponse toResponse(City city) {
        return new CityResponse(city.getCity(), city.getWeather(), null);
    }

    public static CityRequest toRequest(City city) {
        return new CityRequest(city.getCity());
    }
}
