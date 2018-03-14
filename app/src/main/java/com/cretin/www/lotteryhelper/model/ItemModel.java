package com.cretin.www.lotteryhelper.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cretin on 2018/3/12.
 */

public class ItemModel implements MultiItemEntity {
    public static final int TYPE_NO = 0;
    public static final int TYPE_DONE = 1;
    public static final int TYPE_STEP3 = 2;
    public static final int TYPE_STEP4 = 3;
    //购买的7个号 钱6个为红号 第七个是蓝号
    private String no1;
    private String no2;
    private String no3;
    private String no4;
    private String no5;
    private String no6;
    private String no7;
    //索引 A.
    private String index;
    //购买数量
    private String num;
    //0 未检测 1  已检测
    private int type;
    //检测后的结果
    private String result;
    //开奖期
    private String kjq;

    //开奖号码
    private String openCode;

    //记录中奖的下表集合
    private List<Integer> indexs;

    public List<Integer> getIndexs() {
        return indexs;
    }

    public void setIndexs(List<Integer> indexs) {
        this.indexs = indexs;
    }

    public String getOpenCode() {
        return openCode;
    }

    public void setOpenCode(String openCode) {
        this.openCode = openCode;
    }

    public String getKjq() {
        return kjq;
    }

    private List<String> list;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setKjq(String kjq) {
        this.kjq = kjq;
    }

    public ItemModel() {
    }

    public ItemModel(int type,String openCode,String kjq) {
        this.type = type;
        this.openCode = openCode;
        this.kjq = kjq;
    }

    public ItemModel(int type, String kjq) {
        this.type = type;
        this.kjq = kjq;
    }

    //public static final int TYPE_NO = 0;专用
    public ItemModel(String no1, String no2, String no3, String no4, String no5, String no6, String no7, String index, String num) {
        this.no1 = no1;
        this.no2 = no2;
        this.no3 = no3;
        this.no4 = no4;
        this.no5 = no5;
        this.no6 = no6;
        this.no7 = no7;
        this.index = index;
        this.num = num;
        //数据包装
        List<String> l = new ArrayList<>();
        l.add(no1);
        l.add(no2);
        l.add(no3);
        l.add(no4);
        l.add(no5);
        l.add(no6);
        setList(l);
    }

    public String getNo1() {
        return no1;
    }

    public void setNo1(String no1) {
        this.no1 = no1;
    }

    public String getNo2() {
        return no2;
    }

    public void setNo2(String no2) {
        this.no2 = no2;
    }

    public String getNo3() {
        return no3;
    }

    public void setNo3(String no3) {
        this.no3 = no3;
    }

    public String getNo4() {
        return no4;
    }

    public void setNo4(String no4) {
        this.no4 = no4;
    }

    public String getNo5() {
        return no5;
    }

    public void setNo5(String no5) {
        this.no5 = no5;
    }

    public String getNo6() {
        return no6;
    }

    public void setNo6(String no6) {
        this.no6 = no6;
    }

    public String getNo7() {
        return no7;
    }

    public void setNo7(String no7) {
        this.no7 = no7;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public int getItemType() {
        return getType();
    }
}
