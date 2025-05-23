package com.chht.srms.domain.research;

import lombok.Data;

@Data
public class ResearchAuthors {
    private Long research_id;
    private Long author_id;
    private String username;
    private String role;
}
