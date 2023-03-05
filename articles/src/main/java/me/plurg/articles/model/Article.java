package me.plurg.articles.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Article {

    private long id;
    private long posterId;
    private String title;
    private String content;
    private Instant timestamp;
}
