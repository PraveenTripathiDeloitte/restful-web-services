package com.spring.rest_webservices.restfulwebservices.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.spring.rest_webservices.restfulwebservices.response.SomeResponse;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilteringController {

    @GetMapping("/static-filtered-result")
    public SomeResponse getFilteredResult() {
        return new SomeResponse("value1", "value2", "value3");
    }

    @GetMapping("/dynamic-filtered-result")
    public MappingJacksonValue getDynamicFilteredResult() {
        SomeResponse response = new SomeResponse("value1", "value2", "value3");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.
                filterOutAllExcept("field1", "field2");

        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(response);
        mapping.setFilters(filters);

        return mapping;
    }
}
