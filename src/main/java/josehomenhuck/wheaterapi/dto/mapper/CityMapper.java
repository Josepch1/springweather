package josehomenhuck.wheaterapi.dto.mapper;

import josehomenhuck.wheaterapi.dto.CityResponse;
import josehomenhuck.wheaterapi.entity.City;

public class CityMapper {
    public static CityResponse toResponse(City city) {
        return new CityResponse(city.getCity(), city.getWeather(), null);
    }
}
