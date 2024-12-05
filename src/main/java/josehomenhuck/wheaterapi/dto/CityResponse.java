package josehomenhuck.wheaterapi.dto;

import josehomenhuck.wheaterapi.entity.Weather;

public record CityResponse(
        String city,
        Weather weather
) {
}
