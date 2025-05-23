package com.chht.srms.domain.resourse;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class Funds {
    private Integer fund_id;
    private Integer project_id;
    private BigDecimal amount;
    private String usage_description;
    private Date transaction_date;
}