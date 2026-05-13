package truthguard_backend.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin("*")
public class DashboardController {

    @GetMapping("/stats")
    public Map<String, Object> getStats() {

        Map<String, Object> data = new HashMap<>();

        data.put("totalScans", 12450);
        data.put("threatsDetected", 2184);
        data.put("safeResults", 10266);
        data.put("accuracy", 98.2);

        return data;
    }
}