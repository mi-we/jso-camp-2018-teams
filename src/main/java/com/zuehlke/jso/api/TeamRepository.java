package com.zuehlke.jso.api;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.*;
import org.jdbi.v3.sqlobject.transaction.Transaction;

import java.util.List;
import java.util.Optional;

public interface TeamRepository {

    @SqlUpdate("INSERT INTO Team (name) VALUES (:name)")
    @GetGeneratedKeys("id")
    long insertTeamOnly(@BindBean Team team);

    @SqlBatch("INSERT INTO Member (name, fk_team) VALUES (:name, :teamId)")
    void insertTeamMembers(@Bind("teamId") long teamId, @Bind("name") List<String> members);

    @Transaction
    default long insertTeam(Team team) {
        long teamId = insertTeamOnly(team);
        insertTeamMembers(teamId, team.getMembers());
        return teamId;
    }

    @SqlQuery("SELECT t.id t_id, t.name t_name, m.id m_id, m.name m_name " +
            "FROM Team t LEFT JOIN Member m ON t.id = m.fk_team")
    @RegisterConstructorMapper(value = Team.class, prefix = "t")
    @UseRowReducer(TeamRowReducer.class)
    List<Team> selectAllTeams();

    @SqlQuery("SELECT t.id t_id, t.name t_name, m.id m_id, m.name m_name " +
            "FROM Team t LEFT JOIN Member m ON t.id = m.fk_team " +
            "WHERE t.id = ?")
    @RegisterConstructorMapper(value = Team.class, prefix = "t")
    @UseRowReducer(TeamRowReducer.class)
    Optional<Team> selectTeam(long id);

}
