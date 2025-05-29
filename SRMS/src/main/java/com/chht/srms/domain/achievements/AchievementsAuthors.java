package com.chht.srms.domain.achievements;

import lombok.Data;

@Data
public class AchievementsAuthors {
    private Long research_id;
    private Long author_id;
    private String username;
    private String role;
}
