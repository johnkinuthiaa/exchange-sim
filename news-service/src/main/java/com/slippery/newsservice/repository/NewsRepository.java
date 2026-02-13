package com.slippery.newsservice.repository;

import com.slippery.newsservice.models.NewsItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<NewsItem,Long> {
}
