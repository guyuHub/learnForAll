package com.example.learnForAll.reactive;

import lombok.Data;
import reactor.core.publisher.Mono;

public class Demo2 {
    public static void main(String[] args) {
        Demo2 demo2 = new Demo2();
        demo2.pickleByCommand("五花肉");
        System.out.println("===============================");
        demo2.pickleByReactive("鸡肉");
    }

    /**
     * 命令式腌制肉
     *
     * @param meat
     */
    public void pickleByCommand(String meat) {
        CleanedMeet cleanedMeet = new CleanedMeet(meat);
        MassagedMeet massagedMeet = new MassagedMeet(cleanedMeet);
        SaltMeet saltMeet = new SaltMeet(massagedMeet);
        saltMeet.end();
    }

    /**
     * 命令式腌制肉
     *
     * @param meat
     */
    public void pickleByReactive(String meat) {
        Mono.just(meat)
                .map(meatName->new CleanedMeet(meatName))
                .map(cleanedMeet-> new MassagedMeet(cleanedMeet))
                .map(massagedMeet->new SaltMeet(massagedMeet))
                .subscribe(data->data.end());
    }

    /**
     * 清洗好的肉
     */
    @Data
    public class CleanedMeet {
        private String meatName;

        public CleanedMeet(String meatName) {
            System.out.println("清洗肉" + meatName);
            this.meatName = meatName;
        }
    }

    /**
     * 按摩好的肉
     */
    @Data
    public class MassagedMeet {
        private CleanedMeet cleanedMeet;

        public MassagedMeet(CleanedMeet cleanedMeet) {
            System.out.println("按摩肉" + cleanedMeet.getMeatName());
            this.cleanedMeet = cleanedMeet;
        }
    }

    /**
     * 盐肉
     */
    public class SaltMeet {
        private MassagedMeet massagedMeet;

        public SaltMeet(MassagedMeet massagedMeet) {
            System.out.println("加盐肉" + massagedMeet.getCleanedMeet().getMeatName());
            this.massagedMeet = massagedMeet;
        }

        public void end(){
            System.out.println("腌制完毕");
        }
    }
}
