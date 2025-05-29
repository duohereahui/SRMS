package com.chht.srms.service;

import com.chht.srms.domain.achievements.AchievementsOutputs;
import java.util.List;

public interface ResService {

    List<AchievementsOutputs> getAllRes();
    List<AchievementsOutputs> getByType(String type);
    List<AchievementsOutputs> getByState(String state);
    List<AchievementsOutputs> getByResearchId(Long researchId);
    List<AchievementsOutputs> searchResearch(String keyword);


    AchievementsOutputs createResearch(AchievementsOutputs res);

    AchievementsOutputs updateRes(Long resId, AchievementsOutputs newRes);

    void deleteResearchById(Long researchId);
}