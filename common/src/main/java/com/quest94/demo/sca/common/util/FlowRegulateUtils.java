package com.quest94.demo.sca.common.util;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.quest94.demo.sca.common.exception.FlowRegulateException;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
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
        // 手动修改规则（硬编码方式）一般仅用于测试和演示
        FlowRuleManager.loadRules(Arrays.asList(rules));
    }

    public static <T> T within(String resourceName, Supplier<T> supplier) {
        return within(resourceName, supplier, null, null);
    }

    public static <T> T within(String resourceName, Supplier<T> supplier,
                               Function<BlockException, T> blockHandler,
                               Function<Throwable, T> fallbackHandler) {
        try (Entry entry = SphU.entry(resourceName)) {
            return supplier.get();
        } catch (com.alibaba.csp.sentinel.slots.block.BlockException e) {
            if (Objects.nonNull(blockHandler)) {
                return blockHandler.apply(new BlockException(e));
            }
            throw new FlowRegulateException(resourceName);
        } catch (Throwable e) {
            if (Objects.nonNull(blockHandler)) {
                return fallbackHandler.apply(e);
            }
            throw e;
        }
    }

    public static class BlockException extends com.alibaba.csp.sentinel.slots.block.BlockException {
        private final com.alibaba.csp.sentinel.slots.block.BlockException cause;

        public BlockException(com.alibaba.csp.sentinel.slots.block.BlockException cause) {
            super(cause.getMessage(), cause);
            this.cause = cause;
        }

        @Override
        public Throwable fillInStackTrace() {
            return cause.fillInStackTrace();
        }

        @Override
        public String getRuleLimitApp() {
            return cause.getRuleLimitApp();
        }

        @Override
        public void setRuleLimitApp(String ruleLimitApp) {
            cause.setRuleLimitApp(ruleLimitApp);
        }

        @Override
        public RuntimeException toRuntimeException() {
            return cause.toRuntimeException();
        }

        @Override
        public AbstractRule getRule() {
            return new AbstractRule(cause.getRule());
        }
    }

    public static class AbstractRule extends com.alibaba.csp.sentinel.slots.block.AbstractRule {
        private final com.alibaba.csp.sentinel.slots.block.AbstractRule rule;

        public AbstractRule(com.alibaba.csp.sentinel.slots.block.AbstractRule rule) {
            this.rule = rule;
        }

        @Override
        public Long getId() {
            return rule.getId();
        }

        @Override
        public com.alibaba.csp.sentinel.slots.block.AbstractRule setId(Long id) {
            return rule.setId(id);
        }

        @Override
        public String getResource() {
            return rule.getResource();
        }

        @Override
        public com.alibaba.csp.sentinel.slots.block.AbstractRule setResource(String resource) {
            return rule.setResource(resource);
        }

        @Override
        public String getLimitApp() {
            return rule.getLimitApp();
        }

        @Override
        public com.alibaba.csp.sentinel.slots.block.AbstractRule setLimitApp(String limitApp) {
            return rule.setLimitApp(limitApp);
        }

        @Override
        public boolean equals(Object o) {
            return rule.equals(o);
        }

        @Override
        public <T extends com.alibaba.csp.sentinel.slots.block.AbstractRule> T as(Class<T> clazz) {
            return rule.as(clazz);
        }

        @Override
        public int hashCode() {
            return rule.hashCode();
        }
    }

    public static class FlowRule extends com.alibaba.csp.sentinel.slots.block.flow.FlowRule {
    }

}
