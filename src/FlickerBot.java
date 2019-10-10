import org.osbot.rs07.api.Mouse;
import org.osbot.rs07.api.Prayer;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.api.ui.PrayerButton;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import java.awt.*;

@ScriptManifest(name = "Flicker Bot", author = "Uzair", version = 1.0, info = "Literally flicks melee prayer.", logo = "")

public final class FlickerBot extends Script {

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
    }



    @Override

    public void onExit() {

        //Code here will execute after the script ends

    }

    @Override

    public final int onLoop() throws InterruptedException {
        if (player.isUnderAttack()) {
            prayer.set(PrayerButton.PROTECT_FROM_MELEE, true);
            Thread.sleep(10);
            prayer.set(PrayerButton.PROTECT_FROM_MELEE, false);
        }
        return random(75,125);
    }

    @Override

    public void onPaint(Graphics2D g) {

        //This is where you will put your code for paint(s)

    }

    private void flickPrayer() {
        if (prayer.isActivated(PrayerButton.PROTECT_FROM_MELEE)) {
            prayer.set(PrayerButton.PROTECT_FROM_MELEE, false);
        } else {
            prayer.set(PrayerButton.PROTECT_FROM_MELEE, true);
        }
    }

}