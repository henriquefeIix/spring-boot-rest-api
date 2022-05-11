package com.example.demo.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

public class MapperConverter {

    private static ModelMapper mapper = new ModelMapper();

    public static <S, D> void parse(S source, D destination) {
        mapper.map(source, destination);
    }

    public static <S, D> D parse(S source, Class<D> destination) {
        return mapper.map(source, destination);
    }

    public static <S, D> List<D> parseList(List<S> source, Class<D> destination) {
        return source.stream().map(s -> mapper.map(source, destination)).collect(Collectors.toList());
    }

}
