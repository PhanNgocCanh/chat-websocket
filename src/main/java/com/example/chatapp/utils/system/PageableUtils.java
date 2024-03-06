package com.example.chatapp.utils.system;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PageableUtils {
    public static Pageable of(int page, int size) {
        return PageRequest.of(page, size);
    }

    public static Pageable of(int page, int size, Map<String, String> sorts, boolean nativeQuery) {
        List<Sort.Order> orders = new ArrayList<>();
        if(nativeQuery) {
            orders.add(new Sort.Order(Sort.Direction.DESC, "created_date"));
        } else {
            orders.add(new Sort.Order(Sort.Direction.DESC, "createdDate"));
        }
        if(sorts != null) {
            sorts.forEach((k, v) -> orders.add(new Sort.Order(Sort.Direction.valueOf(v), k)));
        }

        return PageRequest.of(page, size, Sort.by(orders));
    }
}
