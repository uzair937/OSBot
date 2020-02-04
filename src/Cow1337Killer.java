import org.osbot.rs07.api.Chatbox;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.EquipmentSlot;
import org.osbot.rs07.api.ui.Skill;

import java.util.List;

import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.api.ui.Tab;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@ScriptManifest(name = "Cow 1337 Leet Killer", author = "Uzair", version = 5.0, info = "Kills cows and calves, drinks wine, eats trout and eats tuna for food and broken banking.", logo = "")

public final class Cow1337Killer extends Script {

    private ArrayList<Position> positionList = new ArrayList<>();
    private Position finalPosition = new Position(3177, 3316, 0);
    private AntiBan antiBan;
    private long timeBegan;
    private long timeRan;
    private int startXP;
    private int currentXP;
    private int xpGained;
    private int xpPerHour;
    private Random random;

    @Override

    public void onStart() {
        //Code here will execute before the loop is started

        log("The script has started.");
        getInventory(); //returns the player inventory
        getNpcs(); // returns an instance of NPCS
        getGroundItems(); // returns an instance of GroundItems
        getObjects(); // returns an instance of Objects
        setupList(); // sets up walking positions list

        random = new Random();
        timeBegan = System.currentTimeMillis();
        startXP = skills.getExperience(Skill.HITPOINTS) * 3;
        antiBan = new AntiBan();
    }

    @Override

    public void onExit() {
        //Code here will execute after the script ends
        log("The script has ended.");
        log("Total XP gained: " + currentXP);
        log("Total time ran: " + timeRan);
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

    private void setupList() {
        positionList.add(new Position(3092, 3245, 0));
        positionList.add(new Position(3102, 3255, 0));
        positionList.add(new Position(3103, 3272, 0));
        positionList.add(new Position(3109, 3284, 0));
        positionList.add(new Position(3116, 3293, 0));
        positionList.add(new Position(3124, 3300, 0));
        positionList.add(new Position(3137, 3307, 0));
        positionList.add(new Position(3147, 3310, 0));
        positionList.add(new Position(3160, 3312, 0));
        positionList.add(new Position(3176, 3315, 0));
    }

    private void handler() {
        if (myPlayer().getHealthPercent() <= 30) {
            if (getInventory().contains("Trout")) {
                getInventory().getItem("Trout").interact("Eat");
                Sleep.sleepUntil(() -> myPlayer().isAnimating(), 500);
            } else if (getInventory().contains("Jug of wine")) {
                if (myPlayer().getHealthPercent() <= 30) {
                    getInventory().getItem("Jug of wine").interact("Drink");
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println(e);
                    }
                }
                Sleep.sleepUntil(() -> myPlayer().isAnimating(), 500);
            } else if (getInventory().contains("Tuna")) {
                getInventory().getItem("Tuna").interact("Eat");
                Sleep.sleepUntil(() -> myPlayer().isAnimating(), 500);
            } else if (!getInventory().contains("Trout") || !getInventory().contains("Jug of wine") || !getInventory().contains("Tuna")) {
                getLogoutTab().logOut();
                stop();
                /*try {
                    goBank();
                } catch (InterruptedException e) {
                    System.out.println(e);
                }*/
            }
        } else {
            attackCows();
        }
    }

    private void attackCows() {
        if (!getEquipment().isWearingItemThatContains(EquipmentSlot.ARROWS,"arrow")) {
            getLogoutTab().logOut();
            stop();
        }
        NPC npc = getNpcs().closest("Cow", "Cow calf");
        int hp = npc.getHealthPercent();
        if (!myPlayer().isUnderAttack() && hp == 100) {
            if (npc == null) {
                walker();
            } else if (npc != null && npc.interact("Attack") && !myPlayer().isAnimating()) {
                Sleep.sleepUntil(() -> myPlayer().isAnimating(), 1000);
            }
        }
    }

    private void goBank() throws InterruptedException {
        getWalking().webWalk(Banks.DRAYNOR);
        if (Banks.DRAYNOR.contains(myPosition())) {
            bank.open();
            if (getBank().contains("Trout") || getBank().contains("Jug of wine") || getBank().contains("Tuna")) {
                if (getInventory().isEmpty()) {
                    if (getBank().contains("Trout")) {
                        bank.withdraw("Trout", 28);
                    } else if (getBank().contains("Tuna")) {
                        bank.withdraw("Tuna", 28);
                    }
                } else if (!getInventory().contains("Jug")) {
                    bank.close();
                    walker();
                }
            } else if (getInventory().contains("Jug")) {
                bank.deposit("Jug", 28);
                bank.withdraw("Jug of wine", 28);
                bank.close();
                walker();
            } else {
                bank.close();
                getLogoutTab().logOut();
            }
        }
    }

    private void walker() {
        getWalking().walkPath(positionList);
        Entity gate = getObjects().closest(883);
        Entity openGate = getObjects().closest(23918);
        if (gate != null) {
            gate.interact("Open");
        } else if (openGate != null) {
            getWalking().walk(finalPosition);
            openGate.interact("Close");
            handler();
        }
    }

    @Override

    public void onPaint(Graphics2D g) {
        //This is where you will put your code for paint(s)
        timeRan = System.currentTimeMillis() - this.timeBegan;
        xpGained = currentXP - startXP;
        xpPerHour = (int) (xpGained / ((System.currentTimeMillis() - this.timeBegan) / 3600000.0D));

        if (g.getColor() != Color.GREEN) {
            g.setColor(Color.GREEN);
        }
        g.drawString("XP Gained: " + xpGained + " (XP/hr: " + xpPerHour + ")", 25, 50);
        g.drawString("Time Running " + timeString(timeRan), 25, 35);
        g.drawString("AntiBan executions: " + antiBan.antiCount, 25, 65);
        g.drawString("Cow 1337 Killer V5 by Uzair (NEW ANTIBAN FEATURES!!!)", 25, 95);
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