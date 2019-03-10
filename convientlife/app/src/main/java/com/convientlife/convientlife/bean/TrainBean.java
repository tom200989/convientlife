package com.convientlife.convientlife.bean;

import java.util.List;

/*
 * Created by Administrator on 2019/3/10 0010.
 */
public class TrainBean {
    /**
     * ERRORCODE : 0
     * RESULT : [{"from_station_name":"杭州东","start_station_name":"上海南","station_train_code":"K1371","rz_num":"","zy_num":"","gr_num":"","qt_num":"","rw_num":"无","arrive_time":"19:54","start_train_date":"20180824","start_time":"13:58","srrb_num":"","yw_num":"无","train_status":"预订","lishi":"05:56","to_station_name":"鄱阳","yz_num":"无","end_station_name":"九江","ze_num":"","tz_num":"","swz_num":"","wz_num":"有"},{"from_station_name":"杭州东","start_station_name":"杭州东","station_train_code":"G1461","rz_num":"","zy_num":"无","gr_num":"","qt_num":"","rw_num":"","arrive_time":"19:34","start_train_date":"20180824","start_time":"15:50","srrb_num":"","yw_num":"","train_status":"预订","lishi":"03:44","to_station_name":"鄱阳","yz_num":"","end_station_name":"南昌西","ze_num":"无","tz_num":"","swz_num":"无","wz_num":""}]
     */

    private String ERRORCODE;
    private List<RESULTBean> RESULT;

    public String getERRORCODE() {
        return ERRORCODE;
    }

    public void setERRORCODE(String ERRORCODE) {
        this.ERRORCODE = ERRORCODE;
    }

    public List<RESULTBean> getRESULT() {
        return RESULT;
    }

    public void setRESULT(List<RESULTBean> RESULT) {
        this.RESULT = RESULT;
    }

    public static class RESULTBean {
        
        /**
         * from_station_name : 杭州东
         * start_station_name : 上海南
         * station_train_code : K1371
         * rz_num : 
         * zy_num : 
         * gr_num : 
         * qt_num : 
         * rw_num : 无
         * arrive_time : 19:54
         * start_train_date : 20180824
         * start_time : 13:58
         * srrb_num : 
         * yw_num : 无
         * train_status : 预订
         * lishi : 05:56
         * to_station_name : 鄱阳
         * yz_num : 无
         * end_station_name : 九江
         * ze_num : 
         * tz_num : 
         * swz_num : 
         * wz_num : 有
         */

        private String from_station_name;
        private String start_station_name;
        private String station_train_code;
        private String rz_num;
        private String zy_num;
        private String gr_num;
        private String qt_num;
        private String rw_num;
        private String arrive_time;
        private String start_train_date;
        private String start_time;
        private String srrb_num;
        private String yw_num;
        private String train_status;
        private String lishi;
        private String to_station_name;
        private String yz_num;
        private String end_station_name;
        private String ze_num;
        private String tz_num;
        private String swz_num;
        private String wz_num;

        public String getFrom_station_name() {
            return from_station_name;
        }

        public void setFrom_station_name(String from_station_name) {
            this.from_station_name = from_station_name;
        }

        public String getStart_station_name() {
            return start_station_name;
        }

        public void setStart_station_name(String start_station_name) {
            this.start_station_name = start_station_name;
        }

        public String getStation_train_code() {
            return station_train_code;
        }

        public void setStation_train_code(String station_train_code) {
            this.station_train_code = station_train_code;
        }

        public String getRz_num() {
            return rz_num;
        }

        public void setRz_num(String rz_num) {
            this.rz_num = rz_num;
        }

        public String getZy_num() {
            return zy_num;
        }

        public void setZy_num(String zy_num) {
            this.zy_num = zy_num;
        }

        public String getGr_num() {
            return gr_num;
        }

        public void setGr_num(String gr_num) {
            this.gr_num = gr_num;
        }

        public String getQt_num() {
            return qt_num;
        }

        public void setQt_num(String qt_num) {
            this.qt_num = qt_num;
        }

        public String getRw_num() {
            return rw_num;
        }

        public void setRw_num(String rw_num) {
            this.rw_num = rw_num;
        }

        public String getArrive_time() {
            return arrive_time;
        }

        public void setArrive_time(String arrive_time) {
            this.arrive_time = arrive_time;
        }

        public String getStart_train_date() {
            return start_train_date;
        }

        public void setStart_train_date(String start_train_date) {
            this.start_train_date = start_train_date;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getSrrb_num() {
            return srrb_num;
        }

        public void setSrrb_num(String srrb_num) {
            this.srrb_num = srrb_num;
        }

        public String getYw_num() {
            return yw_num;
        }

        public void setYw_num(String yw_num) {
            this.yw_num = yw_num;
        }

        public String getTrain_status() {
            return train_status;
        }

        public void setTrain_status(String train_status) {
            this.train_status = train_status;
        }

        public String getLishi() {
            return lishi;
        }

        public void setLishi(String lishi) {
            this.lishi = lishi;
        }

        public String getTo_station_name() {
            return to_station_name;
        }

        public void setTo_station_name(String to_station_name) {
            this.to_station_name = to_station_name;
        }

        public String getYz_num() {
            return yz_num;
        }

        public void setYz_num(String yz_num) {
            this.yz_num = yz_num;
        }

        public String getEnd_station_name() {
            return end_station_name;
        }

        public void setEnd_station_name(String end_station_name) {
            this.end_station_name = end_station_name;
        }

        public String getZe_num() {
            return ze_num;
        }

        public void setZe_num(String ze_num) {
            this.ze_num = ze_num;
        }

        public String getTz_num() {
            return tz_num;
        }

        public void setTz_num(String tz_num) {
            this.tz_num = tz_num;
        }

        public String getSwz_num() {
            return swz_num;
        }

        public void setSwz_num(String swz_num) {
            this.swz_num = swz_num;
        }

        public String getWz_num() {
            return wz_num;
        }

        public void setWz_num(String wz_num) {
            this.wz_num = wz_num;
        }
    }
}
