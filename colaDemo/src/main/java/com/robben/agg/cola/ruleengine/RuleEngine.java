package com.robben.agg.cola.ruleengine;

import com.robben.agg.cola.ruleengine.dto.DiscountContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RuleEngine {
    private List<RuleApi> ruleApis = new ArrayList<>();

    public RuleEngine addRule(RuleApi ruleApi) {
        this.ruleApis.add(ruleApi);
        return this;
    }

    public void fire(DiscountContext context) {
        for (RuleApi ruleApi : ruleApis) {
            if (ruleApi.evaluate(context)) {
                ruleApi.execute(context);
                break; // 只应用第一个匹配的规则（也可改为全部执行）
            }
        }
    }

}
