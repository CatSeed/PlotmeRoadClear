package cc.baka9.plotmeroadclear;

import com.worldcretornica.plotme.PlotManager;
import com.worldcretornica.plotme.PlotMapInfo;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;

public class PlotmeRoadClear extends JavaPlugin {
    @Override
    public void onEnable(){

    }

    private BukkitTask bukkitTask;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (args.length > 0 && "start".equalsIgnoreCase(args[0])) {
            if (bukkitTask == null) {
                bukkitTask = getServer().getScheduler().runTaskTimer(this, new Runnable() {
                    @Override
                    public void run(){
                        for (World world : Bukkit.getWorlds()) {
                            PlotMapInfo plotMapInfo = PlotManager.getMap(world);
                            if (plotMapInfo != null) {
                                RoadClear roadClear = new RoadClear(plotMapInfo);
                                for (Chunk chunk : world.getLoadedChunks()) {
                                    roadClear.populate(world, chunk);
                                }
                            }
                        }

                    }
                }, 0L, 20L * 10);
                sender.sendMessage("开启每10秒清理道路任务...");
            } else {
                sender.sendMessage("已经正在运行清理道路任务...");
            }
            return true;
        }
        if (args.length > 0 && "stop".equalsIgnoreCase(args[0])) {
            if (bukkitTask != null) {
                bukkitTask.cancel();
                bukkitTask = null;
                sender.sendMessage("停止清理道路任务...");
            } else {
                sender.sendMessage("没有正在运行的清理道路任务...");
            }
            return true;
        }
        if (args.length > 0 && "run".equalsIgnoreCase(args[0])) {
            for (World world : Bukkit.getWorlds()) {
                PlotMapInfo plotMapInfo = PlotManager.getMap(world);
                if (plotMapInfo != null) {
                    RoadClear roadClear = new RoadClear(plotMapInfo);
                    for (Chunk chunk : world.getLoadedChunks()) {
                        roadClear.populate(world, chunk);
                    }
                }
            }
            sender.sendMessage("已清理道路...");
            return true;
        }
        return false;
    }
}
