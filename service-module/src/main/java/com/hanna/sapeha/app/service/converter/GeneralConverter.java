package com.hanna.sapeha.app.service.converter;

import java.util.List;

public interface GeneralConverter<E, D> {

    D convertEntityToDTO(E entity);

    List<D> convertEntitiesToDTO(List<E> entities);

    E convertDTOToEntity(D object);
}
