package com.robben.agg.cola.ruleengine;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RuleEngine {
    private List<Rule> rules = new ArrayList<>();

    public RuleEngine addRule(Rule rule) {
        this.rules.add(rule);
        return this;
    }

    public void fire(DiscountContext context) {
        for (Rule rule : rules) {
            if (rule.evaluate(context)) {
                rule.execute(context);
                break; // 只应用第一个匹配的规则（也可改为全部执行）
            }
        }
    }

}
