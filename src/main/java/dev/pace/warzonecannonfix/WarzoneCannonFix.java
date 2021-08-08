package dev.pace.warzonecannonfix;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import dev.pace.warzonecannonfix.metrics.Metrics;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class WarzoneCannonFix extends JavaPlugin implements Listener {

    /**
     * A lot of faction servers have had an issue where people shoot sand from their territory into the warzone.
     * And you cannot patch it with WorldGuard. This plugin is a simple fix for that.
     */

    public FileConfiguration config;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        config = this.getConfig();
        config.options().copyDefaults(true);
        config.addDefault("consolenotify", true);
        this.saveConfig();
        // https://bstats.org/plugin/bukkit/WarzoneCannonFix/12366
        new Metrics(this, 12366);
    }

    @EventHandler
    public void onFallingBlock(final EntityChangeBlockEvent event) {
        final Faction faction = Board.getInstance().getFactionAt(new FLocation(event.getBlock()));
        // If territory is WarZone or SafeZone and sand is falling, remove it.
        if (faction.isWarZone() || faction.isSafeZone()) {
            event.getBlock().setType(Material.AIR);
            event.setCancelled(true);
            // Option to disable the console notifier.
            if (!config.getBoolean("consolenotify")) return;
            System.out.println("[WarzoneCannonFix] A sand has been removed in warzone or safezone, is someone trying to grief warzone with cannons?");
        }
    }
}


