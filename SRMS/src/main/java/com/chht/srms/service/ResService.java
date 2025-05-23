package com.chht.srms.service;

import com.chht.srms.domain.research.ResearchOutputs;
import java.util.List;

public interface ResService {

    List<ResearchOutputs> getAllRes();
    List<ResearchOutputs> getByType(String type);
    List<ResearchOutputs> getByState(String state);
    List<ResearchOutputs> getByResearchId(Long researchId);
    List<ResearchOutputs> searchResearch(String keyword);


    ResearchOutputs createResearch(ResearchOutputs res);

    ResearchOutputs updateRes(Long resId, ResearchOutputs newRes);

    void deleteResearchById(Long researchId);
}