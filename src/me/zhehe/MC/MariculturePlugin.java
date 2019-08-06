package me.zhehe.MC;


import com.bryankeiren.fjord.FjordChunkGenerator;
import com.google.gson.Gson;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import me.zhehe.Config.Config;
import me.zhehe.FishR.Fish;
import me.zhehe.FishR.Type;
import me.zhehe.FishR.Type.FishSpecial;
import me.zhehe.FishR.Type.FishType;
import me.zhehe.Util.Constant;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class MariculturePlugin extends JavaPlugin {
    
    EntityManager entityManager;
    Random random;
    RecipeManager recipeManager;
    SchedulerManager schedulerManager;
    EventManager eventManager;
    FjordChunkGenerator chunkGenerator;
    
    @Override
    public void onEnable() {
        Config.instance.initConfig();
        random = new Random();
        recipeManager = new RecipeManager(this);
        entityManager = new EntityManager(this, random);
        eventManager = new EventManager(this, random);
        chunkGenerator = new FjordChunkGenerator(this);
        schedulerManager = new SchedulerManager(this, random);
        recipeManager.registerTools();
        if(Config.instance.worldEnable) WorldManager.createWorld(chunkGenerator, random);
        
        schedulerManager.startTask();
        
        Tank.debug = false;
        
        this.getServer().getPluginManager().registerEvents(this.eventManager, this);
    }
    
    @Override
    public void onDisable() {
        
    }
    
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
	if (!(sender instanceof Player)) {
            sender.sendMessage("player only command");
            return true;
	}
	final Player player = (Player) sender;
        //Bukkit.getLogger().log(Level.INFO, command.getName());
	if (command.getName().equalsIgnoreCase("mariculture")) {
            //Bukkit.getLogger().log(Level.INFO, Arrays.toString(args));
            if(args.length > 0) {
                if(args[0].equals("temp")) {
                    if (!player.hasPermission("mariculture.command")) {
                        player.sendMessage("您无权使用该命令");
                        return true;
                    }
                    double temp = player.getWorld().getBlockAt(player.getLocation()).getTemperature();
                    player.sendMessage("当前位置温度：" + Double.toString(temp));
                    Set<Integer> set = new HashSet<>();
                    for(int i = 0; i < Constant.TEMPERATURE_LIST.length; i++) {
                        if(Math.abs(Constant.TEMPERATURE_LIST[i] - temp) <= 0.5) set.add(i);
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append("该地区适合饲养的鱼属性： ");
                    for(int sub : set) {
                        if(sub > 0) sb.append('+');
                        sb.append(sub);
                        sb.append(", ");
                    }
                    sb.setLength(sb.length() - 1);
                    player.sendMessage(sb.toString());
                } else if(args[0].equals("deco")) {
                    if (!player.hasPermission("mariculture.deco")) {
                        player.sendMessage("您无权使用该命令");
                        return true;
                    }
                    Inventory inv = player.getInventory();
                    for(ItemStack is : HeadManager.heads) {
                        inv.addItem(is);
                    }
                    //player.getWorld().dropItemNaturally(player.getLocation(), HeadManager.getRandomDrop(random));
                } else if(args[0].equals("debug")) {
                    if (!player.hasPermission("mariculture.debug")) {
                        player.sendMessage("您无权使用该命令");
                        return true;
                    }
                    Fish fish = new Fish();
                    fish.type[0] = FishType.DENGLONG;
                    fish.type[1] = FishType.DENGLONG;
                    fish.pro[0] = 6;
                    fish.pro[1] = 6;
                    fish.special[0] = FishSpecial.YANHUOWANHUI;
                    fish.special[1] = FishSpecial.YANHUOWANHUI;
                    fish.gender = false;
                    player.getInventory().addItem(ItemManager.fishToItem(fish));
                    fish.gender = true;
                    player.getInventory().addItem(ItemManager.fishToItem(fish));
                }
            }
            return true;
        } else {
            //Bukkit.getLogger().log(Level.SEVERE, command.getName());
        }
        return true;
    }
    
    @Override
    public ChunkGenerator getDefaultWorldGenerator( String worldName, String id ) 
    {
            return new FjordChunkGenerator(this);
     }
}
