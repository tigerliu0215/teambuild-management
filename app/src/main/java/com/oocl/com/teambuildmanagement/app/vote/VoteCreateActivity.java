package com.oocl.com.teambuildmanagement.app.vote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.oocl.com.teambuildmanagement.R;

public class VoteCreateActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_create);

        final LinearLayout mainLayout = (LinearLayout)findViewById(R.id.main);
        final LinearLayout minusL1 = (LinearLayout)findViewById(R.id.minusL1);
        final LinearLayout minusL2 = (LinearLayout)findViewById(R.id.minusL2);
        final LinearLayout plusL = (LinearLayout)findViewById(R.id.plusL);
        final LinearLayout bodyLayout = (LinearLayout)findViewById(R.id.bodyLayout);


        ImageButton minusBtn1 = (ImageButton)findViewById(R.id.minusBtn1);
        ImageButton minusBtn2 = (ImageButton)findViewById(R.id.minusBtn2);
        ImageButton plusBtn = (ImageButton)findViewById(R.id.plusBtn);
        final Button hiddenPlusBtn = (Button)findViewById(R.id.hiddenPlusBtn);

        minusBtn1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                bodyLayout.removeView(minusL1);
            }
        });
        minusBtn2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                bodyLayout.removeView(minusL2);
            }
        });
        plusBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageButton plusBtn = (ImageButton)findViewById(v.getId());
                plusBtn.setBackgroundResource(R.mipmap.round_close_fill);
                plusBtn.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImageButton plusBtn = (ImageButton)findViewById(v.getId());
                        LinearLayout layout = (LinearLayout)plusBtn.getParent();
                        bodyLayout.removeView(layout);
                    }
                });
                //todo click hidden button
                hiddenPlusBtn.performClick();
            }
        });


        hiddenPlusBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LinearLayout plusLayout = new LinearLayout(getApplicationContext());
                plusLayout.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams plusLayoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                bodyLayout.addView(plusLayout, plusLayoutParams);

                // newPlusBtn 加入到 plusLayout 中
                ImageButton newPlusBtn = new ImageButton(getApplicationContext());
                newPlusBtn.setId(View.generateViewId());
                LinearLayout.LayoutParams newPlusBtnLayoutParams=new LinearLayout.LayoutParams(80, 80);
                newPlusBtn.setLayoutParams(newPlusBtnLayoutParams);
//                imgbtn3.setImageDrawable(getResources().getDrawable(R.drawable.icon2));
//                newPlusBtn.setImageResource(R.mipmap.plus_btn);
                newPlusBtn.setBackgroundResource(R.mipmap.round_add);
                newPlusBtn.setScaleType(ImageButton.ScaleType.FIT_XY);
                plusLayout.addView(newPlusBtn);
                newPlusBtn.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImageButton plusBtn = (ImageButton)findViewById(v.getId());
                        plusBtn.setBackgroundResource(R.mipmap.round_close_fill);
                        plusBtn.setOnClickListener(new Button.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ImageButton plusBtn = (ImageButton)findViewById(v.getId());
                                LinearLayout layout = (LinearLayout)plusBtn.getParent();
                                bodyLayout.removeView(layout);
                            }
                        });
                        hiddenPlusBtn.performClick();
                    }
                });

                // EditText 加入到LinearLayout 中
                EditText editText = new EditText(getApplicationContext());
                LinearLayout.LayoutParams newEditTextLayoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                editText.setLayoutParams(newEditTextLayoutParams);
//                editText.setHint("添加新选项");
                plusLayout.addView(editText);

            }
        });
    }
}
