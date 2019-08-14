/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.zhehe.MC;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import me.zhehe.Config.Config;
import me.zhehe.Database.SQLiteFishCatcher;
import me.zhehe.Util.Constant;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author zhiqiang.hao
 */
public class EventManager implements Listener {
    Random random;
    MariculturePlugin plugin;
    SQLiteFishCatcher db;
    public EventManager(MariculturePlugin plugin, Random random) {
        this.plugin = plugin;
        this.random = random;
        db = new SQLiteFishCatcher();
        db.load();
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.isCancelled()) {
            return;
        }

        ItemStack is = event.getItem();
        if(is != null && RecipeManager.isRod(is)) {
            //Bukkit.getLogger().log(Level.INFO, "!!ROD!!");
            event.setCancelled(true);
            Block block = event.getClickedBlock();
            if(block != null && block.getType() == Material.CHEST) {
                EntityManager.FishEntity[] entity = new EntityManager.FishEntity[1];
                Constant.TankErrorCode err = Tank.initTank(block, random, entity);
                if(err != Constant.TankErrorCode.None) {
                    String msg = MessageManager.TankErrorCodeToString(err);
                    MessageManager.sendPlayerMessage(event.getPlayer(), msg);
                } else {
                    MessageManager.sendPlayerSuccess(event.getPlayer());
                    
                    Damageable rod = (Damageable) is.getItemMeta();
                    int max_d = is.getType().getMaxDurability();
                    int current_d = rod.getDamage();
                    
                    int time;
                    if(RecipeManager.isIronRod(is)) time = Config.instance.IronRodMaxUseTime;
                    else if(RecipeManager.isGoldRod(is)) time = Config.instance.GoldRodMaxUseTime;
                    else time = Config.instance.DiamondRodMaxUseTime;
                    
                    current_d = current_d + max_d / time;
                    if(current_d > max_d) {
                        Player p = event.getPlayer();
                        EquipmentSlot es = event.getHand();
                        if(es == EquipmentSlot.HAND) {
                            p.getInventory().setItemInMainHand(null);
                            p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                        } else {
                            p.getInventory().setItemInOffHand(null);
                            p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                        }
                    } else {
                        rod.setDamage(current_d);
                        is.setItemMeta((ItemMeta)rod);
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void onInventoryOpenEvent(InventoryOpenEvent e){
        Location loc;
        if (e.getInventory().getHolder() instanceof Chest){
            Chest chest = (Chest) e.getInventory().getHolder();
            loc = chest.getLocation();
            
        } else if(e.getInventory().getHolder() instanceof DoubleChest) {
            DoubleChest chest = (DoubleChest) e.getInventory().getHolder();
            loc = chest.getLocation();
        } else {
            return;
        }
        int x = loc.getBlockX(), y = loc.getBlockY(), z = loc.getBlockZ();
        String world = loc.getWorld().getName();
        long db_time = db.isLocation(world, x, y, z);
        if(db_time > 0) {
            long time = System.currentTimeMillis() / 1000L;
            long diff = time - db_time;
            int minutes = (int)(diff / 60);
            Inventory inv = e.getInventory();
            ItemStack[] items = inv.getContents();
            int seeds = 0;
            for(ItemStack item : items) {
                if(item != null) {
                    if(item.getType() == Material.WHEAT_SEEDS) {
                        seeds = seeds + item.getAmount();
                    }
                }
            }
            ItemStack seed = new ItemStack(Material.WHEAT_SEEDS, 1);
            ItemStack[] fish = {new ItemStack(Material.COD,1), 
                new ItemStack(Material.SALMON,1), 
                new ItemStack(Material.TROPICAL_FISH, 1), 
                new ItemStack(Material.PUFFERFISH, 1)};
            int count = 0;
            while(minutes > 0 && seeds > 0 && count < 50) {
                count++;
                //2-5min : 1
                minutes = minutes - (2 + random.nextInt(3));
                seeds--;
                inv.removeItem(seed);
                inv.addItem(fish[random.nextInt(fish.length)]);
            }
            Bukkit.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
                db.addLocation(world, x, y, z);
            });
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {

        if (event.getBlock().getType() != Material.CHEST)
            return;

        ItemStack item = event.getItemInHand();
        List<String> lores = item.getItemMeta().getLore();
        if(lores == null || lores.isEmpty()) return;
        if(!lores.get(0).equals(Config.instance.FISH_CATCHER)) return;
        Block block = event.getBlock();
        if(block.getRelative(BlockFace.UP).getType() == Material.WATER
                || block.getRelative(BlockFace.DOWN).getType() == Material.WATER
                || block.getRelative(BlockFace.EAST).getType() == Material.WATER
                || block.getRelative(BlockFace.SOUTH).getType() == Material.WATER
                || block.getRelative(BlockFace.WEST).getType() == Material.WATER
                || block.getRelative(BlockFace.NORTH).getType() == Material.WATER) {
            Location location = event.getBlock().getLocation();
            String world = event.getBlock().getWorld().getName();
            Bukkit.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
                db.addLocation(world, block.getX(), block.getY(), block.getZ());
            });
            event.getPlayer().sendMessage(Config.instance.FISH_CATCHER_PLACE);
        } else {
            event.getPlayer().sendMessage(Config.instance.FISH_CATCHER_FAIL);
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getType() != Material.CHEST)
            return;

        if (db.isLocation(block.getWorld().getName(), block.getX(), block.getY(), block.getZ()) > 0) {
            event.setCancelled(true);

            ItemStack is = new ItemStack(Material.CHEST, 1);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(Config.instance.FISH_CATCHER);
            List<String> lores = new ArrayList<>();
            lores.add(Config.instance.FISH_CATCHER);
            im.setLore(lores);
            is.setItemMeta(im);

            event.getBlock().setType(Material.WATER);
            event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), is);
            Bukkit.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
                db.deleteLocation(block.getWorld().getName(), block.getX(), block.getY(), block.getZ());
            });
        }
    }
    
    @EventHandler
    public void onCookieEat(PlayerItemConsumeEvent e) {
        ItemStack is = e.getItem();
        if(!HeadManager.isLuckyCookie(is)) return;
        Player player = e.getPlayer();
        HeadManager.applySpecialEffect(player);
        player.getWorld().dropItemNaturally(player.getLocation(), HeadManager.getRandomDrop(random));
    }
    
    @EventHandler
    public void onBreadEat(PlayerItemConsumeEvent e) {
        if(WorldManager.fishWorld == null) return;
        if(Config.instance.worldEnable == false) return;
        World world;
        if((world = Bukkit.getServer().getWorld("world")) == null) return;
        ItemStack is = e.getItem();
        if(!WorldManager.isDimensionBread(is)) return;
        Player player = e.getPlayer();
        if(player.getWorld().getName().equals(WorldManager.fishWorld.getName())) {
            player.teleport(world.getSpawnLocation());
        } else {
            player.teleport(WorldManager.fishWorld.getSpawnLocation());
        }
    }
}
