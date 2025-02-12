package io.trino.gateway.ha.persistence.dao;

import java.util.List;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import static java.util.Objects.requireNonNull;

/**
 * RoutingRules
 *
 * @param name name of the routing rule
 * @param description description of the routing rule. Defaults to an empty string if not provided, indicating the user intends it to be blank.
 * @param priority priority of the routing rule. Higher number represents higher priority. If two rules have same priority then order of execution is not guaranteed.
 * @param actions actions of the routing rule
 * @param condition condition of the routing rule
 * @param routingRuleEngine the engine used for rule evaluation
 */

public record RoutingRule(
        @ColumnName("name") String name,
        @ColumnName("description") String description,
        @ColumnName("priority") Integer priority,
        @ColumnName("condition") String condition,
        @ColumnName("actions") List<String> actions,
        @ColumnName("routingRuleType") RoutingRuleEngine routingRuleEngine)
{
    public RoutingRule
    {
        requireNonNull(name);
        requireNonNull(condition);
        requireNonNull(actions);
    }
}
