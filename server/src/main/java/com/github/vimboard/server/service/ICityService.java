package com.github.vimboard.server.service;

import com.github.vimboard.server.model.City;

import java.util.List;

@Deprecated
public interface ICityService {

    List<City> findAll();
}
