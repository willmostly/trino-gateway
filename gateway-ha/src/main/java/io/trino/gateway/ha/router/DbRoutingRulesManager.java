package io.trino.gateway.ha.router;

import io.trino.gateway.ha.persistence.dao.RoutingRule;
import io.trino.gateway.ha.persistence.dao.RoutingRulesDao;

import java.util.List;

public class DbRoutingRulesManager
implements IRoutingRulesManager
{
    private final RoutingRulesDao routingRulesDao;

    public DbRoutingRulesManager(RoutingRulesDao routingRulesDao)
    {
        this.routingRulesDao = routingRulesDao;
    }

    @Override
    public List<RoutingRule> getRoutingRules()
    {
        List<RoutingRule> rules = routingRulesDao.getAll();
        return rules;
    }

    @Override
    public List<RoutingRule> updateRoutingRule(RoutingRule routingRule)
    {
        routingRulesDao.update(
                routingRule.name(),
                routingRule.description(),
                routingRule.priority(),
                routingRule.condition(),
                routingRule.actions(),
                routingRule.routingRuleEngine()); //

        return routingRulesDao.getAll();
    }

    @Override
    public void deleteRoutingRule(String name)
    {
        routingRulesDao.delete(name);
    }

    @Override
    public void createRoutingRule(RoutingRule routingRule)
    {
        routingRulesDao.create(
                routingRule.name(),
                routingRule.description(),
                routingRule.priority(),
                routingRule.condition(),
                routingRule.actions(),
                routingRule.routingRuleEngine());
    }
}
