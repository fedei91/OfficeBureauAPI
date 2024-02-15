package com.example.officebureauapi.mappers.entitytodto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface EntityToDtoMapper<E, D> {
    D toDto(E entity);
    E toEntity(D dto, E entity);

    default Page<D> mapPageToDTOs(Page<E> entities) {
        return mapPageToCustomDTOPage(entities, this::toDto);
    }

    default <D> Page<D> mapPageToCustomDTOPage(Page<E> entities, Function<? super E, ? extends D> mapper) {
        final long totalCount = entities.getTotalElements();
        final Pageable pageable = entities.getPageable();

        List<D> resultList = entities.getContent().stream().map(mapper).collect(Collectors.toList());
        return new PageImpl<>(resultList, pageable, totalCount);
    }
}
