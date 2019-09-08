package com.github.vimboard.server.service;

import com.github.vimboard.server.model.City;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Deprecated
public class CityService implements ICityService {

    @Override
    public List<City> findAll() {
        List<City> result = new ArrayList<>();

        City city = new City();
        city.setId(1001L);
        city.setName("Default");
        city.setPopulation(5750258);
        result.add(city);

        city = new City();
        city.setId(1034L);
        city.setName("Muhosransk");
        city.setPopulation(234);
        result.add(city);

        city = new City();
        city.setId(1821L);
        city.setName("Seversk-4");
        city.setPopulation(1608);
        result.add(city);

        return result;
    }
}
