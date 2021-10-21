package com.group15.CreamCloneBackend.domain.user.dto;

import lombok.Getter;

@Getter
public class BookmarkRequestDto {
    private final Boolean bookmark;
    private final Long productId;

    public BookmarkRequestDto(Boolean bookmark, Long productId){
        this.bookmark = bookmark;
        this.productId = productId;
    }


}