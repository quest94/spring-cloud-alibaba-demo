package com.quest94.demo.sca.common.util;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.quest94.demo.sca.common.exception.FlowRegulateException;

import java.util.Arrays;
import java.util.function.Supplier;

public class FlowRegulateUtils {

    public static FlowRule initFlowQpsRule(String resourceName, double count) {
        FlowRule rule = new FlowRule();
        rule.setResource(resourceName);
        rule.setCount(count);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        return rule;
    }

    public static void loadRules(FlowRule... rules) {
        FlowRuleManager.loadRules(Arrays.asList(rules));
    }

    public static <T> T runInFlowRegulate(String resourceName, Supplier<T> supplier) {
        try (Entry entry = SphU.entry(resourceName)) {
            return supplier.get();
        } catch (BlockException e) {
            throw new FlowRegulateException(resourceName);
        }
    }

    public static class FlowRule extends com.alibaba.csp.sentinel.slots.block.flow.FlowRule {
    }

}
