package com.zuehlke.jso.api;

import org.jdbi.v3.core.result.LinkedHashMapRowReducer;
import org.jdbi.v3.core.result.RowView;

import java.util.Map;

public class TeamRowReducer implements LinkedHashMapRowReducer<Long, Team> {

    public TeamRowReducer() {
    }

    @Override
    public void accumulate(Map<Long, Team> container, RowView rowView) {
        Team team = container.computeIfAbsent(getTeamId(rowView),
                id -> rowView.getRow(Team.class));

        if (getMemberId(rowView) != null) {
            team.getMembers().add(getMemberName(rowView));
        }
    }

    private Long getTeamId(RowView rowView) {
        return rowView.getColumn("t_id", Long.class);
    }

    private Long getMemberId(RowView rowView) {
        return rowView.getColumn("m_id", Long.class);
    }

    private String getMemberName(RowView rowView) {
        return rowView.getColumn("m_name", String.class);
    }
}
