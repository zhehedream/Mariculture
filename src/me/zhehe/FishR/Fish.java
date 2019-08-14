/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.zhehe.FishR;

import java.util.ArrayList;
import java.util.List;
import me.zhehe.Config.Config;
import me.zhehe.Util.Constant;
import me.zhehe.FishR.Type.FishSpecial;
import me.zhehe.FishR.Type.FishType;
import me.zhehe.FishR.Type.WaterType;
import org.bukkit.DyeColor;
import org.bukkit.entity.TropicalFish;

/**
 *
 * @author Zhehe
 */
public class Fish {
    public FishType[] type = { FishType.PINGFAN, FishType.PINGFAN };
    public int[] life = { 4, 4 }; //15 18 20 25 30 32 35
    public int[] pro = {3, 3}; //30 26 22 18 14 10 6
    public int[] temp_adap = {4, 4}; //0 1 2 3 4 5 6 7 8
    public int[] oxygen_adap = {4, 4}; //0 1 2 3 4 5 6 7 8
    public WaterType[] water = { WaterType.SWEET, WaterType.SWEET };
    public FishSpecial[] special = { FishSpecial.NONE, FishSpecial.NONE };
    public boolean gender = true;
    public boolean[] dark = { false, false };
    
    public long geneToLong() {
        long lifeA = this.life[0] & 0x7;
        long lifeB = this.life[1] & 0x7;
        long proA = this.pro[0] & 0x7;
        long proB = this.pro[1] & 0x7;
        
        long temp_adapA = this.temp_adap[0] & 0xF;
        long temp_adapB = this.temp_adap[1] & 0xF;
        long oxygen_adapA = this.oxygen_adap[0] & 0xF;
        long oxygen_adapB = this.oxygen_adap[1] & 0xF;
        long waterA = (WaterType.SALT == this.water[0]) ? 1 : 0;
        long waterB = (WaterType.SALT == this.water[1]) ? 1 : 0;
        long specialA = ((int)this.special[0].getValue()) & 0x7FFF;
        long specialB = ((int)this.special[1].getValue()) & 0x7FFF;
        
        long gender = this.gender ? 1 : 0;
        long darkA = this.dark[0] ? 1 : 0;
        long darkB = this.dark[1] ? 1 : 0;
        
        long res = lifeA | (lifeB << 3) | (proA << 6) | (proB << 9) | (temp_adapA << 12) | (temp_adapB << 16) | (oxygen_adapA << 20) | (oxygen_adapB << 24)
                | (waterA << 28) | (waterB << 29) | (specialA << 30) | (specialB << 45) | (gender << 60) | (darkA << 61) | (darkB << 62);
        return res;
    }
    
    public void setType(FishType A, FishType B) {
        type[0] = A;
        type[1] = B;
    }
    
    public void typeFromLong(long gene) {
        type[0] = FishType.fromId((int) ((gene >> 32) & 0xFFFFFFFF));
        type[1] = FishType.fromId((int) (gene & 0xFFFFFFFF));
    }
    
    public long typeToLong() {
        long A = type[0].getValue();
        long B = type[1].getValue();
        
        return (A << 32) | B;
    }
    
    public void geneFromLong(long gene) {
        life[0] = (int)(gene & 0x7);
        life[1] = (int)((gene >> 3) & 0x7);
        pro[0] = (int)((gene >> 6) & 0x7);
        pro[1] = (int)((gene >> 9) & 0x7);
        temp_adap[0] = (int)((gene >> 12) & 0xF);
        temp_adap[1] = (int)((gene >> 16) & 0xF);
        oxygen_adap[0] = (int)((gene >> 20) & 0xF);
        oxygen_adap[1] = (int)((gene >> 24) & 0xF);
        water[0] = ((gene >> 28) & 0x1) > 0 ? WaterType.SALT : WaterType.SWEET;
        water[1] = ((gene >> 29) & 0x1) > 0 ? WaterType.SALT : WaterType.SWEET;
        special[0] = FishSpecial.fromId((int)((gene >> 30) & 0x7FFF));
        special[1] = FishSpecial.fromId((int)((gene >> 45) & 0x7FFF));
        gender = ((gene >> 60) & 0x1) > 0;
        dark[0] = ((gene >> 61) & 0x1) > 0;
        dark[1] = ((gene >> 62) & 0x1) > 0;
    }
    
    public String geneGetAsString() {
        List<String> genes = getAsLore();
        StringBuilder sb = new StringBuilder();
        
        for(String str : genes) {
            sb.append(str);
            sb.append(',');
        }
        sb.deleteCharAt(sb.length() - 1);
        
        return sb.toString();
    }
    public List<String> getAsLore() {
        List<String> res = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        
        sb.append(Constant.LORE_COLOR);
        if(type[0] == type[1]) {
            sb.append(Config.instance.FISHTYPE_DICT.get(type[0]));
        } else {
            sb.append(Config.instance.FISHTYPE_DICT.get(type[0]));
            sb.append("-");
            sb.append(Config.instance.FISHTYPE_DICT.get(type[1]));
            sb.append(Config.instance.MUL_TYPE);
        }
        res.add(sb.toString());
        
        sb.setLength(0);
        sb.append(Constant.LORE_COLOR);
        sb.append(Config.instance.GENDER);
        sb.append(" : ");
        if(gender) sb.append(Config.instance.FEMALE);
        else sb.append(Config.instance.MALE);
        res.add(sb.toString());
        
        sb.setLength(0);
        sb.append(Constant.LORE_COLOR);
        sb.append(Config.instance.LIFE);
        sb.append(" : ");
        sb.append(Constant.RED);
        sb.append(Config.instance.LIFE_DICT.get(life[0]));
        sb.append(Constant.WHITE);
        sb.append('/');
        sb.append(Constant.BLUE);
        sb.append(Config.instance.LIFE_DICT.get(life[1]));
        res.add(sb.toString());
        
        sb.setLength(0);
        sb.append(Constant.LORE_COLOR);
        sb.append(Config.instance.PRO);
        sb.append(" : ");
        sb.append(Constant.RED);
        sb.append((pro[0] & 0x7));
        sb.append(Constant.WHITE);
        sb.append('/');
        sb.append(Constant.BLUE);
        sb.append(pro[1] & 0x7);
        res.add(sb.toString());
        
        sb.setLength(0);
        sb.append(Constant.LORE_COLOR);
        sb.append(Config.instance.TEMP_ADAP);
        sb.append(" : ");
        sb.append(Constant.RED);
        if(temp_adap[0] >= 0) sb.append('+');
        sb.append(temp_adap[0]);
        sb.append(Constant.WHITE);
        sb.append('/');
        sb.append(Constant.BLUE);
        if(temp_adap[1] >= 0) sb.append('+');
        sb.append(temp_adap[1]);
        res.add(sb.toString());
        
        sb.setLength(0);
        sb.append(Constant.LORE_COLOR);
        sb.append(Config.instance.OXYGEN_ADAP);
        sb.append(" : ");
        sb.append(Constant.RED);
        if(oxygen_adap[0] >= 0) sb.append('+');
        sb.append(oxygen_adap[0]);
        sb.append(Constant.WHITE);
        sb.append('/');
        sb.append(Constant.BLUE);
        if(oxygen_adap[1] >= 0) sb.append('+');
        sb.append(oxygen_adap[1]);
        res.add(sb.toString());
        
        sb.setLength(0);
        sb.append(Constant.LORE_COLOR);
        sb.append(Config.instance.WATER_TYPE);
        sb.append(" : ");
        sb.append(Constant.RED);
        if(water[0] == WaterType.SALT) sb.append(Config.instance.WATER_SALT);
        else sb.append(Config.instance.WATER_SWEET);
        sb.append(Constant.WHITE);
        sb.append('/');
        sb.append(Constant.BLUE);
        if(water[1] == WaterType.SALT) sb.append(Config.instance.WATER_SALT);
        else sb.append(Config.instance.WATER_SWEET);
        res.add(sb.toString());
        
        sb.setLength(0);
        sb.append(Constant.LORE_COLOR);
        sb.append(Config.instance.FISH_SPECIAL);
        sb.append(" : ");
        sb.append(Constant.RED);
        sb.append(Config.instance.FISHSPECIAL_DICT.get(special[0]));
        sb.append(Constant.WHITE);
        sb.append('/');
        sb.append(Constant.BLUE);
        sb.append(Config.instance.FISHSPECIAL_DICT.get(special[1]));
        res.add(sb.toString());
        
        sb.setLength(0);
        sb.append(Constant.LORE_COLOR);
        sb.append(Config.instance.DARK);
        sb.append(" : ");
        sb.append(Constant.RED);
        sb.append(dark[0] ? "yes" : "no");
        sb.append(Constant.WHITE);
        sb.append('/');
        sb.append(Constant.BLUE);
        sb.append(dark[1] ? "yes" : "no");
        res.add(sb.toString());
        
        return res;
    }
    public boolean fromLore(String str) {
        if(str == null || str.length() <= 6) return false;
        if(str.indexOf(Constant.DATA_TAG) == 0) {
            str = str.substring(Constant.DATA_TAG.length());
            String[] tags = str.split("/");
            if(tags.length != 2) return false;
            long types, gene;
            
            try {
                types = Long.parseLong(tags[0]);
                gene = Long.parseLong(tags[1]);
            } catch (NumberFormatException ex) {
                System.out.println(ex.toString());
                return false;
            }
            
            typeFromLong(types);
            geneFromLong(gene);
            
            return true;
        }
        return false;
    }
    
    public String toLore() {
        return Constant.DATA_TAG + typeToLong() + "/" + geneToLong();
    }
    
    public DyeColor getBodyColor​() {
        Long g = geneToLong();
        int hash = g.hashCode();
        DyeColor[] array = DyeColor.values();
        return array[Math.abs(hash) % array.length];
    }
    
    public DyeColor getPatternColor​() {
        int hash = Long.toString(geneToLong()).hashCode();
        DyeColor[] array = DyeColor.values();
        return array[Math.abs(hash) % array.length];
    }
    
    public TropicalFish.Pattern getPattern() {
        TropicalFish.Pattern[] array = TropicalFish.Pattern.values();
        return array[type[0].getValue() % array.length];
    }
    
    public static void main(String[] args) {
        Fish instance = new Fish();
        Long tmp;
        System.out.println(Long.toString(tmp = instance.geneToLong()));
        instance.geneFromLong(tmp);
        System.out.println(Long.toString(instance.geneToLong()));
        System.out.println(instance.getAsLore());
        System.out.println(instance.geneGetAsString());
    }
}
