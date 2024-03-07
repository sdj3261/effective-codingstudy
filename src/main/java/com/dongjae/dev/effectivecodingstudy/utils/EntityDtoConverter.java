package com.dongjae.dev.effectivecodingstudy.utils;

import java.lang.reflect.Field;

public class EntityDtoConverter {

    public static <D, E> D convert(E entity, Class<D> dtoClass) {
        try {
            D dto = dtoClass.getDeclaredConstructor().newInstance();

            for (Field entityField : entity.getClass().getDeclaredFields()) {
                entityField.setAccessible(true); // 엔티티의 private 필드에 접근
                Object value = entityField.get(entity); // 엔티티의 필드 값 가져오기

                try {
                    Field dtoField = dtoClass.getDeclaredField(entityField.getName()); // DTO에서 같은 이름의 필드 찾기
                    dtoField.setAccessible(true);

                    if (dtoField.getType().equals(entityField.getType())) { // 타입이 일치하면 값을 설정
                        dtoField.set(dto, value);
                    }
                } catch (NoSuchFieldException e) {
                }
            }

            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Error converting entity to dto", e);
        }
    }
}
