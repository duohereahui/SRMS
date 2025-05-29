package com.chht.srms.domain.achievements;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class AchievementsOutputs {

    //专利部分

    private String patent_number;

    //成果转化
    private Double funds;	//经费
    private String at_type;   //合同类别
    private Date sign_date;   //签订日期
    private String resource;   //项目来源
    private Date  start_date;   //开始日期
    private Date  end_date;    //终止日期

    //成果获奖
    private String categories;

    private String proj_id;
    private String proj_name;           //如果存在则直接关联查询

    private Date ach_date;
    private String proj_level;

    //论文部分
    private String p_name;
    private String paper_type; //CA or JA
    private Date public_date;
    private Integer impact_factor;
    private String doi;

    //著作部分
    private String book_type;
    private String book_num;

    //学术会议部分
    private String confs_num;
    private String confs_type;
    private Date confs_date;

    //公共部分
    private Long ach_id;
    private String title;

    private Long leader_id;
    private String leader_name;

    private Long lab_id;
    private String lab_name;

    private String description;
    private String type; //patents, ach_trans, ach_awards, papers, books, confs
    private Long year;

    private List<AchievementsAuthors> authors;

}