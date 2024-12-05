package josehomenhuck.wheaterapi.web;

import josehomenhuck.wheaterapi.dto.CityResponse;
import josehomenhuck.wheaterapi.service.CityService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<CityResponse>> findAll() {
        try{
            return ResponseEntity.ok(cityService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{city}")
    public ResponseEntity<CityResponse> findByCiy(@PathVariable String city) {
        try {
            return ResponseEntity.ok(cityService.findByCity(city));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
