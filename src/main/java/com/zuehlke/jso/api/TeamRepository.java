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

    @SqlUpdate("UPDATE Team SET name = :name WHERE id = :id")
    void updateTeamOnly(@BindBean Team team);

    @SqlUpdate("DELETE FROM Member WHERE fk_team = :teamId")
    void deleteTeamMembers(@Bind("teamId") long teamId);

    @Transaction
    default void updateTeam(Team team) {
        updateTeamOnly(team);
        deleteTeamMembers(team.getId());
        insertTeamMembers(team.getId(), team.getMembers());
    }

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
