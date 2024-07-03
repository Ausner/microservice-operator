package com.laboratorio.operator.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MovieDto {

    private String name;
    private String description;
    private String img;
    private Boolean alquilada;
    private Integer ano;
    private String video;
    private String director;
    private Long price;

}