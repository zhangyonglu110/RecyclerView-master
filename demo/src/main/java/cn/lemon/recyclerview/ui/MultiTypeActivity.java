package cn.lemon.recyclerview.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import cn.lemon.recyclerview.R;
import cn.lemon.recyclerview.ui.bean.Consumption;
import cn.lemon.recyclerview.ui.bean.TextImage;
import cn.lemon.view.RefreshRecyclerView;
import cn.lemon.view.adapter.LoadMoreAction;
import cn.lemon.view.adapter.MultiTypeAdapter;
import cn.lemon.view.adapter.RefreshAction;

public class MultiTypeActivity extends AppCompatActivity {

    private RefreshRecyclerView mRecyclerView;
    private MultiTypeAdapter mAdapter;
    private Toolbar toolbar;
    private int mPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_type);

        mAdapter = new MultiTypeAdapter(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RefreshRecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setSwipeRefreshColors(0xFF437845,0xFFE44F98,0xFF2FAC21);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setRefreshAction(new RefreshAction() {
            @Override
            public void refresh() {
                getData(true);
            }
        });
        mRecyclerView.setLoadMoreAction(new LoadMoreAction() {
            @Override
            public void loadmore() {
                getData(false);
            }
        });
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.showSwipeRefresh();
                getData(true);
            }
        });
    }

    public void getData(final boolean isRefresh) {
        if (isRefresh) {
            mPage = 0;
        } else {
            mPage++;
        }
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isRefresh) {
                    mAdapter.clear();
                    mRecyclerView.dismissSwipeRefresh();
                }
                mAdapter.add(ImageViewHolder.class, getImageVirtualData());
                mAdapter.addAll(TextViewHolder.class, getTextVirtualData());
                mAdapter.addAll(TextImageViewHolder.class, getTextImageVirualData());
                mAdapter.addAll(CardRecordHolder.class, getRecordVirtualData());
                if (mPage >= 3) {
                    mAdapter.showNoMore();
                }
            }
        }, 1000);
    }

    public String getImageVirtualData() {
        return "http://i03.pictn.sogoucdn.com/3c28af542f2d49f7-fe9c78d2ff4ac332-73d7732e20e2fcfaa954979d623bcbe9_qq";
    }

    public String[] getTextVirtualData() {
        return new String[]{
                "算机相关知识科普博客还有他",
                "技术职级规律越来越摸"
        };
    }

    public TextImage[] getTextImageVirualData() {
        return new TextImage[]{
                new TextImage("http://i03.pictn.sogoucdn.com/3c28af542f2d49f7-9e7c5d699eaea93e-3f7e1bcc57cbe10e050bf58559b0d5ae_qq", "小猫咪"),
                new TextImage("http://i03.pictn.sogoucdn.com/3c28af542f2d49f7-8f0182a4cf50287e-ba43c5aef499c64e6a3297c5c500c7dc_qq", "那些年，我还是帅哥"),
                new TextImage("http://img02.sogoucdn.com/app/a/100520093/803d8006b5d521bb-2eb356b9e8bc4ae6-0725fb0ad48d8b3a32f08eb150380bba.jpg", "看片。。。")
        };
    }

    public Consumption[] getRecordVirtualData() {
        return new Consumption[]{
                new Consumption("Demo", "2015-12-18 12:09", "消费", 9.7f, 24.19f, "兴业源三楼"),
                new Consumption("Demo", "2015-12-18 12:09", "消费", 9.7f, 24.19f, "兴业源三楼"),
                new Consumption("Demo", "2015-12-18 12:09", "消费", 9.7f, 24.19f, "兴业源三楼"),
                new Consumption("Demo", "2015-12-18 12:09", "消费", 9.7f, 24.19f, "兴业源三楼")
        };
    }
}
