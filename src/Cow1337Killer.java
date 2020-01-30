import org.osbot.rs07.api.Chatbox;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
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
    private long timeBegan;
    private long timeRan;
    private int startXP;
    private int currentXP;
    private int xpGained;
    private int xpPerHour;
    private Random random;
    private int antiCount;

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
            antiBan();
        }

        return random(400, 600); //The amount of time in milliseconds before the loop starts over
    }

    private void antiBan() {
        antiCount++;
        Random random2 = new Random();

        int randomValue = random2.nextInt(100);

        switch (randomValue) {
            default:
                break;
            case 1:
                getCamera().moveYaw(random(12, 14) + this.random.nextInt(random(12, 14) + this.random.nextInt(random(30, 35))));
                break;
            case 22:
            case 24:
            case 27:
            case 52:
                getCamera().movePitch(random2.nextInt(random(200, 400)));
                break;
            case 38:
            case 99:
            case 90:
            case 0:
                try {
                    sleep(random(250, 500));
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
                break;
            case 6:
            case 29:
            case 33:
            case 91:
            case 42:
            case 77:
            case 21:
                getCamera().movePitch(random2.nextInt(random(330, 660)));
                getCamera().moveYaw(random(18, 22) + this.random.nextInt(random(18, 22) + this.random.nextInt(random(40, 45))));
                break;
            case 9:
            case 10:
            case 53:
            case 71:
            case 82:
            case 73:
            case 11:
                try {
                    randomRightClick();
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
                break;
            case 43:
            case 30:
                if (getTabs().isOpen(Tab.MAGIC)) {
                    getTabs().open(Tab.QUEST);
                } else {
                    getTabs().open(Tab.MAGIC);
                }
                break;
            case 69:
                if (getTabs().isOpen(Tab.SKILLS)) {
                    getTabs().open(Tab.EQUIPMENT);
                } else {
                    getTabs().open(Tab.SKILLS);
                }
                break;
            case 14:
            case 80:
            case 56:
                if (getTabs().isOpen(Tab.FRIENDS)) {
                    getTabs().open(Tab.CLANCHAT);
                } else {
                    getTabs().open(Tab.FRIENDS);
                }
                break;
            case 66:
                if (getTabs().isOpen(Tab.ATTACK)) {
                    getTabs().open(Tab.PRAYER);
                } else {
                    getTabs().open(Tab.ATTACK);
                }
                break;
        }
    }

    private void randomRightClick() throws InterruptedException {
        List<RS2Object> visibleObjs = getObjects().getAll().stream().filter(o -> o.isVisible()).collect(Collectors.toList());
        // select a random object
        int index = random(0, visibleObjs.size() - 1);
        RS2Object obj = visibleObjs.get(index);
        if (obj != null) {
            // hover the object and right click
            sleep(random(50, 100));
            obj.hover();
            getMouse().click(true);
            // while the menu is still open, move the mouse to a new location
            while (getMenuAPI().isOpen()) {
                sleep(random(50, 100));
                moveMouseRandomly(random(0, 2));
            }
        }
    }

    private void moveMouseRandomly(int numberOfPositions) {
        Point[] pointArray = new Point[numberOfPositions];
        for (int i = 0; i < pointArray.length; i++) {
            pointArray[i] = new Point(-10 + this.random.nextInt(850), -10 + this.random.nextInt(550));
        }
        for (int i = 0; i < pointArray.length; i++) {
            getMouse().move(pointArray[i].x, pointArray[i].y);
            try {
                sleep(random(100, 300));
            } catch (InterruptedException e) {
            }
        }
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
        NPC npc = getNpcs().closest("Cow", "Cow calf");
        int hp = npc.getHealthPercent();
        if (!myPlayer().isAnimating() && hp == 100) {
            if (npc == null) {
                walker();
            } else if (npc != null && npc.interact("Attack")) {
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
        g.drawString("AntiBan executions: " + antiCount, 25, 65);
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