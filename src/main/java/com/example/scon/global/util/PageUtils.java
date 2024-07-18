package com.example.scon.global.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PageUtils {

    public static <T> Page<T> convertListToPage(List<T> list, Pageable pageable) {
//        int start = (int) pageable.getOffset();
//        int end = Math.min((start + pageable.getPageSize()), list.size());
//        List<T> subList = list.subList(start, end);
//        return new PageImpl<>(subList, pageable, list.size());
        // 페이지 번호와 페이지 사이즈를 확인하고 음수인 경우 0 또는 기본값으로 설정
        int page = pageable.getPageNumber() < 0 ? 0 : pageable.getPageNumber();
        int size = pageable.getPageSize() < 1 ? 10 : pageable.getPageSize();

        // 시작 위치 계산
        int start = (int) page * size;
        // 리스트의 끝 위치 계산
        int end = Math.min((start + size), list.size());

        // 시작 위치가 리스트 크기를 초과할 경우 빈 페이지 반환
        if (start > list.size()) {
            return new PageImpl<>(List.of(), pageable, 0);
        }

        // sublist를 사용하여 페이지에 맞는 데이터 추출
        List<T> subList = list.subList(start, end);
        return new PageImpl<>(subList, pageable, list.size());
    }
}
