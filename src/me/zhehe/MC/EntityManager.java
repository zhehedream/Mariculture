/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.zhehe.MC;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import me.zhehe.Config.Config;
import me.zhehe.FishR.Fish;
import static me.zhehe.MC.SpecialEffectManager.doSpecialEffect;
import me.zhehe.Util.Constant;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.TropicalFish;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author zhiqiang.hao
 */
public class EntityManager {
    
    Random random;
    MariculturePlugin plugin;
    
    public EntityManager(MariculturePlugin plugin, Random random) {
        this.plugin = plugin;
        this.random = random;
    }
    
    public static class FishEntity {
        Fish mother;
        Fish[] child;
        transient Block block;
        long unixTime;
        
        public FishEntity(Fish mother, Fish[] child, Block block) {
            this.mother = mother;
            this.child = child;
            this.block = block;
        }
    }
    
    public static boolean spawnEntity(final FishEntity fe) {
        if(fe == null || fe.mother == null || fe.child == null || fe.block == null) return false;
        fe.unixTime = System.currentTimeMillis() / 1000L;
        String json = (new Gson()).toJson(fe);
        TropicalFish entity = (TropicalFish) fe.block.getWorld().spawnEntity(fe.block.getLocation(), EntityType.TROPICAL_FISH);
        entity.setBodyColor(fe.mother.getBodyColor());
        entity.setPattern(fe.mother.getPattern());
        entity.setPatternColor(fe.mother.getPatternColor());
        entity.addScoreboardTag(Constant.FISH_TAG + json);
        entity.setRemoveWhenFarAway(false);
        entity.setGlowing(true);
        
        return true;
    }
    
    private static List<Entity> getAllRelativeFishEntity() {
        List<Entity> result = new ArrayList<>();
        List<World> worlds = Bukkit.getWorlds();
        for(World world : worlds) {
            List<Entity> entities = world.getEntities();
            for(Entity entity : entities) {
                if(entity instanceof TropicalFish && entity.isValid() && entity.isGlowing() && !(((LivingEntity)entity).getRemoveWhenFarAway())) {
                    result.add(entity);
                }
            }
        }
        return result;
    }
    
    public static void doSecondEvent(Random random) {
        final long current = System.currentTimeMillis() / 1000L;
        final List<Entity> entities = getAllRelativeFishEntity();
        final int tag_len = Constant.FISH_TAG.length();
        final Gson gson = new Gson();
        for(Entity entity : entities) {
            Set<String> tags = entity.getScoreboardTags();
            for(String tag : tags) {
                if(tag.length() > tag_len && tag.indexOf(Constant.FISH_TAG) == 0) {
                    tag = tag.substring(tag_len);
                    FishEntity fe = gson.fromJson(tag, FishEntity.class);
                    long age = current - fe.unixTime;
                    if(age / 60 > Constant.LIFE_ARRAY[fe.mother.life[0]]) {
                        //old enough to die
                        for(Fish fish : fe.child) {
                            ItemStack is = ItemManager.fishToItem(fish);
                            entity.getWorld().dropItem(entity.getLocation(), is);
                        }
                        entity.remove();
                    } else {
                        doSpecialEffect(fe, entity, false, random);
                        
                        //calc drop item
                        double r = random.nextInt(4 * Config.instance.baseRate * Constant.PRO_TICK[fe.mother.pro[0]]);
                        double threshold = 1;
                        
                        ItemStack is = new ItemStack(Material.DIRT, 1);
                        if(Config.instance.fishProductionDict.containsKey(fe.mother.type[0])) {
                            is = Config.instance.fishProductionDict.get(fe.mother.type[0]);
                        }
                        
                        if(Config.instance.itemProductionRate.containsKey(is)) {
                            threshold = Config.instance.itemProductionRate.get(is);
                        }
                        if(r > threshold) continue;

                        entity.getWorld().dropItem(entity.getLocation(), is);
                        doSpecialEffect(fe, entity, true, random);
                    }
                    break;
                }
            }
        }
    }
    
    public static boolean isValidFishTag(final String tag, FishEntity[] fe) {
        if(fe == null || fe.length == 0) return false;
        try {
            fe[0] = (new Gson()).fromJson(tag, FishEntity.class);
            if(fe[0] == null) return false;
            return fe[0].mother != null;
        } catch (JsonSyntaxException ex) {
            return false;
        }
    }
    
    public static void main(String[] args) {
        FishEntity fe2 = new FishEntity(new Fish(), null, null);
        String str = (new Gson()).toJson(fe2);
        System.out.println(str);
        FishEntity[] fe = {null};
        System.out.println(isValidFishTag("{\"title\"=>\"Ruby In Rails\", \"url\"=>\"https://rubyinrails.com\", \"posts\"=>{\"1\"=>\"strftime-time-format-in-ruby\", \"2\"=>\"what-is-gemset\"}}", fe));
        System.out.println((new Gson()).toJson(fe));
        
        System.out.println(isValidFishTag(str, fe));
        System.out.println((new Gson()).toJson(fe));
    }
    
}
