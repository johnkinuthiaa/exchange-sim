package com.slippery.newsservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewsItem {
    @Id
    private Long id;
    private String category;
    private Long datetime;
    private String image;
    private String related;
    private String source;
    private String summary;
    private String url;
}
