package com.bw.movie.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bw.movie.R;
import com.bw.movie.api.App;
import com.bw.movie.bean.PayTrueBean;
import com.bw.movie.bean.WTrueBean;
import com.bw.movie.bean.ZfbBean;
import com.bw.movie.contract.ContractInterface;
import com.bw.movie.custom.MoveSeatView;
import com.bw.movie.custom.SeatTable;
import com.bw.movie.presenter.Presenter;
import com.bw.movie.util.EncryptUtil;
import com.bw.movie.zfbapi.AuthResult;
import com.bw.movie.zfbapi.PayResult;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SelectSeatActivity extends AppCompatActivity implements ContractInterface.PayTrueView ,ContractInterface.WeiXinPayTrue,ContractInterface.ZfbView {

    private SeatTable seatTable;
    int s = 0;
    private TextView textView;
    private LinearLayout linearLayout;
    private LinearLayout linearLayout1;
    private ImageView ttrue;
    private ImageView tFalse;
    private PopupWindow popupWindow;
    private ImageView PayPopDown;
    private RadioGroup PayRg;
    private RadioButton PayRb1;
    private RadioButton PayRb2;
    private TextView PayPopSum;
    int type;//支付类型
    String OrderId;

    /** 支付宝支付业务：入参app_id */
    public static final String APPID = "wxb3852e6a6b7d9516";

    /** 支付宝账户登录授权业务：入参pid值 */
    public static final String PID = "";
    /** 支付宝账户登录授权业务：入参target_id值 */
    public static final String TARGET_ID = "";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
    public static final String RSA2_PRIVATE = "";
    public static final String RSA_PRIVATE = "";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(SelectSeatActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SelectSeatActivity.this,GoodsActivity.class);
                        overridePendingTransition(R.anim.anim_right,R.anim.anim_left);
                        startActivity(intent);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(SelectSeatActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(SelectSeatActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(SelectSeatActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }
    };


    ContractInterface.PresenterInterface presenterInterface;

    // APP_ID 替换为你的应用从官方网站申请到的合法appID
    private static final String APP_ID = "wxb3852e6a6b7d9516";

    // IWXAPI 是第三方app和微信通信的openApi接口
    private IWXAPI api;
    private Runnable payRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectseat);
        presenterInterface = new Presenter<>(this);
        seatTable = findViewById(R.id.MoveSeat_id);
        textView = findViewById(R.id.SeatName);
        linearLayout1 = findViewById(R.id.LinPayTicket);
        linearLayout1.bringToFront();
        final TextView Money = findViewById(R.id.PayMoney);
        ttrue = findViewById(R.id.PayTrue);
        tFalse = findViewById(R.id.PayFalse);
        linearLayout = findViewById(R.id.LinPay);
        linearLayout.bringToFront();
        textView.setText(App.StartTime+"----"+App.EndTime+"                  "+App.CinemaHall);
        //没有宽度高度
        //Log.e("tag",seatTable.getWidth()+"----"+seatTable.getHeight());
        seatTable.setScreenName(App.CinemaHall);//设置屏幕名称
        seatTable.setMaxSelected(4);//设置最多选中
        seatTable.setSeatChecker(new SeatTable.SeatChecker() {
            @Override
            public boolean isValidSeat(int row, int column) {
                if(row == 4) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                if(row==6&&column==6){
                    return true;
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {
                //Log.e("tag",row+"----"+column);
                s++;
                Money.setText(s*App.SeatMoney+"");
            }

            @Override
            public void unCheck(int row, int column) {
                //Log.e("tag",row+"****"+column);
                s--;
                // Log.e("tag","选中票数----"+s);
                Money.setText(s*App.SeatMoney+"");
            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        int s = App.DrawSeat/10;
        seatTable.setData(s,10);
        regToWx();
        init();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SelectSeatActivity.this,ScheduleActivity.class);
        overridePendingTransition(R.anim.anim_right,R.anim.anim_left);
        startActivity(intent);
        finish();
    }

    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);
        // 将应用的appId注册到微信
        api.registerApp(APP_ID);
    }

    private void init() {
        tFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectSeatActivity.this,ScheduleActivity.class);
                overridePendingTransition(R.anim.anim_right,R.anim.anim_left);
                startActivity(intent);
                finish();
            }
        });
        ttrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String signMd = App.userId+""+App.ScheduleId+""+s+"movie";
                String sign =  md5Decode(signMd);
                presenterInterface.PayPresenter(App.ScheduleId,s,sign);
                View view1 = View.inflate(SelectSeatActivity.this,R.layout.layout_topaypop,null);
                popupWindow = new PopupWindow(view1,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,false);
                PayPopDown = view1.findViewById(R.id.PayPopDown);
                PayRg = view1.findViewById(R.id.PayRg);
                PayRb1 = view1.findViewById(R.id.PayRb1);
                PayRb2 = view1.findViewById(R.id.PayRb2);
                PayPopSum = view1.findViewById(R.id.PayPopSum);

                PayRb1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        type = 1;
                        PayPopSum.setText("微信支付"+s*App.SeatMoney+"元");
                    }
                });
                PayRb2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        type = 2;
                        PayPopSum.setText("支付宝支付"+s*App.SeatMoney+"元");
                    }
                });
                PayPopDown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });

                PayPopSum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(type == 1){
                            presenterInterface.WeiXinPayTrueP(type,OrderId);
                            popupWindow.dismiss();
                        }else{
                            presenterInterface.ZfbPresenter(type,OrderId);
                            Thread payThread = new Thread(payRunnable);
                            payThread.start();
                            popupWindow.dismiss();
                        }
                    }
                });
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.setOutsideTouchable(true);
                popupWindow.showAtLocation(view1,Gravity.CENTER,0,-190);
            }
        });
    }

    @Override
    public void returnPay(PayTrueBean payTrueBean) {
        OrderId = payTrueBean.getOrderId();
        Toast.makeText(SelectSeatActivity.this,payTrueBean.getMessage(),Toast.LENGTH_SHORT).show();
    }

    public String md5Decode(String content) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(content.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException",e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException", e);
        }
        //对生成的16字节数组进行补零操作
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10){
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }


    @Override
    public void returnTrue(WTrueBean wTrueBean) {
        PayReq req = new PayReq();
        req.appId = wTrueBean.getAppId();
        req.partnerId = wTrueBean.getPartnerId();
        req.prepayId = wTrueBean.getPrepayId();
        req.nonceStr = wTrueBean.getNonceStr();
        req.timeStamp = wTrueBean.getTimeStamp();
        req.packageValue = wTrueBean.getPackageValue();
        req.sign = wTrueBean.getSign();
        req.extData = "app data"; // optional
        api.sendReq(req);
        //Intent intent  = new Intent(SelectSeatActivity.this,MoiveDetailsActivity.class);
        //startActivity(intent);
    }

    @Override
    public void returnZfb(Object obj) {
        Gson gson = new Gson();
        final ZfbBean zfbBean = gson.fromJson(obj.toString(),ZfbBean.class);
        final String orderInfo = zfbBean.getResult();
        payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(SelectSeatActivity.this);
                Map<String,String> result = alipay.payV2(orderInfo,true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
    }
}
