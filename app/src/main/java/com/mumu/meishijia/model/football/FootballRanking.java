package com.mumu.meishijia.model.football;

/**
 * 足球球队排名
 * Created by Administrator on 2017/6/7.
 */

public class FootballRanking {
    private int team_id;
    private int ranking;
    private String logo;
    private String name;
    private int plays;
    private int point;
    private int win;
    private int draw;
    private int lose;
    private int goal;
    private int conceded;
    private int goal_difference;

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlays() {
        return plays;
    }

    public void setPlays(int plays) {
        this.plays = plays;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public int getConceded() {
        return conceded;
    }

    public void setConceded(int conceded) {
        this.conceded = conceded;
    }

    public int getGoal_difference() {
        return goal_difference;
    }

    public void setGoal_difference(int goal_difference) {
        this.goal_difference = goal_difference;
    }
}
