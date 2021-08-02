package dev.pace.warzonecannonfix;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class WarzoneCannonFix extends JavaPlugin implements Listener {

    public FileConfiguration config;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        config = this.getConfig();
        config.options().copyDefaults(true);
        config.addDefault("consolenotify", true);
        this.saveConfig();
    }

    @EventHandler
    public void onFallingBlock(final EntityChangeBlockEvent event) {
        final Faction faction = Board.getInstance().getFactionAt(new FLocation(event.getBlock()));
        if (faction.isWarZone() || faction.isSafeZone()) {
            event.getBlock().setType(Material.AIR);
            event.setCancelled(true);
            if (!config.getBoolean("consolenotify")) return;
            System.out.println("[WarzoneCannonFix] A sand has been removed in warzone or safe zone, is someone trying to grief warzone with cannons?");
        }
    }
}


