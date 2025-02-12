package io.trino.gateway.ha.persistence.dao;

import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface RoutingRulesDao
{
    @SqlQuery("SELECT * FROM routing_rules")
    List<RoutingRule> getAll();

    @SqlUpdate("""
            INSERT INTO routing_rules (name, description, priority, condition, actions, routingRuleEngine)
            VALUES (:name, :description, :priority, :condition, :actions, :routingRuleEngine)
            """)
    void create(String name, String description, Integer priority, String condition, List<String> actions, RoutingRuleEngine routingRuleEngine);

    @SqlUpdate("""
            UPDATE routing_rules
            SET description = :description, priority = :priority, condition = :condition, actions = :actions, routingRuleEngine = :routingRuleEngine
            WHERE name = :name
            """)
    void update(String name, String description, Integer priority, String condition, List<String> actions, RoutingRuleEngine routingRuleEngine);

    @SqlUpdate("""
            DELETE FROM routing_rules
            WHERE name = :name
            """
    )
    void delete(String name);
}
