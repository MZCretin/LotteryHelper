package com.cretin.www.lotteryhelper.model;

/**
 * Created by cretin on 2018/3/13.
 */

public class LotteryModel {


    /**
     * code : 1
     * msg : 数据返回成功
     * data : {"openCode":"01,03,06,10,11,29+16","code":"ssq","expect":"2018135","name":"双色球","time":"2018-11-18 21:18:20"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * openCode : 01,03,06,10,11,29+16
         * code : ssq
         * expect : 2018135
         * name : 双色球
         * time : 2018-11-18 21:18:20
         */

        private String openCode;
        private String code;
        private String expect;
        private String name;
        private String time;

        public String getOpenCode() {
            return openCode;
        }

        public void setOpenCode(String openCode) {
            this.openCode = openCode;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getExpect() {
            return expect;
        }

        public void setExpect(String expect) {
            this.expect = expect;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
