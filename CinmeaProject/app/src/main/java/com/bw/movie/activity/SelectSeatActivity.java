package com.bw.movie.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.api.App;
import com.bw.movie.custom.MoveSeatView;
import com.bw.movie.custom.SeatTable;

import java.util.ArrayList;
import java.util.List;

public class SelectSeatActivity extends AppCompatActivity {

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectseat);
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
        init();
    }

    private void init() {

        ttrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        PayPopSum.setText("微信支付"+s*App.SeatMoney+"元");
                    }
                });
                PayRb2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PayPopSum.setText("支付宝支付"+s*App.SeatMoney+"元");
                    }
                });
                PayPopDown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.setOutsideTouchable(true);
                popupWindow.showAtLocation(view1,Gravity.CENTER,0,-190);
            }
        });
    }

}
