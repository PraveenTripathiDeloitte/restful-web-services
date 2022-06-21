package com.spring.rest_webservices.restfulwebservices.response;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@JsonIgnoreProperties(value = {"field1", "field2"})
/*
above annotation is used to provide the list
of fields which need to filter out from response
 */
@JsonFilter("SomeBeanFilter")
public class SomeResponse {

    private String field1;

    private String field2;

//    @JsonIgnore
    /*
    above annotation is used for filtering the field from
    output response
     */
    private String field3;

    public SomeResponse(String field1, String field2, String field3) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }
}
