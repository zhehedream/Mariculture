/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.zhehe.MC;

import java.util.ArrayList;
import java.util.List;
import me.zhehe.FishR.Fish;
import me.zhehe.Util.Constant;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author zhiqiang.hao
 */
public class ItemManager {
    public static ItemStack fishToItem(Fish fish) {
        String lore = fish.toLore();
        ItemStack is = new ItemStack(Material.TROPICAL_FISH, 1);
        ItemMeta im = is.getItemMeta();
        List<String> lores = new ArrayList<>();
        lores.add(Constant.FISH_TAG);
        lores.add(lore);
        
        lores.addAll(fish.getAsLore());
        
        im.setLore(lores);
        is.setItemMeta(im);
        
        return is;
    }
}
