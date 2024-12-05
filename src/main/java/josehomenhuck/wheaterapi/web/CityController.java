package josehomenhuck.wheaterapi.web;

import josehomenhuck.wheaterapi.dto.CityResponse;
import josehomenhuck.wheaterapi.entity.City;
import josehomenhuck.wheaterapi.service.CityService;
import josehomenhuck.wheaterapi.dto.CityRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weather")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("")
    public List<CityResponse> findAll() {
        return cityService.findAll();
    }

    @GetMapping("/{name}")
    public CityResponse findById(@PathVariable String name) {
        return cityService.findByCity(name);
    }
}
