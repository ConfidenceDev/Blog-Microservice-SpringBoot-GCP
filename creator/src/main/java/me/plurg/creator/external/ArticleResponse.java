package me.plurg.creator.external;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleResponse {

    private long id;
    private long posterId;
    private String title;
    private String content;
    private Instant timestamp;
}
