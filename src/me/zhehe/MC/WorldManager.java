/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.zhehe.MC;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import me.zhehe.Util.Constant;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Zhehe
 */
public class WorldManager {
    public static World fishWorld;
    public static void createWorld(ChunkGenerator generator, Random random) {
        WorldCreator wc = new WorldCreator(Constant.FISH_WORLD);
        wc.generator(generator);
        wc.type(WorldType.NORMAL);
        wc.seed(random.nextInt(67534200));
        
        fishWorld = wc.createWorld();
    }
    
    public static ItemStack getDimensionBread() {
        ItemStack is = new ItemStack(Material.BREAD, 1);
        ItemMeta im = is.getItemMeta();
        List<String> lores = new ArrayList<>();
        lores.add(Constant.FISH_BREAD_TAG);
        im.setLore(lores);
        im.setDisplayName(Constant.FISH_BREAD);
        is.setItemMeta(im);
        return is;
    }
    
    public static boolean isDimensionBread(ItemStack is) {
        if(is.getType() != Material.BREAD) return false;
        List<String> lores = is.getItemMeta().getLore();
        if(lores == null || lores.isEmpty()) return false;
        return lores.get(0).equals(Constant.FISH_BREAD_TAG);
    }
}
