package com.anbang.qipai.game.web.fb;

import java.util.List;

public class PdkLawsFB {
    private String panshu;
    private String renshu;

    private String bichu = "false";//首出必须包含黑桃三
    private String biya = "false"; //能压牌必须压
    private String aBoom = "false";//三个当做炸弹
    private String sandaique = "false";//尾牌时三带可缺牌
    private String feijique = "false";//尾牌时飞机可缺牌

    private String showShoupaiNum = "false";//显示剩余手牌
    private String zhuaniao = "false";//红桃10抓鸟玩法


    public PdkLawsFB() {
    }

    public PdkLawsFB(List<String> lawNames) {
        lawNames.forEach((lawName) -> {
            if (lawName.equals("sj")) {// 10局
                panshu = "10";
            } else if (lawName.equals("esj")) {// 20局
                panshu = "20";
            } else if (lawName.equals("er")) {// 二人
                renshu = "2";
            } else if (lawName.equals("sr")) {// 三人
                renshu = "3";
            } else if (lawName.equals("bichu")) {
                bichu = "true";
            } else if (lawName.equals("biya")) {
                biya = "true";
            } else if (lawName.equals("aBoom")) {
                aBoom = "true";
            } else if (lawName.equals("sandaique")) {
                sandaique = "true";
            } else if (lawName.equals("feijique")) {
                feijique = "true";
            } else if (lawName.equals("showShoupaiNum")) {
                showShoupaiNum = "true";
            } else if (lawName.equals("zhuaniao")) {
                zhuaniao = "true";
            } else {

            }
        });
    }

    public int payForCreateRoom() {
        int gold = 100;
        if (panshu.equals("10")) {
            gold = 100;
        } else if (panshu.equals("20")) {
            gold = 200;
        } else {

        }
        return gold;
    }

    public String getPanshu() {
        return panshu;
    }

    public void setPanshu(String panshu) {
        this.panshu = panshu;
    }

    public String getRenshu() {
        return renshu;
    }

    public void setRenshu(String renshu) {
        this.renshu = renshu;
    }

    public String getBichu() {
        return bichu;
    }

    public void setBichu(String bichu) {
        this.bichu = bichu;
    }

    public String getBiya() {
        return biya;
    }

    public void setBiya(String biya) {
        this.biya = biya;
    }

    public String getaBoom() {
        return aBoom;
    }

    public void setaBoom(String aBoom) {
        this.aBoom = aBoom;
    }

    public String getSandaique() {
        return sandaique;
    }

    public void setSandaique(String sandaique) {
        this.sandaique = sandaique;
    }

    public String getFeijique() {
        return feijique;
    }

    public void setFeijique(String feijique) {
        this.feijique = feijique;
    }

    public String getShowShoupaiNum() {
        return showShoupaiNum;
    }

    public void setShowShoupaiNum(String showShoupaiNum) {
        this.showShoupaiNum = showShoupaiNum;
    }

    public String getZhuaniao() {
        return zhuaniao;
    }

    public void setZhuaniao(String zhuaniao) {
        this.zhuaniao = zhuaniao;
    }
}
