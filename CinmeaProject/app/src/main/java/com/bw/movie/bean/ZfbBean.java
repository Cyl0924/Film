package com.bw.movie.bean;

/**
 * @Author：Chen
 * @E-mail： 1850915912@qq.com
 * @Date：2019/3/15 11:29
 * @Description：描述信息
 */
public class ZfbBean {


    /**
     * result : alipay_sdk=alipay-sdk-java-3.1.0&app_id=2018080760951276&biz_content=%7B%22out_trade_no%22%3A%2220190517090805269%22%2C%22subject%22%3A%22%E5%85%AB%E7%BB%B4%E7%A7%BB%E5%8A%A8%E9%80%9A%E4%BF%A1%E5%AD%A6%E9%99%A2-%E7%BB%B4%E5%BA%A6%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.28%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fmobile.bwstudent.com%2FpayApiProd%2FaliPay%2FmovieNotification&sign=rTugohYc0r%2Fr7h240N7PDNj09KgytgFaUm%2FDsXlg92Gu%2BkixYIlg44Fd94biS%2BV0tk3hOgFwNdsD3TBysFIfmO%2B581N8tfTM6I4rlMpkuLzvLR8pmlpAr%2FauKJIEvi1xSOChXw6QRXwWkWFP0s4xUQImCjarTXeO59cfriFEJlYlWh%2FtmOWqxb%2FixqXkhNKRGJ3wYMIEkWqcANGp%2Ff75g%2FiMwKrPU2Y714Ax%2F9F%2BzEUy9%2Bu2wJQtm0yd1JcAWSMtx1DjQGr1owH7AlQ8QTvF814CxrDwpU%2FaFp3OmMFm0rZYuasYgTW1vbC%2BNWoPMyHuP5%2BiBlfVKWzk9hkXkqos3w%3D%3D&sign_type=RSA2&timestamp=2019-05-17+09%3A08%3A16&version=1.0
     * message : ok
     * status : 0000
     */

    private String result;
    private String message;
    private String status;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
