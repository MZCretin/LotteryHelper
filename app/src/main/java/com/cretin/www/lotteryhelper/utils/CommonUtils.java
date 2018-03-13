package com.cretin.www.lotteryhelper.utils;

import com.cretin.www.lotteryhelper.model.ItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cretin on 2018/3/13.
 */

public class CommonUtils {

    public static void copyProperties(ItemModel desc, ItemModel orign) {
        if ( orign != null ) {
            desc.setType(orign.getType());
            desc.setResult(orign.getResult());
            desc.setList(orign.getList());
            desc.setIndex(orign.getIndex());
            desc.setKjq(orign.getKjq());
            desc.setNo1(orign.getNo1());
            desc.setNo2(orign.getNo2());
            desc.setNo3(orign.getNo3());
            desc.setNo4(orign.getNo4());
            desc.setNo5(orign.getNo5());
            desc.setNo6(orign.getNo6());
            desc.setNo7(orign.getNo7());
            desc.setNum(orign.getNum());
        }
    }

    public static String lotteryResult(int red, int blue) {
        /**
         * 一等奖：7个号码相符（6个红色球号码和1个蓝色球号码）（红色球号码顺序不限，下同）
         二等奖：6个红色球号码相符；
         三等奖：5个红色球号码和1个蓝色球号码相符；
         四等奖：5个红色球号码，或4个红色球号码和1个蓝色球号码相符；
         五等奖：4个红色球号码，或3个红色球号码和1个蓝色球号码相符；
         六等奖：1个蓝色球号码相符（有无红色球号码相符均可）。
         */
        String result = "未中奖";
        if ( blue > 0 ) {
            //篮号中了
            if ( red == 6 ) {
                //中大奖了
                result = "一等奖";
            } else if ( red == 5 ) {
                //中三等奖
                result = "三等奖";
            } else if ( red == 4 ) {
                //中 四等奖
                result = " 四等奖";
            } else if ( red == 3 ) {
                //中 四等奖
                result = " 五等奖";
            } else {
                //中 六等奖
                result = " 六等奖";
            }
        } else {
            //蓝号没有中
            if ( red == 6 ) {
                //中二等奖
                result = "二等奖";
            } else if ( red == 5 ) {
                //中四等奖
                result = "四等奖";
            } else if ( red == 4 ) {
                //中五等奖
                result = "五等奖";
            } else {
                //未中奖
                result = "未中奖";
            }
        }
        return result;
    }

    /**
     * 设置数据
     *
     * @param currIndex
     * @param itemModel
     * @param item
     */
    public static void setData(int currIndex, ItemModel itemModel, String item) {
        switch ( currIndex ) {
            case 1:
                itemModel.setNo1(item);
                break;
            case 2:
                itemModel.setNo2(item);
                break;
            case 3:
                itemModel.setNo3(item);
                break;
            case 4:
                itemModel.setNo4(item);
                break;
            case 5:
                itemModel.setNo5(item);
                break;
            case 6:
                itemModel.setNo6(item);
                break;
            case 7:
                itemModel.setNo7(item);
                break;
        }
    }

    /**
     * 恢复数据
     *
     * @param list
     */
    public static void resetData(List<ItemModel> list) {
        List<String> l = null;
        for ( ItemModel item :
                list ) {
            l = new ArrayList<>();
            l.add(item.getNo1());
            l.add(item.getNo2());
            l.add(item.getNo3());
            l.add(item.getNo4());
            l.add(item.getNo5());
            l.add(item.getNo6());
            item.setList(l);
        }
    }
}
