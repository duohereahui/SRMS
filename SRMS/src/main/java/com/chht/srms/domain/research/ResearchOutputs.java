package com.chht.srms.domain.research;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ResearchOutputs {

    //论文部分
    private String journal_name;
    private String paper_type; //CA or JA
    private Integer impact_factor;
    private String doi;

    //专利部分

    private String patent_number;

    //公共部分
    private Long research_id;
    private String title;
    private String type; //paper or patent

    private List<ResearchAuthors> authors;

    private Date publish_date;
    private String status;
    private String file_path;
    private String description;
}