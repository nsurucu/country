package com.nobelglobe.ro.gameapp;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountryController {

    @Autowired
    JsonReader jsonReader;

    @GetMapping("/routing/{origin}/{destination}")
    public ResponseEntity<String> greeting(@PathVariable("origin") String origin, @PathVariable("destination") String destination) throws IOException {
        List<String> listem = jsonReader.call(origin, destination);

        String str = null;
        for (int i = 0; i < listem.size(); i++) {
            str = str +
					"route : [" + "'" + origin.toUpperCase(Locale.ROOT) + "' , " + "'" + listem.get(i) + "' , " + "'" + destination.toUpperCase(Locale.ROOT) + "'" + "] \n";

        }
        return ResponseEntity.ok(str);
    }
}