package io.trino.gateway.ha.router;

import com.google.inject.Inject;
import io.trino.gateway.ha.config.HaGatewayConfiguration;
import io.trino.gateway.ha.config.RoutingRulesConfiguration;
import io.trino.gateway.ha.persistence.RecordAndAnnotatedConstructorMapper;
import io.trino.gateway.ha.persistence.dao.RoutingRule;
import io.trino.gateway.ha.persistence.dao.RoutingRulesDao;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.util.List;

public class ForwardingRoutingRulesManager
    implements IRoutingRulesManager
{
    IRoutingRulesManager delegate;

    @Inject
    ForwardingRoutingRulesManager(HaGatewayConfiguration haGatewayConfiguration)
    {
        RoutingRulesConfiguration routingRulesConfig = haGatewayConfiguration.getRoutingRules();
        delegate = switch (routingRulesConfig.getRulesType()) {
            case FILE -> new FileBasedRoutingRulesManager(haGatewayConfiguration);
            case DB -> {
                Jdbi jdbi = Jdbi.create(
                        haGatewayConfiguration.getDataStore().getJdbcUrl(),
                        haGatewayConfiguration.getDataStore().getUser(),
                        haGatewayConfiguration.getDataStore().getPassword())
                        .installPlugin(new SqlObjectPlugin())
                        .registerRowMapper(new RecordAndAnnotatedConstructorMapper());

                yield new DbRoutingRulesManager(jdbi.onDemand(RoutingRulesDao.class));
            }
            default -> throw new RuntimeException("No routing manager for " + routingRulesConfig.getRulesType());
        };
    }

    @Override
    public List<RoutingRule> getRoutingRules()
    {
        return delegate.getRoutingRules();
    }

    @Override
    public List<RoutingRule> updateRoutingRule(RoutingRule routingRule)
    {
        return delegate.updateRoutingRule(routingRule);
    }

    @Override
    public void deleteRoutingRule(String name)
    {
        delegate.deleteRoutingRule(name);
    }

    @Override
    public void createRoutingRule(RoutingRule routingRule)
    {
        delegate.createRoutingRule(routingRule);
    }
}
