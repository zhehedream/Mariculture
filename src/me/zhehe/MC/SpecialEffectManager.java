/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.zhehe.MC;

import java.util.Random;
import me.zhehe.FishR.Type.FishSpecial;
import me.zhehe.MC.EntityManager.FishEntity;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

/**
 *
 * @author zhiqiang.hao
 */
public class SpecialEffectManager {
    public final static Color[] COLORSET = {
        Color.AQUA,
        Color.BLACK,
        Color.BLUE,
        Color.FUCHSIA,
        Color.GRAY,
        Color.GREEN,
        Color.LIME,
        Color.MAROON,
        Color.NAVY,
        Color.OLIVE,
        Color.ORANGE,
        Color.PURPLE,
        Color.RED,
        Color.SILVER,
        Color.TEAL,
        Color.WHITE,
        Color.YELLOW,
    };
    public final static Material[] FLOWERSET = {
        Material.DANDELION,
        Material.POPPY,
        Material.BLUE_ORCHID,
        Material.ALLIUM,
        Material.AZURE_BLUET,
        Material.RED_TULIP,
        Material.ORANGE_TULIP,
        Material.WHITE_TULIP,
        Material.PINK_TULIP,
        Material.OXEYE_DAISY,
        Material.SUNFLOWER,
        Material.LILAC,
        Material.FERN,
        Material.ROSE_BUSH,
        Material.PEONY,
    };
    
    public final static PotionType[] POTIONSET = {
        PotionType.JUMP,
        PotionType.FIRE_RESISTANCE,
        PotionType.INSTANT_HEAL,
        PotionType.NIGHT_VISION,
        PotionType.REGEN,
        PotionType.SLOW_FALLING,
        PotionType.SPEED,
        PotionType.WATER_BREATHING,
    };
    
    public final static Material[] CORALSET = {
        Material.TUBE_CORAL, Material.TUBE_CORAL_BLOCK, Material.TUBE_CORAL_FAN, 
        Material.BRAIN_CORAL, Material.BRAIN_CORAL_BLOCK, Material.BRAIN_CORAL_FAN,
        Material.BUBBLE_CORAL, Material.BUBBLE_CORAL_BLOCK, Material.BUBBLE_CORAL_FAN,
        Material.FIRE_CORAL, Material.FIRE_CORAL_BLOCK, Material.FIRE_CORAL_FAN,
    };
    
    public static void doSpecialEffect(FishEntity fe, Entity entity, boolean isRandomTick, Random random) {
        if(isRandomTick) {
            World world = entity.getWorld();
            Location loc = entity.getLocation();
            if(null != fe.mother.special[0]) switch (fe.mother.special[0]) {
                case YANHUOWANHUI:{
                    int amount = 1 + random.nextInt(2);
                    for(int i = 0; i < amount; i++) {
                        ItemStack is = getFireworkStar(random);
                        if(is != null) world.dropItem(loc, is);
                    }       break;
                }
                case YUANDING:{
                    int amount = 1 + random.nextInt(2);
                    for(int i = 0; i < amount; i++) {
                        ItemStack is = getFlower(random);
                        if(is != null) world.dropItem(loc, is);
                    }       break;
                }
                case HULI:{
                    int amount = 1 + random.nextInt(2);
                    for(int i = 0; i < amount; i++) {
                        ItemStack is = getPotion(random);
                        if(is != null) world.dropItem(loc, is);
                    }       break;
                }
                case GUOJIABAOZANG:{
                    int rand = random.nextInt(10);
                    if(rand == 0) {
                        ItemStack is = getDiamond();
                        world.dropItem(loc, is);
                    }       break;
                }
                case SHIDAI:{
                    int rand = random.nextInt(10);
                    if(rand == 0) {
                        ItemStack is = getTotem();
                        world.dropItem(loc, is);
                    }       break;
                }
                case DABAOJIAO:{
                    int rand = random.nextInt(10);
                    if(rand == 0) {
                        ItemStack is = getCoral(random);
                        world.dropItem(loc, is);
                    }
                }
                default:
                    break;
            }
        } else {
            if(null != fe.mother.special[0] && fe.mother.special[0] == FishSpecial.XIDAPUBEN) {
                applyEffect(entity.getLocation(), PotionEffectType.ABSORPTION, 20);
            }
        }
    }
    
    public static ItemStack getCoral(Random random) {
        int amount = 1 + random.nextInt(2);
        return new ItemStack(CORALSET[random.nextInt(CORALSET.length)], amount);
    }
    
    public static ItemStack getFireworkStar(Random random) {
        ItemStack is = new ItemStack(Material.FIREWORK_STAR, 1);
        ItemMeta meta = is.getItemMeta();
        FireworkEffectMeta metaFw = (FireworkEffectMeta) meta;
        if(metaFw == null) return null;
        Builder builder = FireworkEffect.builder();
        //Get the type
        int rt = random.nextInt(4) + 1;
        Type type;       
        switch (rt) {
            case 1:
                type = Type.BALL;
                break;
            case 2:
                type = Type.BALL_LARGE;
                break;
            case 3:
                type = Type.BURST;
                break;
            case 4:
                type = Type.CREEPER;
                break;
            default:
                type = Type.STAR;
                break;
        }
        int r1i = random.nextInt(COLORSET.length);
        int r2i = random.nextInt(COLORSET.length);
        Color c1 = COLORSET[r1i];
        Color c2 = COLORSET[r2i];
        FireworkEffect effect = builder.flicker(random.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(random.nextBoolean()).build();
        metaFw.setEffect(effect);
        is.setItemMeta(metaFw);
        return is;
    }
    public static ItemStack getFlower(Random random) {
        return new ItemStack(FLOWERSET[random.nextInt(FLOWERSET.length)], 1);
    }
    public static ItemStack getPotion(Random random) {
        ItemStack potion = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta) potion.getItemMeta();
        if(meta == null) return null;
        meta.setBasePotionData(new PotionData(POTIONSET[random.nextInt(POTIONSET.length)], random.nextBoolean(), random.nextBoolean()));
        potion.setItemMeta(meta);
        return potion;
    }
    public static ItemStack getDiamond() {
        return new ItemStack(Material.DIAMOND, 1);
    }
    public static ItemStack getTotem() {
        return new ItemStack(Material.TOTEM_OF_UNDYING, 1);
    }
    public static void applyEffect(Location loc, PotionEffectType effect, double range) {
        PotionEffect pe = new PotionEffect(effect, 10, 2);
        double x = loc.getX();
        double y = loc.getX();
        double z = loc.getX();
        range = range * range;
        try {
            for (Player player : loc.getWorld().getPlayers()) {
                Location ploc = player.getLocation();
                double dx = ploc.getX() - x;
                double dy = ploc.getY() - y;
                double dz = ploc.getZ() - z;
                double tmp = dx * dx + dy * dy + dz * dz;
                if(tmp < range) {
                    player.addPotionEffect(pe, true);
                }
            }
        } catch(Exception ex) {
            
        }
        
        
        
    }
}
