package josehomenhuck.wheaterapi.dto;

import josehomenhuck.wheaterapi.entity.Weather;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityResponse {
    private String city;
    private Weather weather;
    private String message;
}
