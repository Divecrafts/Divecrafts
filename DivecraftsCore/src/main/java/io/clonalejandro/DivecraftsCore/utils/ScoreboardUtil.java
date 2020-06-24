package io.clonalejandro.DivecraftsCore.utils;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public class ScoreboardUtil {

    @Getter private final Scoreboard scoreboard;

    private final Objective objective;
    private final HashMap<Integer, String> scores;
    @Getter @Setter private boolean reset;

    /**
     * Creates new Scoreboard
     * @param displayName The display name
     * @param score The data name
     */
    public ScoreboardUtil(String displayName, String score) {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = this.scoreboard.registerNewObjective(score, "dummy");
        this.objective.setDisplayName(Utils.colorize(displayName));
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.scores = new HashMap<>();
    }

    /**
     * Sets new name
     * @param name
     */
    public void setName(String name) {
        this.objective.setDisplayName(Utils.colorize(name));
    }

    /**
     * Adds text in a location (score)
     * @param score The location
     * @param text
     */
    public void text(int score, String text) {
        if (scores.containsKey(score)) {
            String textX = scores.get(score);
            if (!(text.equalsIgnoreCase(textX))) {
                scoreboard.resetScores(textX);
                objective.getScore(text).setScore(score);
                scores.put(score, text);
            }
        } else {
            scores.put(score, text);
            objective.getScore(text).setScore(score);
        }
    }

    /**
     * Registers a new Team
     * @param name The team name
     * @param prefix The prefix for the team
     */
    public void team(String name, String prefix) {
        Team team = getTeam(name);
        if (team == null) {
            scoreboard.registerNewTeam(name);
            scoreboard.getTeam(name).setPrefix(Utils.colorize(prefix));
        }
    }

    /**
     * Resets all the Score
     */
    public void reset() {
        if (!isReset()) {
            scores.keySet().forEach(s -> getScoreboard().resetScores(scores.get(s)));
            scores.clear();
            setReset(true);
        }
    }

    /**
     * Builds a Scoreboard for the player
     * @param player
     */
    public void build(Player player) {
        player.setScoreboard(scoreboard);
    }

    /**
     * Gets A team
     * @param name
     * @return A team or null
     */
    public Team getTeam(String name) {
        return scoreboard.getTeam(name);
    }
}
