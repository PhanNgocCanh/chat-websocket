package com.example.chatapp.mapper;

import java.util.List;

public interface EntityMapper<D, E> {
    E toEntity (D dto);
    D toDTO (E entity);
    List<E> toEntities (List<D> DTOs);
    List<D> toDTOs (List<E> entities);
}
