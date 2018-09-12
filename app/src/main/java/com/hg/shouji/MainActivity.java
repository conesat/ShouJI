package com.hg.shouji;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hg.shouji.adapter.MsgAdapter;
import com.hg.shouji.entity.Msg;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ListView msgList;//信息列表
    private ViewPager viewPager;
    private int[] imageResIds;
    private ArrayList<ImageView> imageViewList;
    private LinearLayout ll_point_container;
    private String[] contentDescs;
    private TextView tv_desc;
    boolean isRunning = false;

    //设置按钮
    private ImageView settings;//


    //声明ViewPager
    private ViewPager mViewpager;

    //声明四个Tab
    private LinearLayout mTabHome;
    private LinearLayout mTabMsg;
    private LinearLayout mTabPlus;
    private LinearLayout mTabMy;

    //四个图标ImageView
    private ImageView mHomeImg;
    private ImageView mMsgImg;
    private ImageView mPlusImg;
    private ImageView mMyImg;

    //四个文本text
    private TextView mHoneText;
    private TextView mMsgText;
    private TextView mPlusText;
    private TextView mMyText;


    //声明ViewPager的适配器
    private PagerAdapter mAdpater;
    //用于装载四个Tab的List
    private List<View> mTabs = new ArrayList<View>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //去掉TitleBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initViews();//初始化控件
        initDatas();//初始化数据
        initEvents();//初始化事件

        // 开启轮询
        new Thread() {
            public void run() {
                isRunning = true;
                while (isRunning) {
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 往下跳一位
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        }
                    });
                }
            }

            ;
        }.start();


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }


    private void initEvents() {
        //设置四个Tab的点击事件
        mTabHome.setOnClickListener(this);
        mTabMsg.setOnClickListener(this);
        mTabPlus.setOnClickListener(this);
        mTabMy.setOnClickListener(this);
        settings.setOnClickListener(this);

        //添加ViewPager的切换Tab的监听事件
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //获取ViewPager的当前Tab
                int currentItem = mViewpager.getCurrentItem();
                //将所以的ImageButton设置成灰色
                resetImgs();
                //将当前Tab对应的ImageButton设置成绿色
                switch (currentItem) {
                    case 0:
                        mHomeImg.setImageResource(R.drawable.home_1);
                        mHoneText.setTextColor(Color.rgb(20, 151, 219));
                        break;
                    case 1:
                        mMsgImg.setImageResource(R.drawable.msg_1);
                        mMsgText.setTextColor(Color.rgb(20, 151, 219));
                        break;
                    case 2:
                        mPlusImg.setImageResource(R.drawable.plus_1);
                        mPlusText.setTextColor(Color.rgb(20, 151, 219));
                        break;
                    case 3:
                        mMyImg.setImageResource(R.drawable.my_1);
                        mMyText.setTextColor(Color.rgb(20, 151, 219));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initDatas() {
        //初始化ViewPager的适配器
        mAdpater = new PagerAdapter() {
            @Override
            public int getCount() {
                return mTabs.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = mTabs.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mTabs.get(position));
            }
        };
        //设置ViewPager的适配器
        mViewpager.setAdapter(mAdpater);


        // 图片资源id数组
        imageResIds = new int[]{R.drawable.test, R.drawable.test, R.drawable.test, R.drawable.test, R.drawable.test};

        // 文本描述
        contentDescs = new String[]{
                "巩俐不低俗，我就不能低俗",
                "扑树又回来啦！再唱经典老歌引万人大合唱歌引万人大合唱歌引万人大合唱",
                "揭秘北京电影如何升级",
                "乐视网TV版大派送",
                "热血屌丝的反杀"
        };

        // 初始化要展示的5个ImageView
        imageViewList = new ArrayList<ImageView>();

        ImageView imageView;
        View pointView;
        LinearLayout.LayoutParams layoutParams;
        for (int i = 0; i < imageResIds.length; i++) {
            // 初始化要显示的图片对象
            imageView = new ImageView(this);
            Picasso.with(getBaseContext()).load(imageResIds[i]).transform(new RoundTransform(20)).into(imageView);
            imageViewList.add(imageView);

            // 加小白点, 指示器
            pointView = new View(this);
            if (i == 0) {
                pointView.setBackgroundResource(R.drawable.point);
            } else {
                pointView.setBackgroundResource(R.drawable.point_1);
            }
            layoutParams = new LinearLayout.LayoutParams(15, 15);
            if (i != 0)
                layoutParams.leftMargin = 10;
            // 设置默认所有都不可用
            pointView.setEnabled(false);
            ll_point_container.addView(pointView, layoutParams);
        }


        ll_point_container.getChildAt(0).setEnabled(true);
        tv_desc.setText(contentDescs[0]);
        // 设置适配器
        viewPager.setAdapter(new MyAdapter());

        // 默认设置到中间的某个位置
        //int pos = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % imageViewList.size());
        // 2147483647 / 2 = 1073741823 - (1073741823 % 5)
        viewPager.setCurrentItem(5000000); // 设置到某个位置


    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        // 3. 指定复用的判断逻辑, 固定写法
        @Override
        public boolean isViewFromObject(View view, Object object) {
//			System.out.println("isViewFromObject: "+(view == object));
            // 当划到新的条目, 又返回来, view是否可以被复用.
            // 返回判断规则
            return view == object;
        }

        // 1. 返回要显示的条目内容, 创建条目
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int newPosition = position % imageViewList.size();

            ImageView imageView = imageViewList.get(newPosition);
            // a. 把View对象添加到container中
            try {
                container.addView(imageView);
            } catch (Exception e) {

            }
            // b. 把View对象返回给框架, 适配器
            return imageView; // 必须重写, 否则报异常
        }

        // 2. 销毁条目
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // object 要销毁的对象
            container.removeView((View) object);
        }
    }


    //初始化控件
    private void initViews() {
        mViewpager = (ViewPager) findViewById(R.id.id_viewpager);

        mTabHome = (LinearLayout) findViewById(R.id.id_tab_home);
        mTabMsg = (LinearLayout) findViewById(R.id.id_tab_msg);
        mTabPlus = (LinearLayout) findViewById(R.id.id_tab_plus);
        mTabMy = (LinearLayout) findViewById(R.id.id_tab_my);

        mHomeImg = (ImageView) findViewById(R.id.img_tab_home);
        mMsgImg = (ImageView) findViewById(R.id.img_tab_msg);
        mPlusImg = (ImageView) findViewById(R.id.img_tab_plus);
        mMyImg = (ImageView) findViewById(R.id.img_tab_my);

        mHoneText = (TextView) findViewById(R.id.text_tab_home);
        mMsgText = (TextView) findViewById(R.id.text_tab_msg);
        mPlusText = (TextView) findViewById(R.id.text_tab_plus);
        mMyText = (TextView) findViewById(R.id.text_tab_my);


        //获取到四个Tab
        LayoutInflater inflater = LayoutInflater.from(this);
        View tab1 = inflater.inflate(R.layout.tab1, null);
        View tab2 = inflater.inflate(R.layout.tab2, null);
        View tab3 = inflater.inflate(R.layout.tab3, null);
        View tab4 = inflater.inflate(R.layout.tab4, null);

        settings = (ImageView) tab1.findViewById(R.id.home_settings);

        //将四个Tab添加到集合中
        mTabs.add(tab1);
        mTabs.add(tab2);
        mTabs.add(tab3);
        mTabs.add(tab4);

        viewPager = (ViewPager) tab1.findViewById(R.id.viewpager);
        viewPager.setOnPageChangeListener(this);// 设置页面更新监听
//		viewPager.setOffscreenPageLimit(1);// 左右各保留几个对象
        ll_point_container = (LinearLayout) tab1.findViewById(R.id.ll_point_container);

        tv_desc = (TextView) tab1.findViewById(R.id.tv_desc);


        //消息列表
        msgList = (ListView) tab2.findViewById(R.id.msg_list);

        List<Msg> list = new ArrayList<>();
        list.add(new Msg());
        list.add(new Msg());
        list.add(new Msg());
        list.add(new Msg());
        list.add(new Msg());
        list.add(new Msg());
        list.add(new Msg());
        list.add(new Msg());
        list.add(new Msg());

        msgList.setAdapter(new MsgAdapter(list, this));
    }

    @Override
    public void onClick(View v) {
        //先将四个ImageButton都设置成灰色
        resetImgs();
        switch (v.getId()) {
            case R.id.id_tab_home:
                //设置viewPager的当前Tab
                mViewpager.setCurrentItem(0);
                //将当前Tab对应的ImageButton设置成绿色
                mHomeImg.setImageResource(R.drawable.home_1);
                mHoneText.setTextColor(Color.rgb(18, 150, 219));
                break;
            case R.id.id_tab_msg:
                mViewpager.setCurrentItem(1);
                mMsgImg.setImageResource(R.drawable.msg_1);
                mMsgText.setTextColor(Color.rgb(18, 150, 219));
                break;
            case R.id.id_tab_plus:
                mViewpager.setCurrentItem(2);
                mPlusImg.setImageResource(R.drawable.plus_1);
                mPlusText.setTextColor(Color.rgb(18, 150, 219));
                break;
            case R.id.id_tab_my:
                mViewpager.setCurrentItem(3);
                mMyImg.setImageResource(R.drawable.my_1);
                mMyText.setTextColor(Color.rgb(18, 150, 219));
                break;

            case R.id.home_settings:
                startActivity(new Intent(MainActivity.this, Settings.class));
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
        // 滚动时调用
    }

    @Override
    public void onPageSelected(int position) {
        // 新的条目被选中时调用
        int newPosition = position % imageViewList.size();

        //设置文本
        tv_desc.setText(contentDescs[newPosition]);

        for (int i = 0; i < ll_point_container.getChildCount(); i++) {
            View childAt = ll_point_container.getChildAt(i);
            childAt.setBackgroundResource(R.drawable.point_1);
        }

        ll_point_container.getChildAt(newPosition).setBackgroundResource(R.drawable.point);

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // 滚动状态变化时调用
    }


    //将四个ImageButton设置成灰色
    private void resetImgs() {
        mHomeImg.setImageResource(R.drawable.home_0);
        mHoneText.setTextColor(Color.rgb(163, 163, 163));
        mMsgImg.setImageResource(R.drawable.msg_0);
        mMsgText.setTextColor(Color.rgb(163, 163, 163));
        mPlusImg.setImageResource(R.drawable.plus_0);
        mPlusText.setTextColor(Color.rgb(163, 163, 163));
        mMyImg.setImageResource(R.drawable.my_0);
        mMyText.setTextColor(Color.rgb(163, 163, 163));

    }
}
