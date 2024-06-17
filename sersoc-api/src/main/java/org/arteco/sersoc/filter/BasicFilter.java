package org.arteco.sersoc.filter;


import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

@Data
public class BasicFilter {

    @Parameter
    String term;

}
