/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.zhehe.FishR;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import me.zhehe.FishR.Type.FishSpecial;
import me.zhehe.FishR.Type.FishType;
import me.zhehe.FishR.Type.WaterType;

/**
 *
 * @author Zhehe
 */
public class Fusion {
    public static class FusionIndex {
        public FishType FusionA;
        public FishType FusionB;
        
        public FusionIndex(FishType A, FishType B) {
            FusionA = A;
            FusionB = B;
        }
        
        @Override
        public int hashCode() {
            int A = FusionA.getValue();
            int B = FusionB.getValue();
            if(A > B) {
                int tmp = B;
                B = A;
                A = tmp;
            }
            
            String str = Integer.toString(A) + "#" + Integer.toString(B);
            return str.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final FusionIndex other = (FusionIndex) obj;
            if (this.FusionA != other.FusionA) {
                return false;
            }
            return this.FusionB == other.FusionB;
        }
    }
    public static class FusionResult {
        public int chance; //total 1000
        public FishType type;
        public int life;
        public int pro;
        public int temp_adap;
        public int oxygen_adap;
        public WaterType water;
        public FishSpecial special;
        public boolean dark;
    }
    
    private static final Fusion INSTANCE = new Fusion();
    private Fusion() {
        initFusionDict();
    }
    
    public static Map<FusionIndex, List<FusionResult>> FUSION_DICT;
    
    private static void addToFusionDict(FusionIndex index, FusionResult result) {
        List<FusionResult> sub;
        if(FUSION_DICT.containsKey(index)) {
            sub = FUSION_DICT.get(index);
        } else {
            sub = new ArrayList<>();
        }
        sub.add(result);
        FUSION_DICT.put(index, sub);
    }
    
    private static void initFusionDict() {
        FUSION_DICT = new HashMap<>();
        FusionIndex index;
        FusionResult result;
        
        //CHONGLANG
        index = new FusionIndex(FishType.PINGFAN, FishType.PUSU);
        result = new FusionResult();
        {
            result.chance = 100;
            result.type = FishType.CHONGLANG;
            result.dark = false;
            result.life = 2;
            result.pro = 2;
            result.temp_adap = 4;
            result.oxygen_adap = 4;
            result.water = WaterType.SWEET;
            result.special = FishSpecial.NONE;
        }
        addToFusionDict(index, result);
        
        //QIANSHUI
        index = new FusionIndex(FishType.PINGFAN, FishType.PUSU);
        result = new FusionResult();
        {
            result.chance = 100;
            result.type = FishType.QIANSHUI;
            result.dark = true;
            result.life = 2;
            result.pro = 2;
            result.temp_adap = 3;
            result.oxygen_adap = 5;
            result.water = WaterType.SALT;
            result.special = FishSpecial.NONE;
        }
        addToFusionDict(index, result);
        
        //TOUTIE
        index = new FusionIndex(FishType.PINGFAN, FishType.PUSHI);
        result = new FusionResult();
        {
            result.chance = 100;
            result.type = FishType.TOUTIE;
            result.dark = false;
            result.life = 0;
            result.pro = 1;
            result.temp_adap = 4;
            result.oxygen_adap = 4;
            result.water = WaterType.SWEET;
            result.special = FishSpecial.NONE;
        }
        addToFusionDict(index, result);
        
        //TIAOYUE
        index = new FusionIndex(FishType.PUSU, FishType.PUSHI);
        result = new FusionResult();
        {
            result.chance = 100;
            result.type = FishType.TIAOYUE;
            result.dark = false;
            result.life = 5;
            result.pro = 4;
            result.temp_adap = 5;
            result.oxygen_adap = 6;
            result.water = WaterType.SWEET;
            result.special = FishSpecial.NONE;
        }
        addToFusionDict(index, result);
        
        //JILIU
        index = new FusionIndex(FishType.CHONGLANG, FishType.QIANSHUI);
        result = new FusionResult();
        {
            result.chance = 100;
            result.type = FishType.JILIU;
            result.dark = false;
            result.life = 3;
            result.pro = 4;
            result.temp_adap = 2;
            result.oxygen_adap = 3;
            result.water = WaterType.SWEET;
            result.special = FishSpecial.NONE;
        }
        addToFusionDict(index, result);
        
        //JIYOU
        index = new FusionIndex(FishType.CHONGLANG, FishType.QIANSHUI);
        result = new FusionResult();
        {
            result.chance = 100;
            result.type = FishType.JIYOU;
            result.dark = false;
            result.life = 6;
            result.pro = 5;
            result.temp_adap = 0;
            result.oxygen_adap = 7;
            result.water = WaterType.SALT;
            result.special = FishSpecial.NONE;
        }
        addToFusionDict(index, result);
        
        //NILIU
        index = new FusionIndex(FishType.TOUTIE, FishType.TIAOYUE);
        result = new FusionResult();
        {
            result.chance = 100;
            result.type = FishType.NILIU;
            result.dark = false;
            result.life = 1;
            result.pro = 6;
            result.temp_adap = 1;
            result.oxygen_adap = 8;
            result.water = WaterType.SALT;
            result.special = FishSpecial.NONE;
        }
        addToFusionDict(index, result);
        
        //JIANYUE
        index = new FusionIndex(FishType.TOUTIE, FishType.TIAOYUE);
        result = new FusionResult();
        {
            result.chance = 100;
            result.type = FishType.JIANYUE;
            result.dark = false;
            result.life = 3;
            result.pro = 0;
            result.temp_adap = 6;
            result.oxygen_adap = 2;
            result.water = WaterType.SWEET;
            result.special = FishSpecial.NONE;
        }
        addToFusionDict(index, result);
        
        //YUANGU
        index = new FusionIndex(FishType.PUSU, FishType.QIANSHUI);
        result = new FusionResult();
        {
            result.chance = 100;
            result.type = FishType.YUANGU;
            result.dark = true;
            result.life = 6;
            result.pro = 0;
            result.temp_adap = 0;
            result.oxygen_adap = 0;
            result.water = WaterType.SALT;
            result.special = FishSpecial.NONE;
        }
        addToFusionDict(index, result);
        index = new FusionIndex(FishType.PINGFAN, FishType.QIANSHUI);
        addToFusionDict(index, result);
        index = new FusionIndex(FishType.PUSHI, FishType.QIANSHUI);
        addToFusionDict(index, result);
        
        //DITU
        index = new FusionIndex(FishType.PUSU, FishType.QIANSHUI);
        result = new FusionResult();
        {
            result.chance = 100;
            result.type = FishType.DITU;
            result.dark = false;
            result.life = 6;
            result.pro = 0;
            result.temp_adap = 8;
            result.oxygen_adap = 0;
            result.water = WaterType.SALT;
            result.special = FishSpecial.NONE;
        }
        addToFusionDict(index, result);
        index = new FusionIndex(FishType.PINGFAN, FishType.QIANSHUI);
        addToFusionDict(index, result);
        index = new FusionIndex(FishType.PUSHI, FishType.QIANSHUI);
        addToFusionDict(index, result);
        
        //SHIZU
        index = new FusionIndex(FishType.PUSU, FishType.QIANSHUI);
        result = new FusionResult();
        {
            result.chance = 100;
            result.type = FishType.SHIZU;
            result.dark = false;
            result.life = 6;
            result.pro = 0;
            result.temp_adap = 8;
            result.oxygen_adap = 0;
            result.water = WaterType.SALT;
            result.special = FishSpecial.HULI;
        }
        addToFusionDict(index, result);
        index = new FusionIndex(FishType.PINGFAN, FishType.QIANSHUI);
        addToFusionDict(index, result);
        index = new FusionIndex(FishType.PUSHI, FishType.QIANSHUI);
        addToFusionDict(index, result);
        
        //CHENCHUAN
        index = new FusionIndex(FishType.JILIU, FishType.JIYOU);
        result = new FusionResult();
        {
            result.chance = 100;
            result.type = FishType.CHENCHUAN;
            result.dark = false;
            result.life = 0;
            result.pro = 1;
            result.temp_adap = 0;
            result.oxygen_adap = 1;
            result.water = WaterType.SWEET;
            result.special = FishSpecial.NONE;
        }
        addToFusionDict(index, result);
        
        //BAOZANG
        index = new FusionIndex(FishType.JILIU, FishType.JIYOU);
        result = new FusionResult();
        {
            result.chance = 100;
            result.type = FishType.BAOZANG;
            result.dark = true;
            result.life = 0;
            result.pro = 1;
            result.temp_adap = 8;
            result.oxygen_adap = 0;
            result.water = WaterType.SALT;
            result.special = FishSpecial.NONE;
        }
        addToFusionDict(index, result);
        
        //HUASHI
        index = new FusionIndex(FishType.YUANGU, FishType.SHIZU);
        result = new FusionResult();
        {
            result.chance = 100;
            result.type = FishType.HUASHI;
            result.dark = true;
            result.life = 0;
            result.pro = 0;
            result.temp_adap = 6;
            result.oxygen_adap = 8;
            result.water = WaterType.SWEET;
            result.special = FishSpecial.NONE;
        }
        addToFusionDict(index, result);
        
        //HAIREN
        index = new FusionIndex(FishType.YUANGU, FishType.NILIU);
        result = new FusionResult();
        {
            result.chance = 100;
            result.type = FishType.HAIREN;
            result.dark = false;
            result.life = 6;
            result.pro = 2;
            result.temp_adap = 4;
            result.oxygen_adap = 8;
            result.water = WaterType.SALT;
            result.special = FishSpecial.XIDAPUBEN;
        }
        addToFusionDict(index, result);
        
        //DENGLONG
        index = new FusionIndex(FishType.CHONGLANG, FishType.TOUTIE);
        result = new FusionResult();
        {
            result.chance = 100;
            result.type = FishType.DENGLONG;
            result.dark = true;
            result.life = 1;
            result.pro = 1;
            result.temp_adap = 6;
            result.oxygen_adap = 7;
            result.water = WaterType.SWEET;
            result.special = FishSpecial.YANHUOWANHUI;
        }
        addToFusionDict(index, result);
        
        //HUAYANG
        index = new FusionIndex(FishType.CHONGLANG, FishType.TIAOYUE);
        result = new FusionResult();
        {
            result.chance = 100;
            result.type = FishType.HUAYANG;
            result.dark = false;
            result.life = 5;
            result.pro = 3;
            result.temp_adap = 4;
            result.oxygen_adap = 7;
            result.water = WaterType.SWEET;
            result.special = FishSpecial.YUANDING;
        }
        addToFusionDict(index, result);
        
        //MEILI
        index = new FusionIndex(FishType.HUAYANG, FishType.HAIREN);
        result = new FusionResult();
        {
            result.chance = 100;
            result.type = FishType.MEILI;
            result.dark = false;
            result.life = 2;
            result.pro = 1;
            result.temp_adap = 4;
            result.oxygen_adap = 4;
            result.water = WaterType.SALT;
            result.special = FishSpecial.DABAOJIAO;
        }
        addToFusionDict(index, result);
        
        //ZHUANGJIA
        index = new FusionIndex(FishType.TOUTIE, FishType.CHENCHUAN);
        result = new FusionResult();
        {
            result.chance = 100;
            result.type = FishType.ZHUANGJIA;
            result.dark = false;
            result.life = 0;
            result.pro = 1;
            result.temp_adap = 2;
            result.oxygen_adap = 8;
            result.water = WaterType.SWEET;
            result.special = FishSpecial.NONE;
        }
        addToFusionDict(index, result);
        
        //HEJIN
        index = new FusionIndex(FishType.TOUTIE, FishType.BAOZANG);
        result = new FusionResult();
        {
            result.chance = 100;
            result.type = FishType.HEJIN;
            result.dark = false;
            result.life = 0;
            result.pro = 1;
            result.temp_adap = 6;
            result.oxygen_adap = 8;
            result.water = WaterType.SWEET;
            result.special = FishSpecial.NONE;
        }
        addToFusionDict(index, result);
        
        //DONGHAI
        index = new FusionIndex(FishType.DITU, FishType.HAIREN);
        result = new FusionResult();
        {
            result.chance = 100;
            result.type = FishType.DONGHAI;
            result.dark = false;
            result.life = 3;
            result.pro = 1;
            result.temp_adap = 4;
            result.oxygen_adap = 6;
            result.water = WaterType.SALT;
            result.special = FishSpecial.GUOJIABAOZANG;
        }
        addToFusionDict(index, result);
        
        //SANSHA
        index = new FusionIndex(FishType.DITU, FishType.HAIREN);
        result = new FusionResult();
        {
            result.chance = 100;
            result.type = FishType.SANSHA;
            result.dark = false;
            result.life = 3;
            result.pro = 1;
            result.temp_adap = 6;
            result.oxygen_adap = 6;
            result.water = WaterType.SALT;
            result.special = FishSpecial.GUOJIABAOZANG;
        }
        addToFusionDict(index, result);
        
        //BAODAO
        index = new FusionIndex(FishType.DITU, FishType.HAIREN);
        result = new FusionResult();
        {
            result.chance = 100;
            result.type = FishType.BAODAO;
            result.dark = false;
            result.life = 3;
            result.pro = 1;
            result.temp_adap = 5;
            result.oxygen_adap = 6;
            result.water = WaterType.SALT;
            result.special = FishSpecial.GUOJIABAOZANG;
        }
        addToFusionDict(index, result);
        
        //BOHAI
        index = new FusionIndex(FishType.DITU, FishType.HAIREN);
        result = new FusionResult();
        {
            result.chance = 100;
            result.type = FishType.BOHAI;
            result.dark = false;
            result.life = 3;
            result.pro = 1;
            result.temp_adap = 2;
            result.oxygen_adap = 6;
            result.water = WaterType.SALT;
            result.special = FishSpecial.GUOJIABAOZANG;
        }
        addToFusionDict(index, result);
        
        //WEIZHI
        index = new FusionIndex(FishType.ZHUANGJIA, FishType.HEJIN);
        result = new FusionResult();
        {
            result.chance = 100;
            result.type = FishType.WEIZHI;
            result.dark = false;
            result.life = 6;
            result.pro = 0;
            result.temp_adap = 0;
            result.oxygen_adap = 0;
            result.water = WaterType.SWEET;
            result.special = FishSpecial.NONE;
        }
        addToFusionDict(index, result);
        
        //YOULING
        index = new FusionIndex(FishType.WEIZHI, FishType.HAIREN);
        result = new FusionResult();
        {
            result.chance = 100;
            result.type = FishType.YOULING;
            result.dark = false;
            result.life = 4;
            result.pro = 0;
            result.temp_adap = 4;
            result.oxygen_adap = 4;
            result.water = WaterType.SWEET;
            result.special = FishSpecial.NONE;
        }
        addToFusionDict(index, result);
        
        //SHENMI
        index = new FusionIndex(FishType.WEIZHI, FishType.HAIREN);
        result = new FusionResult();
        {
            result.chance = 100;
            result.type = FishType.SHENMI;
            result.dark = false;
            result.life = 4;
            result.pro = 0;
            result.temp_adap = 4;
            result.oxygen_adap = 4;
            result.water = WaterType.SWEET;
            result.special = FishSpecial.NONE;
        }
        addToFusionDict(index, result);
        
        //ZHENZHU
        index = new FusionIndex(FishType.DONGHAI, FishType.HAIREN);
        result = new FusionResult();
        {
            result.chance = 100;
            result.type = FishType.ZHENZHU;
            result.dark = false;
            result.life = 6;
            result.pro = 0;
            result.temp_adap = 4;
            result.oxygen_adap = 4;
            result.water = WaterType.SALT;
            result.special = FishSpecial.SHIDAI;
        }
        addToFusionDict(index, result);
        index = new FusionIndex(FishType.SANSHA, FishType.HAIREN);
        addToFusionDict(index, result);
        index = new FusionIndex(FishType.BOHAI, FishType.HAIREN);
        addToFusionDict(index, result);
        index = new FusionIndex(FishType.BAODAO, FishType.HAIREN);
        addToFusionDict(index, result);
        
        //ZUANSHI
        index = new FusionIndex(FishType.DONGHAI, FishType.HAIREN);
        result = new FusionResult();
        {
            result.chance = 100;
            result.type = FishType.ZUANSHI;
            result.dark = false;
            result.life = 6;
            result.pro = 0;
            result.temp_adap = 4;
            result.oxygen_adap = 4;
            result.water = WaterType.SALT;
            result.special = FishSpecial.SHIDAI;
        }
        addToFusionDict(index, result);
        index = new FusionIndex(FishType.SANSHA, FishType.HAIREN);
        addToFusionDict(index, result);
        index = new FusionIndex(FishType.BOHAI, FishType.HAIREN);
        addToFusionDict(index, result);
        index = new FusionIndex(FishType.BAODAO, FishType.HAIREN);
        addToFusionDict(index, result);
    }
    
    public static Fish fusion(final Fish A, final Fish B, final Random random, final boolean forceMut) {
        if(A == null || B == null) return null;
        //提取染色体
        FusionResult[] fr = { new FusionResult(), new FusionResult()};
        fr[0].type = A.type[random.nextInt(2)];
        fr[0].dark = A.dark[random.nextInt(2)];
        fr[0].life = A.life[random.nextInt(2)];
        fr[0].oxygen_adap = A.oxygen_adap[random.nextInt(2)];
        fr[0].pro = A.pro[random.nextInt(2)];
        fr[0].temp_adap = A.temp_adap[random.nextInt(2)];
        fr[0].water = A.water[random.nextInt(2)];
        fr[0].special = A.special[random.nextInt(2)];
        fr[1].type = B.type[random.nextInt(2)];
        fr[1].dark = B.dark[random.nextInt(2)];
        fr[1].life = B.life[random.nextInt(2)];
        fr[1].oxygen_adap = B.oxygen_adap[random.nextInt(2)];
        fr[1].pro = B.pro[random.nextInt(2)];
        fr[1].temp_adap = B.temp_adap[random.nextInt(2)];
        fr[1].water = B.water[random.nextInt(2)];
        fr[1].special = B.special[random.nextInt(2)];
        
        FusionIndex fi1 = new FusionIndex(fr[0].type, fr[1].type);
        FusionIndex fi2 = new FusionIndex(fr[1].type, fr[0].type);
        List<FusionResult> sub = null;
        if(FUSION_DICT.containsKey(fi1)) sub = FUSION_DICT.get(fi1);
        else if(FUSION_DICT.containsKey(fi2)) sub = FUSION_DICT.get(fi2);
        
        if(sub != null) {
            int len = sub.size();
            for(int i = 0; i < len; i++) {
                FusionResult tmp = sub.get(i);
                if(forceMut || random.nextInt(1000) < tmp.chance) {
                    int index = random.nextInt(2);
                    fr[index].type = tmp.type;
                    fr[index].dark = tmp.dark;
                    fr[index].life = tmp.life;
                    fr[index].oxygen_adap = tmp.oxygen_adap;
                    fr[index].pro = tmp.pro;
                    fr[index].special = tmp.special;
                    fr[index].temp_adap = tmp.temp_adap;
                    fr[index].water = tmp.water;
                    break;
                }
            }
        }
        
        Fish fish = new Fish();
        for(int i = 0; i < 2; i++) {
            fish.dark[i] = fr[i].dark;
            fish.life[i] = fr[i].life;
            fish.oxygen_adap[i] = fr[i].oxygen_adap;
            fish.pro[i] = fr[i].pro;
            fish.special[i] = fr[i].special;
            fish.temp_adap[i] = fr[i].temp_adap;
            fish.type[i] = fr[i].type;
            fish.water[i] = fr[i].water;
        }
        
        return fish;
    }
    
    public static void main(String[] args) {
        Fish A = new Fish();
        {
            A.type[0] = FishType.PUSHI;
            A.type[1] = FishType.PUSHI;
            A.life[0] = 5;
            A.life[1] = 5;
            A.pro[0] = 4;
            A.pro[1] = 4;
            A.temp_adap[0] = 1;
            A.temp_adap[1] = 1;
            A.oxygen_adap[0] = 6;
            A.oxygen_adap[1] = 6;
            A.water[0] = WaterType.SWEET;
            A.water[1] = WaterType.SWEET;
            A.dark[0] = true;
            A.dark[1] = true;
        }
        System.out.println(A.geneGetAsString());
        Fish B = new Fish();
        System.out.println(B.geneGetAsString());
        
        Fish f = fusion(A, B, new Random(), true);
        System.out.println(f.geneGetAsString());
        System.out.println(f.type[0]);
        System.out.println(f.type[1]);
    }
}
