/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.zhehe.Config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import me.zhehe.FishR.Type.FishType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


/**
 *
 * @author zhiqiang.hao
 */
public class Config {
    public static Config instance = new Config();
    public String Rod_Iron;
    public String RodIronTag;
    public String RodIronDescription;
    public String Rod_Gold;
    public String RodGoldTag;
    public String RodGoldDescription;
    public String Rod_Diamond;
    public String RodDiamondTag;
    public String RodDiamondDescription;
    public String UnknownErrorMessage;
    public String NoWaterErrorMessage;
    public String NoFishErrorMessage;
    public String GenderErrorMessage;
    public String TooDarkErrorMessage;
    public String TooLightErrorMessage;
    public String NotSweetWaterErrorMessage;
    public String NotSaltWaterErrorMessage;
    public String OxygenErrorMessage;
    public String TemperatureErrorMessage;
    public Map<ItemStack,Double> itemProductionRate = new HashMap<>();
    public Map<FishType, ItemStack> fishProductionDict = new HashMap<>();
    public int baseRate = 35;
    public int IronRodMaxUseTime;
    public int GoldRodMaxUseTime;
    public int DiamondRodMaxUseTime;
    
    private Config() {
        
    }
    
    public void initConfig() {
        final String PATH = "./plugins/Mariculture/";
        File directory = new File(PATH);
        if(!directory.exists()) {
            directory.mkdir();
        }
        File file = new File(PATH + "FishConfig.json");
        boolean need_initialize = true;
        DefaultConfig dc = generateDefaultConfig();
        if(file.exists()) {
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(PATH + "FishConfig.json"), "UTF8"))) {
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                    sb.append(line);
                    line = reader.readLine();
                }

                Bukkit.getLogger().log(Level.INFO, sb.toString());
                dc = (new Gson()).fromJson(sb.toString(), DefaultConfig.class);
            } catch(IOException ex) {
                need_initialize = true;
            }
        }
        
        if(need_initialize) {
            dc = generateDefaultConfig();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(dc);
            try(OutputStreamWriter oStreamWriter = new OutputStreamWriter(new FileOutputStream(PATH + "FishConfig.json"), "utf-8")) {
                oStreamWriter.append(json);
                oStreamWriter.close();
            } catch (IOException ex) {
                
            }
            
        }
        for(Map.Entry<Map<String, Object>, Double> entry : dc.itemProductionRate.entrySet()) {
            this.itemProductionRate.put(ItemStack.deserialize(entry.getKey()), entry.getValue());
        }
        for(Map.Entry<FishType, Map<String, Object>> entry : dc.fishProductionDict.entrySet()) {
            this.fishProductionDict.put(entry.getKey(), ItemStack.deserialize(entry.getValue()));
        }
        this.Rod_Iron = dc.Rod_Iron;
        this.RodIronTag = dc.RodIronTag;
        this.RodIronDescription = dc.RodIronDescription;
        this.Rod_Gold = dc.Rod_Gold;
        this.RodGoldTag = dc.RodGoldTag;
        this.RodGoldDescription = dc.RodGoldDescription;
        this.Rod_Diamond = dc.Rod_Diamond;
        this.RodDiamondTag = dc.RodDiamondTag;
        this.RodDiamondDescription = dc.RodDiamondDescription;
        
        this.GenderErrorMessage = dc.GenderErrorMessage;
        this.NoFishErrorMessage = dc.NoFishErrorMessage;
        this.NoWaterErrorMessage = dc.NoWaterErrorMessage;
        this.NotSaltWaterErrorMessage = dc.NotSaltWaterErrorMessage;
        this.NotSweetWaterErrorMessage = dc.NotSweetWaterErrorMessage;
        this.OxygenErrorMessage = dc.OxygenErrorMessage;
        this.TemperatureErrorMessage = dc.TemperatureErrorMessage;
        this.TooDarkErrorMessage = dc.TooDarkErrorMessage;
        this.TooLightErrorMessage = dc.TooLightErrorMessage;
        this.UnknownErrorMessage = dc.UnknownErrorMessage;
        
        this.IronRodMaxUseTime = dc.IronRodMaxUseTime;
        this.GoldRodMaxUseTime = dc.GoldRodMaxUseTime;
        this.DiamondRodMaxUseTime = dc.DiamondRodMaxUseTime;
    }
    
    static class DefaultConfig {
        public String Rod_Iron = "铁钩竿";
        public String RodIronTag = "RodIron";
        public String Rod_Gold = "金钩竿";
        public String RodGoldTag = "RodGold";
        public String Rod_Diamond = "钻石钩竿";
        public String RodDiamondTag = "RodDiamond";
        
        public String RodIronDescription = "§f这个钓竿散发着神奇的魔力，似乎可以召唤什么...";
        public String RodGoldDescription = "§f这个钓竿散发着神奇的魔力，似乎可以召唤什么...";
        public String RodDiamondDescription = "§f这个钓竿散发着神奇的魔力，似乎可以召唤什么...";
        
        public String UnknownErrorMessage = "发生未知错误";
        public String NoWaterErrorMessage = "找不到水面。养鱼需要3x3的水面";
        public String NoFishErrorMessage = "请在箱子的前两格分别放入需要培养的两种鱼";
        public String GenderErrorMessage = "两种鱼的性别需不相同";
        public String TooDarkErrorMessage = "该个体无法适应非露天环境";
        public String TooLightErrorMessage = "该个体无法适应露天环境";
        public String NotSweetWaterErrorMessage = "该个体无法适应咸水环境";
        public String NotSaltWaterErrorMessage = "该个体无法适应淡水环境";
        public String OxygenErrorMessage = "氧气浓度错误，请尝试增加／移除水草";
        public String TemperatureErrorMessage = "温度错误，您可以使用/macu temp查看当前位置温度   请尝试将鱼塘迁移至其他生态群系";
        
        public int IronRodMaxUseTime = 10;
        public int GoldRodMaxUseTime = 15;
        public int DiamondRodMaxUseTime = 40;
        
        
        public Map<Map<String, Object>, Double> itemProductionRate = new HashMap<>();
        public Map<FishType, Map<String, Object>> fishProductionDict = new HashMap<>();
    }
    private static DefaultConfig generateDefaultConfig() {
        DefaultConfig dc = new DefaultConfig();
        {
            dc.fishProductionDict.put(FishType.PINGFAN, (new ItemStack(Material.DIRT, 10)).serialize());
            dc.fishProductionDict.put(FishType.PUSU, (new ItemStack(Material.CLAY, 10)).serialize());
            dc.fishProductionDict.put(FishType.PUSHI, (new ItemStack(Material.SAND, 10)).serialize());
            dc.fishProductionDict.put(FishType.CHONGLANG, (new ItemStack(Material.LILY_PAD, 5)).serialize());
            dc.fishProductionDict.put(FishType.QIANSHUI, (new ItemStack(Material.ICE, 5)).serialize());
            dc.fishProductionDict.put(FishType.TOUTIE, (new ItemStack(Material.RABBIT_FOOT, 2)).serialize());
            dc.fishProductionDict.put(FishType.TIAOYUE, (new ItemStack(Material.SLIME_BALL, 3)).serialize());
            dc.fishProductionDict.put(FishType.JILIU, (new ItemStack(Material.COD, 3)).serialize());
            dc.fishProductionDict.put(FishType.JIYOU, (new ItemStack(Material.SALMON, 3)).serialize());
            dc.fishProductionDict.put(FishType.NILIU, (new ItemStack(Material.MAGMA_CREAM, 3)).serialize());
            dc.fishProductionDict.put(FishType.JIANYUE, (new ItemStack(Material.GUNPOWDER, 3)).serialize());
            dc.fishProductionDict.put(FishType.YUANGU, (new ItemStack(Material.PRISMARINE_SHARD, 3)).serialize());
            dc.fishProductionDict.put(FishType.SHIZU, (new ItemStack(Material.PRISMARINE_CRYSTALS, 3)).serialize());
            dc.fishProductionDict.put(FishType.DITU, (new ItemStack(Material.RED_SAND, 3)).serialize());
            dc.fishProductionDict.put(FishType.CHENCHUAN, (new ItemStack(Material.GLOWSTONE, 3)).serialize());
            dc.fishProductionDict.put(FishType.BAOZANG, (new ItemStack(Material.QUARTZ, 3)).serialize());
            dc.fishProductionDict.put(FishType.HUASHI, (new ItemStack(Material.NAUTILUS_SHELL, 3)).serialize());
            
            dc.fishProductionDict.put(FishType.DENGLONG, (new ItemStack(Material.LAPIS_LAZULI, 3)).serialize());
            dc.fishProductionDict.put(FishType.HUAYANG, (new ItemStack(Material.LAPIS_LAZULI, 3)).serialize());
            dc.fishProductionDict.put(FishType.MEILI, (new ItemStack(Material.LAPIS_LAZULI, 3)).serialize());
            
            dc.fishProductionDict.put(FishType.ZHUANGJIA, (new ItemStack(Material.GOLD_INGOT, 2)).serialize());
            dc.fishProductionDict.put(FishType.HEJIN, (new ItemStack(Material.IRON_INGOT, 2)).serialize());
            
            dc.fishProductionDict.put(FishType.SANSHA, (new ItemStack(Material.COAL, 2)).serialize());
            dc.fishProductionDict.put(FishType.DONGHAI, (new ItemStack(Material.GRANITE, 5)).serialize());
            dc.fishProductionDict.put(FishType.BAODAO, (new ItemStack(Material.DIORITE, 5)).serialize());
            dc.fishProductionDict.put(FishType.BOHAI, (new ItemStack(Material.ANDESITE, 5)).serialize());
            
            dc.fishProductionDict.put(FishType.WEIZHI, (new ItemStack(Material.EXPERIENCE_BOTTLE, 2)).serialize());
            
            dc.fishProductionDict.put(FishType.YOULING, (new ItemStack(Material.GHAST_TEAR, 2)).serialize());
            dc.fishProductionDict.put(FishType.SHENMI, (new ItemStack(Material.DRAGON_BREATH, 2)).serialize());
            
            dc.fishProductionDict.put(FishType.WEIZHI, (new ItemStack(Material.EMERALD, 2)).serialize());
            
            dc.fishProductionDict.put(FishType.ZHENZHU, (new ItemStack(Material.DIAMOND, 1)).serialize());
            dc.fishProductionDict.put(FishType.ZUANSHI, (new ItemStack(Material.DIAMOND, 1)).serialize());
            
            dc.fishProductionDict.put(FishType.HAIREN, (new ItemStack(Material.TRIDENT, 1)).serialize());
        }
        return dc;
    }
}
