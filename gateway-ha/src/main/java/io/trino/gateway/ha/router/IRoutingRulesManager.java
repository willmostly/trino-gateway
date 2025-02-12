package io.trino.gateway.ha.router;

import io.trino.gateway.ha.persistence.dao.RoutingRule;

import java.util.List;

public interface IRoutingRulesManager
{
    List<RoutingRule> getRoutingRules();

    List<RoutingRule> updateRoutingRule(RoutingRule routingRule);

    void deleteRoutingRule(String name);

    void createRoutingRule(RoutingRule routingRule);
}
