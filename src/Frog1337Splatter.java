import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@ScriptManifest(name = "Frog 1337 Splatter", author = "Uzair", version = 2.0, info = "Kills froggy bois and eats tuna.", logo = "")

public final class Frog1337Splatter extends Script {

    //private ArrayList<Position> positionList = new ArrayList<>();
    //private Position finalPosition = new Position(3177,3316,0);

    private long timeBegan;
    private long timeRan;
    private int startXP;
    private int currentXP;
    private int xpGained;
    private int xpPerHour;
    private Random random;
    private AntiBan antiBan;

    @Override

    public void onStart() {
        //Code here will execute before the loop is started

        log("The script has started.");
        getPlayers(); // returns an instance of Players
        getInventory(); //returns the player inventory
        getNpcs(); // returns an instance of NPCS
        getGroundItems(); // returns an instance of GroundItems
        getObjects(); // returns an instance of Objects
        getBank();

        timeBegan = System.currentTimeMillis();
        startXP = skills.getExperience(Skill.HITPOINTS) * 3;
        antiBan = new AntiBan();
    }

    @Override

    public void onExit() {
        //Code here will execute after the script ends
        log("The script has ended.");
    }

    @Override

    public final int onLoop() throws InterruptedException {
        currentXP = skills.getExperience(Skill.HITPOINTS) * 3;

        int randVal = random.nextInt(100000);

        if (myPlayer().getInteracting() == null || getDialogues() != null) {
            handler();
        }

        if (randVal >= 99000 || randVal <= 1000) {
            antiBan.execute();
        }

        return random(400, 600); //The amount of time in milliseconds before the loop starts over
    }

    private void handler() {
        if (myPlayer().getHealthPercent() <= 30) {
            if (getInventory().contains("Tuna")) {
                getInventory().getItem("Tuna").interact("Eat");
                Sleep.sleepUntil(() -> myPlayer().isAnimating(), 500);
            } else if (getInventory().contains("Jug of wine")) {
                if (myPlayer().getHealthPercent() <= 30) {
                    getInventory().getItem("Jug of wine").interact("Drink");
                    Sleep.sleepUntil(() -> myPlayer().isAnimating(), 500);
                }
            } else if (getInventory().isEmpty() || !getInventory().contains("Jug of wine") || !getInventory().contains("Tuna"))
                logoutTab.logOut();
        } else {
            attackFrogs();
        }
    }

    private void attackFrogs() {
        NPC npc = getNpcs().closest("Frog", "Big frog");
        int hp = npc.getHealthPercent();
        if (!myPlayer().isUnderAttack() && hp == 100) {
            if (npc != null && npc.interact("Attack")) {
                Sleep.sleepUntil(() -> myPlayer().isAnimating(), 1000);
            }
        }
    }

    @Override

    public void onPaint(Graphics2D g) {
        //This is where you will put your code for paint(s)
        timeRan = System.currentTimeMillis() - this.timeBegan;
        xpGained = currentXP - startXP;
        xpPerHour = (int) (xpGained / ((System.currentTimeMillis() - this.timeBegan) / 3600000.0D));

        if (g.getColor() != Color.RED) {
            g.setColor(Color.RED);
        }
        g.drawString("XP Gained: " + xpGained + " (XP/hr: " + xpPerHour + ")", 25, 50);
        g.drawString("Time Running: " + timeString(timeRan), 25, 35);
        g.drawString("AntiBan executions: " + antiBan.antiCount, 25, 65);
        g.drawString("Frog 1337 Splatter by Uzair", 25, 65);
    }

    private String timeString(long duration) {
        String res = "";
        long days = TimeUnit.MILLISECONDS.toDays(duration);
        long hours = TimeUnit.MILLISECONDS.toHours(duration)
                - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duration));
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration)
                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                .toHours(duration));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                .toMinutes(duration));
        if (days == 0) {
            res = (hours + ":" + minutes + ":" + seconds);
        } else {
            res = (days + ":" + hours + ":" + minutes + ":" + seconds);
        }
        return res;
    }
}