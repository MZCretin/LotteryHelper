package com.cretin.www.lotteryhelper.model;

/**
 * Created by cretin on 2018/3/13.
 */

public class LotteryModel {

    /**
     * showapi_res_error :
     * showapi_res_code : 0
     * showapi_res_body : {"result":{"expect":"2018027","timestamp":1520774300,"time":"2018-03-11 21:18:20","name":"双色球","code":"ssq","openCode":"02,07,09,14,18,28+05"},"ret_code":0}
     */

    private String showapi_res_error;
    private int showapi_res_code;
    private ShowapiResBodyBean showapi_res_body;

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public ShowapiResBodyBean getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(ShowapiResBodyBean showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public static class ShowapiResBodyBean {
        /**
         * result : {"expect":"2018027","timestamp":1520774300,"time":"2018-03-11 21:18:20","name":"双色球","code":"ssq","openCode":"02,07,09,14,18,28+05"}
         * ret_code : 0
         */

        private ResultBean result;
        private int ret_code;

        public ResultBean getResult() {
            return result;
        }

        public void setResult(ResultBean result) {
            this.result = result;
        }

        public int getRet_code() {
            return ret_code;
        }

        public void setRet_code(int ret_code) {
            this.ret_code = ret_code;
        }

        public static class ResultBean {
            /**
             * expect : 2018027
             * timestamp : 1520774300
             * time : 2018-03-11 21:18:20
             * name : 双色球
             * code : ssq
             * openCode : 02,07,09,14,18,28+05
             */

            private String expect;
            private int timestamp;
            private String time;
            private String name;
            private String code;
            private String openCode;

            public String getExpect() {
                return expect;
            }

            public void setExpect(String expect) {
                this.expect = expect;
            }

            public int getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(int timestamp) {
                this.timestamp = timestamp;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getOpenCode() {
                return openCode;
            }

            public void setOpenCode(String openCode) {
                this.openCode = openCode;
            }
        }
    }
}
