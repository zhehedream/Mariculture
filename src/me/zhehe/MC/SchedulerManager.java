/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.zhehe.MC;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

/**
 *
 * @author zhiqiang.hao
 */
public class SchedulerManager {
    private BukkitTask task;
    private Plugin plugin;
    private Random random;
    
    public SchedulerManager(Plugin plugin, Random random) {
        this.plugin = plugin;
        this.random = random;
    }
    
    public void startTask() {
        task = Bukkit.getServer().getScheduler().runTaskTimer(plugin, () -> {
            EntityManager.doSecondEvent(random);
        }, 1L, 20L);
    }
}
