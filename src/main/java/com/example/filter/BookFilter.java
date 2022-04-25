package com.example.filter;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BookFilter extends DefaultFilter{
    private String name;
    private String title;
    private Integer bookPage;
}
