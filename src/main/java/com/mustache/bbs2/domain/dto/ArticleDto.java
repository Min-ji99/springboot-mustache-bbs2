package com.mustache.bbs2.domain.dto;

import com.mustache.bbs2.domain.entity.Article;
import lombok.Getter;

@Getter
public class ArticleDto {
    private String title;
    private String content;

    public ArticleDto(String title, String content) {
        this.title = title;
        this.content=content;
    }
    public Article getEntity(){
        return new Article(title, content);
    }
}
