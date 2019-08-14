/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.zhehe.MC;

import java.util.ArrayList;
import java.util.List;
import me.zhehe.Config.Config;
import me.zhehe.FishR.Fish;
import me.zhehe.FishR.Type.FishType;
import me.zhehe.FishR.Type.WaterType;
import static me.zhehe.MC.BookManager.getGuideBook;
import me.zhehe.Util.Constant;
import static org.bukkit.Bukkit.getServer;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;


public class RecipeManager {
    private MariculturePlugin plugin;
    public RecipeManager(MariculturePlugin plugin) {
        this.plugin = plugin;
    }
    
    public void registerTools() {
        
        {
            ItemStack rod1 = new ItemStack(Material.FISHING_ROD);
            ItemMeta im = rod1.getItemMeta();
            if(im == null) return;
            im.setDisplayName(Config.instance.Rod_Iron);
            List<String> sub = new ArrayList<>();
            sub.add(Config.instance.RodIronTag);
            sub.add(Config.instance.RodIronDescription);
            im.setLore(sub);
            rod1.setItemMeta(im);
            ShapedRecipe recipe = new ShapedRecipe( new NamespacedKey(plugin, "rod1"), rod1 );
            recipe.shape( " X ", "XSX", " X " );
            recipe.setIngredient( 'X', Material.IRON_INGOT );
            recipe.setIngredient( 'S', Material.FISHING_ROD );
            getServer().addRecipe( recipe );
        }
        {
            ItemStack rod2 = new ItemStack(Material.FISHING_ROD);
            ItemMeta im = rod2.getItemMeta();
            if(im == null) return;
            im.setDisplayName(Config.instance.Rod_Gold);
            List<String> sub = new ArrayList<>();
            sub.add(Config.instance.RodGoldTag);
            sub.add(Config.instance.RodGoldDescription);
            im.setLore(sub);
            rod2.setItemMeta(im);
            ShapedRecipe recipe = new ShapedRecipe( new NamespacedKey(plugin, "rod2"), rod2 );
            recipe.shape( " X ", "XSX", " X " );
            recipe.setIngredient( 'X', Material.GOLD_INGOT );
            recipe.setIngredient( 'S', Material.FISHING_ROD );
            getServer().addRecipe( recipe );
        }
        {
            ItemStack rod3 = new ItemStack(Material.FISHING_ROD);
            ItemMeta im = rod3.getItemMeta();
            if(im == null) return;
            im.setDisplayName(Config.instance.Rod_Diamond);
            List<String> sub = new ArrayList<>();
            sub.add(Config.instance.RodDiamondTag);
            sub.add(Config.instance.RodDiamondDescription);
            im.setLore(sub);
            rod3.setItemMeta(im);
            ShapedRecipe recipe = new ShapedRecipe( new NamespacedKey(plugin, "rod3"), rod3 );
            recipe.shape( " X ", "XSX", " X " );
            recipe.setIngredient( 'X', Material.DIAMOND );
            recipe.setIngredient( 'S', Material.FISHING_ROD );
            getServer().addRecipe( recipe );
        }
        ItemStack guide_book = getGuideBook();
        {
            ShapelessRecipe recipe = new ShapelessRecipe( new NamespacedKey(plugin, "guide_book1"), guide_book);
            recipe.addIngredient(Material.COD);
            recipe.addIngredient(Material.BOOK);
            getServer().addRecipe(recipe);
        }
        {
            ShapelessRecipe recipe = new ShapelessRecipe( new NamespacedKey(plugin, "guide_book2"), guide_book);
            recipe.addIngredient(Material.SALMON);
            recipe.addIngredient(Material.BOOK);
            getServer().addRecipe(recipe);
        }
        {
            ShapelessRecipe recipe = new ShapelessRecipe( new NamespacedKey(plugin, "guide_book3"), guide_book);
            recipe.addIngredient(Material.TROPICAL_FISH);
            recipe.addIngredient(Material.BOOK);
            getServer().addRecipe(recipe);
        }
        
        {
            Fish pingfan = new Fish();
            pingfan.type[0] = FishType.PINGFAN; pingfan.type[1] = FishType.PINGFAN;
            pingfan.gender = false;
            ShapelessRecipe recipe = new ShapelessRecipe( new NamespacedKey(plugin, "mpingfan_fish"), ItemManager.fishToItem(pingfan));
            recipe.addIngredient(Material.TROPICAL_FISH_BUCKET);
            recipe.addIngredient(Material.IRON_BLOCK);
            recipe.addIngredient(Material.EXPERIENCE_BOTTLE);
            getServer().addRecipe(recipe);
        }
        {
            Fish pusu = new Fish();
            pusu.type[0] = FishType.PUSU; pusu.type[1] = FishType.PUSU;
            pusu.water[0] = WaterType.SALT; pusu.water[1] = WaterType.SALT;
            pusu.gender = false;
            ShapelessRecipe recipe = new ShapelessRecipe( new NamespacedKey(plugin, "mpusu_fish"), ItemManager.fishToItem(pusu));
            recipe.addIngredient(Material.SALMON_BUCKET);
            recipe.addIngredient(Material.IRON_BLOCK);
            recipe.addIngredient(Material.EXPERIENCE_BOTTLE);
            getServer().addRecipe(recipe);
        }
        {
            Fish pushi = new Fish();
            pushi.type[0] = FishType.PUSHI; pushi.type[1] = FishType.PUSHI;
            pushi.gender = false;
            ShapelessRecipe recipe = new ShapelessRecipe( new NamespacedKey(plugin, "mpushi_fish"), ItemManager.fishToItem(pushi));
            recipe.addIngredient(Material.COD_BUCKET);
            recipe.addIngredient(Material.IRON_BLOCK);
            recipe.addIngredient(Material.EXPERIENCE_BOTTLE);
            getServer().addRecipe(recipe);
        }
        {
            Fish pingfan = new Fish();
            pingfan.type[0] = FishType.PINGFAN; pingfan.type[1] = FishType.PINGFAN;
            pingfan.gender = true;
            ShapelessRecipe recipe = new ShapelessRecipe( new NamespacedKey(plugin, "fpingfan_fish"), ItemManager.fishToItem(pingfan));
            recipe.addIngredient(Material.TROPICAL_FISH_BUCKET);
            recipe.addIngredient(Material.DIAMOND_BLOCK);
            recipe.addIngredient(Material.EXPERIENCE_BOTTLE);
            getServer().addRecipe(recipe);
        }
        {
            Fish pusu = new Fish();
            pusu.type[0] = FishType.PUSU; pusu.type[1] = FishType.PUSU;
            pusu.water[0] = WaterType.SALT; pusu.water[1] = WaterType.SALT;
            pusu.gender = true;
            ShapelessRecipe recipe = new ShapelessRecipe( new NamespacedKey(plugin, "fpusu_fish"), ItemManager.fishToItem(pusu));
            recipe.addIngredient(Material.SALMON_BUCKET);
            recipe.addIngredient(Material.DIAMOND_BLOCK);
            recipe.addIngredient(Material.EXPERIENCE_BOTTLE);
            getServer().addRecipe(recipe);
        }
        {
            Fish pushi = new Fish();
            pushi.type[0] = FishType.PUSHI; pushi.type[1] = FishType.PUSHI;
            pushi.gender = true;
            ShapelessRecipe recipe = new ShapelessRecipe( new NamespacedKey(plugin, "fpushi_fish"), ItemManager.fishToItem(pushi));
            recipe.addIngredient(Material.COD_BUCKET);
            recipe.addIngredient(Material.DIAMOND_BLOCK);
            recipe.addIngredient(Material.EXPERIENCE_BOTTLE);
            getServer().addRecipe(recipe);
        }
        
        {
            ItemStack is = new ItemStack(Material.CHEST, 1);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(Config.instance.FISH_CATCHER);
            List<String> lores = new ArrayList<>();
            lores.add(Config.instance.FISH_CATCHER);
            im.setLore(lores);
            is.setItemMeta(im);
            
            ShapedRecipe recipe = new ShapedRecipe( new NamespacedKey(plugin, "fish_catcher"), is );
            recipe.shape( " X ", "XSX", " X " );
            recipe.setIngredient( 'X', Material.COBWEB );
            recipe.setIngredient( 'S', Material.CHEST );
            getServer().addRecipe( recipe );
        }
        
        {
            ShapedRecipe recipe = new ShapedRecipe( new NamespacedKey(plugin, "lucky_cookie"), HeadManager.getLuckyCookie() );
            recipe.shape("XSX","FCF","XSX");
            recipe.setIngredient('X', Material.TROPICAL_FISH);
            recipe.setIngredient('S', Material.PRISMARINE_CRYSTALS);
            recipe.setIngredient('F', Material.PRISMARINE_SHARD);
            recipe.setIngredient('C', Material.COOKIE);
            getServer().addRecipe( recipe );
        }
        
        {
            ShapedRecipe recipe = new ShapedRecipe( new NamespacedKey(plugin, "dimension_bread"), WorldManager.getDimensionBread());
            recipe.shape(" B "," F "," B ");
            recipe.setIngredient('B', Material.BREAD);
            recipe.setIngredient('F', Material.COOKED_COD);
            getServer().addRecipe( recipe );
        }
    }
    
    public static boolean isRod(ItemStack item) {
        return item.getType() == Material.FISHING_ROD &&( isIronRod(item) || isGoldRod(item) || isDiamondRod(item) );
    }
    
    public static boolean isIronRod(ItemStack item) {
        if(item == null) return false;
        ItemMeta im = item.getItemMeta();
        if(im == null) return false;
        List<String> lores = im.getLore();
        return lores != null && lores.size() > 0 && lores.get(0).equals(Config.instance.RodIronTag);
    }
    public static boolean isGoldRod(ItemStack item) {
        if(item == null) return false;
        ItemMeta im = item.getItemMeta();
        if(im == null) return false;
        List<String> lores = im.getLore();
        return lores != null && lores.size() > 0 && lores.get(0).equals(Config.instance.RodGoldTag);
    }
    public static boolean isDiamondRod(ItemStack item) {
        if(item == null) return false;
        ItemMeta im = item.getItemMeta();
        if(im == null) return false;
        List<String> lores = im.getLore();
        return lores != null && lores.size() > 0 && lores.get(0).equals(Config.instance.RodDiamondTag);
    }
}
