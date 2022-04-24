package com.example.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {
    private Long id;
    private String name;
    private String title;
    private int page;
    @JsonFormat(pattern = "MMM. d, yyyy HH:mm")
    private LocalDateTime createdAt;
    private List<AuthorResponse> authors;
}
