package com.convientlife.convientlife.bean;

import java.util.List;

/*
 * Created by Administrator on 2019/3/11 0011.
 */
public class Weixinbean {

    public Weixinbean() {
    }

    /**
     * reason : 请求成功
     * result : {"list":[{"id":"3008050622_2650555965_1","title":"去了尼泊尔才知道，这里没有寡妇，女人公开沐浴，一生结三次婚！","source":"创业宝典","firstImg":"","mark":"","url":"https://mp.weixin.qq.com/s?__biz=MzAwODA1MDYyMg==&mid=2650555965&idx=1&sn=78f81b23be1a800f7030372ecedecadc&chksm=837c24ecb40badfa074ea6e42d259534e26e9961480b00ea5fded0c357ff34021154f79700d4"},{"id":"2397049020_2651410287_1","title":"又塌了！天佑碧桂园啊！","source":"CEO管理语录","firstImg":"","mark":"","url":"https://mp.weixin.qq.com/s?__biz=MjM5NzA0OTAyMA==&mid=2651410287&idx=1&sn=2e3170433a52e2107dfd1036ebc68d82&chksm=bd2208bc8a5581aab8567fd9ca9ce1c03ac4255bf802abbcc3353dc162a67b9baca2bafb234c"},{"id":"2395834812_2654909674_1","title":"再见铁饭碗！又一行业被颠覆！中国建设银行正式宣布","source":"总裁商业思维","firstImg":"","mark":"","url":"https://mp.weixin.qq.com/s?__biz=MjM5NTgzNDgxMg==&mid=2654909674&idx=1&sn=8039d6ab8fc98d251f2ddf16db73d70d&chksm=bd386a398a4fe32f44b444f22bf59cba4f0b564f4953ead357c1a7daea1e05341008124b6c94"},{"id":"3277012322_2649614938_1","title":"超级精品暑期档营销突围战，腾讯视频\u201c好时光夏日刷片季\u201d很青春","source":"公关界的007","firstImg":"","mark":"","url":"https://mp.weixin.qq.com/s?__biz=MzI3NzAxMjMyMg==&mid=2649614938&idx=1&sn=08d48cb5f565d079c3495ef00059a54f&chksm=f3758057c4020941cd99b571d86ed3cf92ef2c16df0849c9a0a962b845b0db3592af0306a141"},{"id":"2396021622_2658282010_2","title":"寒门女孩707分考入北大！她写的\u201c感谢贫穷\u201d一文看哭了所有人\u2026\u2026","source":"创业财经汇","firstImg":"","mark":"","url":"https://mp.weixin.qq.com/s?__biz=MjM5NjAyMTYyMg==&mid=2658282010&idx=2&sn=1cc3ce7e837ca9537a3ef142633ae9ae&chksm=bd691e038a1e97157afcde89010c39c3e4ec182ff544b4131e189a83a8197b25d0c5a2c6dedf"}],"totalPage":816,"ps":5,"pno":1}
     * error_code : 0
     */

    private String reason;
    private ResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        /**
         * list : [{"id":"3008050622_2650555965_1","title":"去了尼泊尔才知道，这里没有寡妇，女人公开沐浴，一生结三次婚！","source":"创业宝典","firstImg":"","mark":"","url":"https://mp.weixin.qq.com/s?__biz=MzAwODA1MDYyMg==&mid=2650555965&idx=1&sn=78f81b23be1a800f7030372ecedecadc&chksm=837c24ecb40badfa074ea6e42d259534e26e9961480b00ea5fded0c357ff34021154f79700d4"},{"id":"2397049020_2651410287_1","title":"又塌了！天佑碧桂园啊！","source":"CEO管理语录","firstImg":"","mark":"","url":"https://mp.weixin.qq.com/s?__biz=MjM5NzA0OTAyMA==&mid=2651410287&idx=1&sn=2e3170433a52e2107dfd1036ebc68d82&chksm=bd2208bc8a5581aab8567fd9ca9ce1c03ac4255bf802abbcc3353dc162a67b9baca2bafb234c"},{"id":"2395834812_2654909674_1","title":"再见铁饭碗！又一行业被颠覆！中国建设银行正式宣布","source":"总裁商业思维","firstImg":"","mark":"","url":"https://mp.weixin.qq.com/s?__biz=MjM5NTgzNDgxMg==&mid=2654909674&idx=1&sn=8039d6ab8fc98d251f2ddf16db73d70d&chksm=bd386a398a4fe32f44b444f22bf59cba4f0b564f4953ead357c1a7daea1e05341008124b6c94"},{"id":"3277012322_2649614938_1","title":"超级精品暑期档营销突围战，腾讯视频\u201c好时光夏日刷片季\u201d很青春","source":"公关界的007","firstImg":"","mark":"","url":"https://mp.weixin.qq.com/s?__biz=MzI3NzAxMjMyMg==&mid=2649614938&idx=1&sn=08d48cb5f565d079c3495ef00059a54f&chksm=f3758057c4020941cd99b571d86ed3cf92ef2c16df0849c9a0a962b845b0db3592af0306a141"},{"id":"2396021622_2658282010_2","title":"寒门女孩707分考入北大！她写的\u201c感谢贫穷\u201d一文看哭了所有人\u2026\u2026","source":"创业财经汇","firstImg":"","mark":"","url":"https://mp.weixin.qq.com/s?__biz=MjM5NjAyMTYyMg==&mid=2658282010&idx=2&sn=1cc3ce7e837ca9537a3ef142633ae9ae&chksm=bd691e038a1e97157afcde89010c39c3e4ec182ff544b4131e189a83a8197b25d0c5a2c6dedf"}]
         * totalPage : 816
         * ps : 5
         * pno : 1
         */

        private int totalPage;
        private int ps;
        private int pno;
        private List<ListBean> list;

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getPs() {
            return ps;
        }

        public void setPs(int ps) {
            this.ps = ps;
        }

        public int getPno() {
            return pno;
        }

        public void setPno(int pno) {
            this.pno = pno;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 3008050622_2650555965_1
             * title : 去了尼泊尔才知道，这里没有寡妇，女人公开沐浴，一生结三次婚！
             * source : 创业宝典
             * firstImg : 
             * mark : 
             * url : https://mp.weixin.qq.com/s?__biz=MzAwODA1MDYyMg==&mid=2650555965&idx=1&sn=78f81b23be1a800f7030372ecedecadc&chksm=837c24ecb40badfa074ea6e42d259534e26e9961480b00ea5fded0c357ff34021154f79700d4
             */

            private String id;
            private String title;
            private String source;
            private String firstImg;
            private String mark;
            private String url;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getFirstImg() {
                return firstImg;
            }

            public void setFirstImg(String firstImg) {
                this.firstImg = firstImg;
            }

            public String getMark() {
                return mark;
            }

            public void setMark(String mark) {
                this.mark = mark;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
