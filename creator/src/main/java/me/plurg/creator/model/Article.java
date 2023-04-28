package me.plurg.creator.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({"posterId", "title", "content"})
public class Article {

    @JsonProperty("posterId")
    private long posterId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;
}
