package com.example.learnForAll.reactive;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Demo {
    /**
     * 代加工店
     * 流程:
     * 1.清洗从客人手中获取菜原料
     * 2.烹饪
     * 3.装盘
     *
     * @param args
     */
    public static void main(String[] args) {
        //原料
        String[] material = new String[]{"肉", "西红柿", "马铃薯", "包菜"};

        Demo demo = new Demo();
        demo.processService(material[new Random().nextInt(2)]);
    }

    /**
     * 加工服务
     *
     * @param materialName
     * @return
     */
    public GoodDelicacies processService(String materialName) {
        //接单
        Material material = new Material(materialName);
        //清洗原料
        CleanMaterial cleanMaterial = cleanMaterial(material);
        //烹饪洗净后的原料
        Delicacies delicacies = cook(cleanMaterial);
        //装盘烹饪后的美食
        GoodDelicacies goodDelicacies = serve(delicacies);
        return goodDelicacies;
    }

    /**
     * 接单
     * @param materialName
     * @return
     */
    Material receiveOrder(String materialName){
        return new Material(materialName);
    }

    /**
     * 装盘
     *
     * @param delicacies
     * @return
     */
    private GoodDelicacies serve(Delicacies delicacies) {
        return new GoodDelicacies(delicacies);
    }

    /**
     * 烹饪
     *
     * @param cleanMaterial
     * @return
     */
    private Delicacies cook(CleanMaterial cleanMaterial) {
        return new Delicacies(cleanMaterial);
    }

    /**
     * 清洗原料
     *
     * @param material
     * @return
     */
    private CleanMaterial cleanMaterial(Material material) {
        return new CleanMaterial(material);
    }

    /**
     * 装盘后的美食
     */
    private class GoodDelicacies {
        public GoodDelicacies(Delicacies delicacies) {

        }
    }

    /**
     * 美食
     */
    private class Delicacies {
        public Delicacies(CleanMaterial cleanMaterial) {

        }
    }

    /**
     * 原料
     */
    private class Material {
        public Material(String materialName) {

        }

    }

    /**
     * 洗干净的原料
     */
    private class CleanMaterial {
        public CleanMaterial(Material material) {

        }
    }

}
