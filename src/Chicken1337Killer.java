import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import java.awt.*;
import java.util.ArrayList;

@ScriptManifest(name = "Chicken 1337 Leet Killer", author = "Uzair", version = 1.0, info = "Kills chickens, eating Trout for food and banking.", logo = "")

public final class Chicken1337Killer extends Script {

    private ArrayList<Position> arrayList = new ArrayList<>();
    private Position finalPosition = new Position(3177, 3316, 0);

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


    }

    @Override

    public void onExit() {
        //Code here will execute after the script ends
        log("The script has ended.");
    }

    @Override

    public final int onLoop() throws InterruptedException {
        if (myPlayer().getInteracting() == null) {
            handler();
        }
        return random(200, 600); //The amount of time in milliseconds before the loop starts over
    }

    private void handler() {
        if (myPlayer().getHealthPercent() <= 30 && getInventory().contains("Trout")) {
            getInventory().getItem("Trout").interact("Eat");
            Sleep.sleepUntil(() -> myPlayer().isAnimating(), 500);
        } else {
            attackChickens();
        }
    }

    private void attackChickens() {
        NPC npc = getNpcs().closest("Chicken");
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

    }

}