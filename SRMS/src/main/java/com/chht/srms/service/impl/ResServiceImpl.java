package com.chht.srms.service.impl;

import com.chht.srms.domain.achievements.AchievementsOutputs;
import com.chht.srms.mapper.ResMapper;
import com.chht.srms.mapper.UserMapper;
import com.chht.srms.service.ResService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResServiceImpl implements ResService {
    @Autowired
    private ResMapper resMapper;

    Map<String, String> params = new HashMap<>();
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<AchievementsOutputs> getAllRes() {
        List<AchievementsOutputs> research = resMapper.select(params);
        research.forEach(p -> p.setAuthors(resMapper.selectAuthorByResearchId(p.getResearch_id())));
        return research;
    }

    @Override
    public List<AchievementsOutputs>  getByType(String type) {
        params.put("type", type.toLowerCase());
        List<AchievementsOutputs> research = resMapper.select(params);
        research.forEach(p -> p.setAuthors(resMapper.selectAuthorByResearchId(p.getResearch_id())));
        return research;
    }

    @Override
    public List<AchievementsOutputs> getByState(String status) {
        params.put("status", status.toLowerCase());
        List<AchievementsOutputs> research = resMapper.select(params);
        research.forEach(p -> p.setAuthors(resMapper.selectAuthorByResearchId(p.getResearch_id())));
        return research;
    }

    @Override
    public List<AchievementsOutputs> getByResearchId(Long researchId) {
        params.put("researchId", String.valueOf(researchId));
        List<AchievementsOutputs> research = resMapper.select(params);
        research.forEach(p -> p.setAuthors(resMapper.selectAuthorByResearchId(p.getResearch_id())));
        return research;
    }

    @Override
    public List<AchievementsOutputs> searchResearch(String keyword) {
        List<AchievementsOutputs> research = resMapper.searchResearch(keyword);
        research.forEach(p -> p.setAuthors(resMapper.selectAuthorByResearchId(p.getResearch_id())));
        return research;
    }
    @Override
    public AchievementsOutputs createResearch(AchievementsOutputs res){
        if(res.getTitle() == null || res.getTitle().isEmpty()){
            throw new IllegalArgumentException("标题不能为空");
        }

        resMapper.insertRes(res);
        res.getAuthors().forEach(author -> {
            // Add validation for existing user
            if (userMapper.findByUserId(author.getAuthor_id()) == null) {
                resMapper.deleteResearchById(res.getResearch_id());
                throw new IllegalArgumentException("用户ID不存在: " + author.getAuthor_id());
            }
            author.setResearch_id(res.getResearch_id());
            resMapper.insertAuthor(author);
        });
        return res;
    }
    public AchievementsOutputs updateRes(Long resId, AchievementsOutputs newRes){

        params.put("researchId", String.valueOf(resId));
        List<AchievementsOutputs> research = resMapper.select(params);
        research.forEach(p -> p.setAuthors(resMapper.selectAuthorByResearchId(p.getResearch_id())));
        AchievementsOutputs update=research.get(0);

        if (update == null) {
            throw new RuntimeException("Research not found");
        }

        if(newRes.getTitle() != null ){
            update.setTitle(newRes.getTitle());
        }
        if(newRes.getDescription() != null){
            update.setDescription(newRes.getDescription());
        }
        if(newRes.getType() != null){
            update.setType(newRes.getType());
        }
//        if(update.getAuthors() != null){
//            update.setAuthors(newRes.getAuthors());
//        }
        if(newRes.getStatus() != null){
            update.setStatus(newRes.getStatus());
        }
        if(newRes.getFile_path() != null){
            update.setFile_path(newRes.getFile_path());
        }
        if(newRes.getPublish_date() != null){
            update.setPublish_date(newRes.getPublish_date());
        }
        if(newRes.getJournal_name() != null){
            update.setJournal_name(newRes.getJournal_name());
        }
        if(newRes.getPaper_type() != null){
            update.setPaper_type(newRes.getPaper_type());
        }
        if(newRes.getDoi() != null){
            update.setDoi(newRes.getDoi());
        }
        if(newRes.getPatent_number() != null){
            update.setPatent_number(newRes.getPatent_number());
        }
        if(newRes.getImpact_factor() != null){
            update.setImpact_factor(newRes.getImpact_factor());
        }

        resMapper.updateRes(update);

        update.getAuthors().forEach(author -> {
            author.setResearch_id(update.getResearch_id());
            resMapper.selectAuthorByResearchId(update.getResearch_id()).add(author);
        });

        return update;
    }

    @Override
    public void deleteResearchById(Long researchId) {
        // 先删除关联作者
        resMapper.deleteAuthor(researchId);
        // 再删除研究主体
        int affectedRows = resMapper.deleteResearchById(researchId);
        if (affectedRows == 0) {
            throw new RuntimeException("未找到research_id为 " + researchId + " 的记录");
        }
    }
}