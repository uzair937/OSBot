import org.osbot.rs07.api.NPCS;
import org.osbot.rs07.api.Prayer;
import org.osbot.rs07.api.Widgets;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.api.ui.PrayerButton;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;

import org.osbot.rs07.script.ScriptManifest;

import java.awt.*;

@ScriptManifest(name = "NMZ Bot", author = "Uzair", version = 1.0, info = "Bot will start a dream, flick prayer and hopefully overload on time.", logo = "")

public final class NMZ extends Script {

    private Player player;
    private boolean setup = false;
    @Override

    public void onStart() {
        getInventory(); // returns the Inventory instance
        Prayer prayer = new Prayer();
        player = myPlayer(); // returns the Player instance
        getWalking(); // returns the Walking instance
        getPlayers(); // returns an instance of Players
        getNpcs(); // returns an instance of NPCS
        getGroundItems(); // returns an instance of GroundItems
        getObjects(); // returns an instance of Objects
        //Code here will execute before the loop is started
        NPC npc = getNpcs().closest("Dominic Onion");
        if (npc.exists() && !player.isInteracting(npc)) {
            npc.interact("Dream");
        }
    }



    @Override

    public void onExit() {

        //Code here will execute after the script ends

    }

    @Override

    public final int onLoop() throws InterruptedException {
        if (!setup) {
            setDream();
        } else {
            flickPrayer();
            if (getSkills().getDynamic(Skill.STRENGTH) <= getSkills().getStatic(Skill.STRENGTH)) {
                if (getInventory().contains("Overload")) {
                    getInventory().interact("Drink", "Overload");
                }
            }
        }
        return random(50, 150); //The amount of time in milliseconds before the loop starts over
    }

    @Override

    public void onPaint(Graphics2D g) {

        //This is where you will put your code for paint(s)

    }

    private void setDream() {
        //Area nmzArea = new Area(2601,3113,2609,3121);
            getDialogues().selectOption("Previous: Customisable Rumble (hard)");
            getDialogues().clickContinue();
            getDialogues().selectOption("Yes");
            getDialogues().clickContinue();
            enterDream();
    }

    private void enterDream() {
        Entity emptyVial = getObjects().closest("Empty vial");
        if (emptyVial.exists()) {
            setup = false;
        } else {
            Entity vial = getObjects().closest("Potion");
            if (vial.exists()) {
                vial.interact("Drink");
            }
            RS2Widget accept = getWidgets().getWidgetContainingText("Accept");
            if (accept != null) {
                accept.interact("Accept");
                setup = true;
            }
        }
    }

    private void flickPrayer() {
        if (player.isUnderAttack() && getSkills().getDynamic(Skill.PRAYER) > 0) {
            prayer.set(PrayerButton.PROTECT_FROM_MELEE, true);
        }
    }

}