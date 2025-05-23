package com.chht.srms.controller;

import com.chht.srms.domain.research.ResearchOutputs;
import com.chht.srms.domain.shared.Result;
import com.chht.srms.service.ResService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/research")
public class ResController {
    @Autowired
    private ResService resService;

    @GetMapping
    public Result getAllRes() {
        return Result.success(resService.getAllRes());
    }

    @GetMapping("/{researchId}")
    public Result getById(@PathVariable Long researchId) {
        return Result.success(resService.getByResearchId(researchId));
    }

    @GetMapping("/type")
    public Result getByType(@RequestParam String type) {
        return Result.success(resService.getByType(type)); //paper,patent
    }

    @GetMapping("/state")
    public Result getByState(@RequestParam String status) {
        return Result.success(resService.getByState(status));
    }

    @GetMapping("/search")
    public Result searchResearch(@RequestParam String keyword) {
        return Result.success(resService.searchResearch(keyword));
    }

    @PostMapping
    public Result createResearch(@RequestBody ResearchOutputs res) {
        res.setStatus("draft");
        return Result.success(resService.createResearch(res));
    }

    @PutMapping("/{resId}")
    public Result updateResearch(@PathVariable Long resId, @RequestBody ResearchOutputs newRes) {
        return Result.success(resService.updateRes(resId,newRes));
    }

    @DeleteMapping("/delete/{resId}")
    public Result deleteResearch(@PathVariable Long resId) {
        try {
            resService.deleteResearchById(resId);
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
        return Result.success();
    }

}