package com.gingold.basisglide;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gingold.basisglidelibrary.BasisGlideUtils;

public class MainActivity extends Activity implements View.OnClickListener {
    private String url = "http://hr.zqlwl.com/upload/ehr/apps/pic/0010.jpeg";
    private Activity mActivity;
    private TextView tv_main_cache;
    private TextView tv_main_load;
    private TextView tv_main_circle;
    private TextView tv_main_download;
    private ImageView iv_main_pic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;

        tv_main_cache = (TextView) findViewById(R.id.tv_main_cache);
        tv_main_load = (TextView) findViewById(R.id.tv_main_load);
        tv_main_circle = (TextView) findViewById(R.id.tv_main_circle);
        tv_main_download = (TextView) findViewById(R.id.tv_main_download);
        iv_main_pic = (ImageView) findViewById(R.id.iv_main_pic);

        tv_main_cache.setOnClickListener(this);
        tv_main_load.setOnClickListener(this);
        tv_main_circle.setOnClickListener(this);
        tv_main_download.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_main_cache:

                break;
            case R.id.tv_main_load:
                BasisGlideUtils.load(mActivity, url, iv_main_pic);
                break;
            case R.id.tv_main_circle:

                break;
            case R.id.tv_main_download:

                break;
        }
    }
}
