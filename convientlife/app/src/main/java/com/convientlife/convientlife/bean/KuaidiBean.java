package com.convientlife.convientlife.bean;

import java.util.List;

/*
 * Created by Administrator on 2019/3/11 0011.
 */
public class KuaidiBean {

    public KuaidiBean() {
    }

    /**
     * RESULT : {"com":"万象物流","context":[{"time":"2017-09-15 15:40:16","desc":"[拱宸站]派件确认;签收人:物业:ZPB反馈方式：app;签收方式：放自提点"},{"time":"2017-09-15 09:28:53","desc":"[拱宸站]王思梅:正在派件;联系电话:13082809190:ZPB"},{"time":"2017-09-15 09:10:53","desc":"拱宸站:已到站:ZPB"},{"time":"2017-09-15 06:42:33","desc":"[杭州分拣中心]万象杭州分拨中心:已出库:ZPB"},{"time":"2017-09-15 02:19:32","desc":"[杭州分拣中心]万象杭州分拨中心:已入库:ZPB"},{"time":"2017-09-15 00:29:15","desc":"[浙江中转中心]万象浙江分拨中心:已入库:ZPB"}]}
     * ERRORCODE : 0
     */

    private RESULTBean RESULT;
    private int ERRORCODE;

    public RESULTBean getRESULT() {
        return RESULT;
    }

    public void setRESULT(RESULTBean RESULT) {
        this.RESULT = RESULT;
    }

    public int getERRORCODE() {
        return ERRORCODE;
    }

    public void setERRORCODE(int ERRORCODE) {
        this.ERRORCODE = ERRORCODE;
    }

    public static class RESULTBean {
        /**
         * com : 万象物流
         * context : [{"time":"2017-09-15 15:40:16","desc":"[拱宸站]派件确认;签收人:物业:ZPB反馈方式：app;签收方式：放自提点"},{"time":"2017-09-15 09:28:53","desc":"[拱宸站]王思梅:正在派件;联系电话:13082809190:ZPB"},{"time":"2017-09-15 09:10:53","desc":"拱宸站:已到站:ZPB"},{"time":"2017-09-15 06:42:33","desc":"[杭州分拣中心]万象杭州分拨中心:已出库:ZPB"},{"time":"2017-09-15 02:19:32","desc":"[杭州分拣中心]万象杭州分拨中心:已入库:ZPB"},{"time":"2017-09-15 00:29:15","desc":"[浙江中转中心]万象浙江分拨中心:已入库:ZPB"}]
         */

        private String com;
        private List<ContextBean> context;

        public String getCom() {
            return com;
        }

        public void setCom(String com) {
            this.com = com;
        }

        public List<ContextBean> getContext() {
            return context;
        }

        public void setContext(List<ContextBean> context) {
            this.context = context;
        }

        public static class ContextBean {
            /**
             * time : 2017-09-15 15:40:16
             * desc : [拱宸站]派件确认;签收人:物业:ZPB反馈方式：app;签收方式：放自提点
             */

            private String time;
            private String desc;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }
    }
}
