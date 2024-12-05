package josehomenhuck.wheaterapi.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Weather {
    private String status;
    private String description;
    private String temperature;
    private String maxTemperature;
    private String minTemperature;
    private String feelsLikeTemperature;
}
