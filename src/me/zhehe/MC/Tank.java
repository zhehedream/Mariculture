/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.zhehe.MC;

import java.util.List;
import java.util.Random;
import me.zhehe.FishR.Fish;
import static me.zhehe.FishR.Fusion.fusion;
import me.zhehe.FishR.Type.WaterType;
import me.zhehe.MC.EntityManager.FishEntity;
import me.zhehe.Util.Constant;
import me.zhehe.Util.Constant.TankErrorCode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Zhehe
 */
public class Tank {
    
    static boolean debug = false;
    
    public static TankErrorCode initTank(final Block handle, final Random random, FishEntity[] entity) {
        if(entity == null || entity.length == 0) {
            return TankErrorCode.Unknown;
        }
        int seagrass_count = 0;
        final World world = handle.getWorld();
        final int x = handle.getX();
        final int y = handle.getY();
        final int z = handle.getZ();
        final Block[] surface = isValidTank(handle);
        if(surface == null) {
            return TankErrorCode.NoWater;
        }
        
        boolean isCave = false;
        for(Block block : surface) {
            if(world.getHighestBlockAt(block.getLocation()).getRelative(BlockFace.DOWN).getY() != block.getY()) {
                isCave = true;
                break;
            }
        }
        
        boolean isSaltWater = false;
        Biome biome = surface[0].getBiome();
        for (Biome ocean : Constant.OCEAN) {
            if (biome == ocean) {
                isSaltWater = true;
                break;
            }
        }
        
        for(Block block : surface) {
            Material tmp = getBottom(block);
            if(tmp == Material.SEAGRASS || tmp == Material.TALL_SEAGRASS) seagrass_count++;
        }
                
        final Inventory inv = ((Chest) handle.getState()).getInventory();
        final ItemStack[] fishes = findFish(inv);
        
        if(fishes == null) {
            return TankErrorCode.NoFish;
        }
        
        Fish A = new Fish();
        Fish B = new Fish();
        
        try {
            if(!A.fromLore(fishes[0].getItemMeta().getLore().get(1))) { 
                return TankErrorCode.NoFish; 
            }
            if(!B.fromLore(fishes[1].getItemMeta().getLore().get(1))) { 
                return TankErrorCode.NoFish; 
            }
        } catch(Exception ex) {
            //未知错误
            return TankErrorCode.Unknown;
        }
        
        if(A.gender == B.gender) {
            //性别相同
            return TankErrorCode.Gender;
        }
        
        if(!A.gender) {
            Fish tmp = B;
            B = A;
            A = tmp;
        }
        
        String tank_world = handle.getWorld().getName();
        String fish_world;
        if(WorldManager.fishWorld != null) fish_world = WorldManager.fishWorld.getName();
        else fish_world = "";
        
        if(isCave && !A.dark[0]) {
            //非穴居鱼穴居
            return TankErrorCode.TooDark;
        }
        
        if(!isCave && A.dark[0]) {
            //穴居鱼非穴居
            return TankErrorCode.TooLight;
        }
        
        if(isSaltWater && A.water[0] != WaterType.SALT  && !fish_world.equals(tank_world)) {
            //不是咸水鱼
            return TankErrorCode.NoSweetWater;
        }
        
        if(!isSaltWater && A.water[0] != WaterType.SWEET  && !fish_world.equals(tank_world)) {
            //不是淡水鱼
            return TankErrorCode.NoSaltWater;
        }
        
        if(A.oxygen_adap[0] != seagrass_count) {
            //氧浓度错误
            return TankErrorCode.Oxygen;
        }
        
        final double biome_temperature = surface[0].getTemperature();
        final double fish_temperature = Constant.TEMPERATURE_LIST[A.temp_adap[0]];
        if(Math.abs(biome_temperature - fish_temperature) > 0.5  && !fish_world.equals(tank_world)) {
            //温度错误
            return TankErrorCode.Temperature;
        }
        
        final int next_gen = random.nextInt(3) + 2;
        Fish[] child = new Fish[next_gen];
        
        for(int i = 0; i < child.length; i++) {
            child[i] = fusion(A, B, random, debug);
            child[i].gender = false;
        }
        child[0].gender = true;
        
        entity[0] = new FishEntity(A, child, surface[0]);
        if(!EntityManager.spawnEntity(entity[0])) {
            //unknown
            return TankErrorCode.Unknown;
        }
        
        
        //fish-1
        if(fishes[0].getAmount() == 1) {
            inv.setItem(0, null);
        } else {
            fishes[0].setAmount(fishes[0].getAmount() - 1);
        }
        if(fishes[1].getAmount() == 1) {
            inv.setItem(1, null);
        } else {
            fishes[1].setAmount(fishes[1].getAmount() - 1);
        }
        
        return TankErrorCode.None;
    }
    
    public static Block[] isValidTank(final Block handle) {
        if(handle.getType() != Material.CHEST) return null;

        BlockData data = handle.getBlockData();
        
        BlockFace facing = ((Directional) data).getFacing();
        BlockFace[] vert = new BlockFace[2];
        if (facing == BlockFace.NORTH || facing == BlockFace.SOUTH) {
            vert[0] = BlockFace.EAST;
            vert[1] = BlockFace.WEST;
        } else {
            vert[0] = BlockFace.NORTH;
            vert[1] = BlockFace.SOUTH;
        }
        
        Block[] tank = new Block[9];
        tank[0] = handle.getRelative(facing).getRelative(BlockFace.DOWN);
        tank[1] = tank[0].getRelative(vert[0]);
        tank[2] = tank[0].getRelative(vert[1]);
        tank[3] = tank[0].getRelative(facing);
        tank[4] = tank[1].getRelative(facing);
        tank[5] = tank[2].getRelative(facing);
        tank[6] = tank[3].getRelative(facing);
        tank[7] = tank[4].getRelative(facing);
        tank[8] = tank[5].getRelative(facing);
        
        for (Block tank1 : tank) {
            Material material = tank1.getType();
            if (material != Material.WATER && material != Material.SEAGRASS && material != Material.TALL_SEAGRASS && material != Material.KELP_PLANT) {
                return null;
            }
            
        }
        
        return tank;
    }
    
    private static ItemStack[] findFish(final Inventory inv) {
        ItemStack[] res = new ItemStack[2];
        ItemStack[] storage = inv.getStorageContents();
        for(int i = 0; i < 2; i++) {
            if(storage[i] == null) return null;
            ItemMeta meta = storage[i].getItemMeta();
            if(meta == null) return null;
            List<String> lore = meta.getLore();
            if(lore != null && lore.size() >= 2 && lore.get(0).equals(Constant.FISH_TAG)) {
                res[i] = storage[i];
            } else return null;
        }
        return res;
    }
    
    private static Material getBottom(Block block) {
        while(block.getType() == Material.WATER) {
            block = block.getRelative(BlockFace.DOWN);
        }
        return block.getType();
    }
}
