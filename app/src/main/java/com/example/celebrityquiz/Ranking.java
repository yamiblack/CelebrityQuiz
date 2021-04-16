package com.example.celebrityquiz;

public class Ranking implements Comparable<Ranking> {
    String rank;
    String email;
    String gameType;
    String level;
    String score;
    String time;

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        rank = rank;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int compareTo(Ranking ranking) {
        if (Integer.parseInt(this.score) < Integer.parseInt(ranking.getScore())) {
            return 1;
        } else if (Integer.parseInt(this.score) == Integer.parseInt(ranking.getScore())) {
            if (Integer.parseInt(this.time) < Integer.parseInt(ranking.getTime())) {
                return -1;
            } else {
                return 1;
            }
        } else if (Integer.parseInt(this.score) > Integer.parseInt(ranking.getScore())) {
            return -1;
        }
        return 0;
    }
}
