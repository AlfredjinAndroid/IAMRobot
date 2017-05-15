package com.alfredjin.iamrobot.app;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.alfredjin.iamrobot.R;
import com.alfredjin.iamrobot.activity.ChatActivity;
import com.alfredjin.iamrobot.activity.LoginActivity;
import com.alfredjin.iamrobot.activity.SettingActivity;
import com.alfredjin.iamrobot.activity.StudyActivity;
import com.alfredjin.iamrobot.activity.UserActivity;
import com.alfredjin.iamrobot.adapter.ChatMessageAdapter;
import com.alfredjin.iamrobot.bean.BaseResult;
import com.alfredjin.iamrobot.bean.ChatMessage;
import com.alfredjin.iamrobot.bean.CookBook;
import com.alfredjin.iamrobot.bean.CookbookResult;
import com.alfredjin.iamrobot.bean.LinkResult;
import com.alfredjin.iamrobot.bean.News;
import com.alfredjin.iamrobot.bean.NewsResult;
import com.alfredjin.iamrobot.bean.SongResult;
import com.alfredjin.iamrobot.bean.StudyResult;
import com.alfredjin.iamrobot.bean.TextResult;
import com.alfredjin.iamrobot.bean.User;
import com.alfredjin.iamrobot.dao.ChatMessageDao;
import com.alfredjin.iamrobot.dao.StudyDao;
import com.alfredjin.iamrobot.dao.UserDao;
import com.alfredjin.iamrobot.http.HttpManager;
import com.alfredjin.iamrobot.service.RobotService;
import com.alfredjin.iamrobot.utils.ContentUtil;
import com.alfredjin.iamrobot.utils.DataUtils;
import com.alfredjin.iamrobot.utils.LogUtil;
import com.alfredjin.iamrobot.utils.NetworkUtils;
import com.alfredjin.iamrobot.utils.SharedPreferenceUtil;
import com.alfredjin.iamrobot.utils.TTSUtil;
import com.alfredjin.iamrobot.view.MyToast;
import com.alfredjin.iamrobot.view.PopView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView mMsgs;
    private ChatMessageAdapter mAdapter;
    private List<ChatMessage> mDatas;
    private EditText mInputMsg;
    private Button mSendButton;
    private View bottomView;

    private ChatMessageDao chatMessageDao;
    private UserDao userDao;
    private StudyDao studyDao;
    private PopupWindow popupWindow;

    private Button btnMore;

    private String userName;
    private TTSUtil ttsUtil;

    private static final float r = 400f;//偏移量
    //控件数组
    int[] ids = {R.id.img_setting_base,
            R.id.img_setting_logout, R.id.img_setting_chat,
            R.id.img_setting_setting, R.id.img_setting_user,
            R.id.img_setting_xunlian, R.id.img_setting_cancle};
    List<ImageView> imgList;//存放需要操作的控件的集合
    private boolean isShow = false;//菜单是否正在显示的标识

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0xAAA) {
                showToat("网络不给力");
                ChatMessage fromMessage = new ChatMessage();
                fromMessage.setDate(new Date());
                fromMessage.setMsgType(ContentUtil.RESULT_CODE_TEXT);
                fromMessage.setType(ChatMessage.Type.INCOMING);
                fromMessage.setMsg("网络不给力，查不到数据");
                chatMessageDao.insert(fromMessage);
                mDatas.add(fromMessage);
                mAdapter.notifyDataSetChanged();
            } else if (msg.what == 0xBBB) {
                //等待接收子线程数据返回
                ChatMessage fromMessage = new ChatMessage();
                BaseResult baseResult = (BaseResult) msg.obj;
                fromMessage.setDate(new Date());
                fromMessage.setType(ChatMessage.Type.INCOMING);
                fromMessage.setName(userName);
                fromMessage.setState(1);
                if (baseResult instanceof TextResult) {
                    fromMessage.setMsg(((TextResult) baseResult).getText());
                    fromMessage.setMsgType(ContentUtil.RESULT_CODE_TEXT);
                }
                if (baseResult instanceof LinkResult) {
                    fromMessage.setMsg(((LinkResult) baseResult).getText() + "\n" + ((LinkResult) baseResult).getUrl());
                    fromMessage.setMsgType(ContentUtil.RESULT_CODE_LINK);
                }
                if (baseResult instanceof NewsResult) {
                    List<News> newsList = ((NewsResult) baseResult).getList();
                    fromMessage.setMsg(newsList.get(0).getArticle());
                    fromMessage.setMsgType(ContentUtil.RESULT_CODE_NEWS);
                    fromMessage.setDate(new Date());
                    fromMessage.setImageUrl(newsList.get(0).getIcon());
                    LogUtil.e("获取新闻的URL == " + newsList.get(0).getDetailurl());
                    fromMessage.setNewsUrl(newsList.get(0).getDetailurl());
                }
                if (baseResult instanceof CookbookResult) {
                    List<CookBook> cookBooks = ((CookbookResult) baseResult).getList();
                    fromMessage.setCookName(cookBooks.get(0).getName());
                    fromMessage.setCookInfo(cookBooks.get(0).getInfo());
                    fromMessage.setCookUrl(cookBooks.get(0).getDetailurl());
                    fromMessage.setMsgType(ContentUtil.RESULT_CODE_COOK);
                    LogUtil.e("菜名:" + cookBooks.get(0).getName());
                    LogUtil.e("材料" + cookBooks.get(0).getInfo());
                    LogUtil.e("链接地址" + cookBooks.get(0).getDetailurl());
                }
                if (baseResult instanceof SongResult) {
                    fromMessage.setMsg(((SongResult) baseResult).getText());
                }
                fromMessage.setState(1);
                fromMessage.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            LogUtil.i("数据上传成功");
                        } else {
                            LogUtil.w("数据上传失败");
                        }
                    }
                });
                chatMessageDao.insert(fromMessage);
                mDatas.add(fromMessage);
                mAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        setContentView(R.layout.activity_main);
        chatMessageDao = new ChatMessageDao(this);
        initView();
        initData();
        initListener();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        userDao = new UserDao(this);
        studyDao = new StudyDao(this);
        mSendButton = (Button) findViewById(R.id.btn_send_msg);
        mInputMsg = (EditText) findViewById(R.id.et_input_msg);
        mMsgs = (ListView) findViewById(R.id.lv_msgs);
        btnMore = (Button) findViewById(R.id.btn_add_more);
        bottomView = findViewById(R.id.rl_bottom);
        ImageView empty_img = (ImageView) findViewById(R.id.lv_empty_img);
        mMsgs.setEmptyView(empty_img);
        //初始化设置菜单的所有控件
        imgList = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            // 查找控件
            ImageView img = (ImageView) findViewById(ids[i]);
            img.setOnClickListener(this);// 设置控件的监听
            imgList.add(img);// 将控件添加到集合中
        }
        ttsUtil = TTSUtil.getInstance(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        userName = SharedPreferenceUtil.getString(ContentUtil.USERNAME, null, this);
        mDatas = new ArrayList<>();
        ChatMessage defaultMsg = new ChatMessage("你好，俺是萌萌哒机器人", ChatMessage.Type.INCOMING, new Date());
        defaultMsg.setMsgType(ContentUtil.RESULT_CODE_TEXT);
        mDatas.add(defaultMsg);
        mAdapter = new ChatMessageAdapter(this, mDatas);
        mMsgs.setAdapter(mAdapter);
        User user = userDao.findByName(userName);
        if (user.getRobotName() == null) {
            cacheUser(user);
        }
        //成功进入APP后就开启服务功能
        startService(new Intent(this, RobotService.class));
    }

    /**
     * 缓存用户信息
     */
    private void cacheUser(final User user) {
        new BmobQuery<User>().findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        User u = list.get(i);
                        if (u.getUserName().equals(user.getUserName())) {
                            userDao.insert(u);
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
        if (isShow){
            hideMenu();
            isShow = false;
        }
    }

    /**
     * 初始化事件
     */
    private void initListener() {
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String toMsg = mInputMsg.getText().toString().trim();
                if (TextUtils.isEmpty(toMsg)) {
                    Toast.makeText(MainActivity.this, "发送内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                searchMsg(toMsg);
            }
        });
        mMsgs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChatMessage chatMessage = mDatas.get(position);
                if (chatMessage.getMsgType() == ContentUtil.RESULT_CODE_LINK) {
                    String msg = chatMessage.getMsg();
                    String[] strings = msg.split("\n");
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri parse = Uri.parse(strings[1]);
                    intent.setData(parse);
                    startActivity(intent);
                }
                if (chatMessage.getMsgType() == ContentUtil.RESULT_CODE_NEWS) {
                    String url = chatMessage.getNewsUrl();
                    LogUtil.e("url == " + url);
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri parse = Uri.parse(url);
                    intent.setData(parse);
                    startActivity(intent);
                }
                if (chatMessage.getMsgType() == ContentUtil.RESULT_CODE_COOK) {
                    String cookUrl = chatMessage.getCookUrl();
                    LogUtil.e("url == " + cookUrl);
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");

                    Uri parse = Uri.parse(cookUrl);
                    intent.setData(parse);
                    startActivity(intent);
                }
            }
        });

        mMsgs.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                PopView.getInstance().showPop(MainActivity.this, parent, position, mDatas, mAdapter);
                return true;
            }
        });

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddPopView();
            }
        });

    }

    /**
     * 显示更多的PopView
     */
    private void showAddPopView() {

        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            return;
        }
        View view = View.inflate(this, R.layout.popview_more, null);
        int widthPixels = this.getResources().getDisplayMetrics().widthPixels;
        int heightPixels = this.getResources().getDisplayMetrics().heightPixels;
        popupWindow = new PopupWindow(view, widthPixels, heightPixels / 4);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);
        int height = bottomView.getHeight();//获取底边高度
        popupWindow.showAtLocation(bottomView, Gravity.BOTTOM, 0, height);
        initPopListener(view);
    }

    /**
     * PopWindow菜单事件处理
     */
    private void initPopListener(View view) {
        Button news = (Button) view.findViewById(R.id.more_news);
        Button caidan = (Button) view.findViewById(R.id.more_caidan);
        Button tianqi = (Button) view.findViewById(R.id.more_tianqi);
        Button xiaohua = (Button) view.findViewById(R.id.more_xiaohua);
        Button gushi = (Button) view.findViewById(R.id.gushi);
        Button chengyu = (Button) view.findViewById(R.id.chengyu);
        Button raokouling = (Button) view.findViewById(R.id.raokouling);
        Button hangban = (Button) view.findViewById(R.id.hangban);
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMsg("新闻");
                popupWindow.dismiss();
            }
        });
        caidan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInputMsg.setText("怎么做");
                popupWindow.dismiss();
            }
        });
        tianqi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInputMsg.setText("郑州天气");
                popupWindow.dismiss();
            }
        });
        xiaohua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMsg("笑话");
                popupWindow.dismiss();
            }
        });
        gushi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMsg("故事");
                popupWindow.dismiss();
            }
        });
        chengyu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInputMsg.setText("成语接龙");
                popupWindow.dismiss();
            }
        });
        raokouling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMsg("绕口令");
                popupWindow.dismiss();
            }
        });
        hangban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInputMsg.setText("明天郑州到北京航班");
                popupWindow.dismiss();
            }
        });

    }

    /**
     * 查询消息
     *
     * @param toMsg
     */
    private void searchMsg(String toMsg) {
        ChatMessage toMessage = sendMsg(toMsg);
        StudyResult studyResult = getLocal(toMsg);
        if (studyResult != null) {
            TextResult textResult = new TextResult();
            textResult.setText(studyResult.getAnswer());
            Message message = Message.obtain();
            message.obj = textResult;
            message.what = 0xBBB;
            mHandler.sendMessage(message);
        } else {
            toInternet(toMsg, toMessage);
        }
    }

    /**
     * 发送数据
     *
     * @param toMsg
     * @return
     */
    private ChatMessage sendMsg(String toMsg) {
        ChatMessage toMessage = new ChatMessage();
        toMessage.setDate(new Date());
        toMessage.setMsg(toMsg);
        toMessage.setType(ChatMessage.Type.OUTCOMING);
        toMessage.setName(userName);
        toMessage.setState(1);
        toMessage.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    LogUtil.i("数据上传成功");
                }
            }
        });
        chatMessageDao.insert(toMessage);
        mDatas.add(toMessage);
        mAdapter.notifyDataSetChanged();
        mInputMsg.setText("");
        return toMessage;
    }

    /**
     * 从网络请求数据
     *
     * @param toMsg
     * @param toMessage
     */
    private void toInternet(String toMsg, ChatMessage toMessage) {
        if (!NetworkUtils.getInstance().isNetworkAvailable(MainActivity.this)) {
            mDatas.add(new ChatMessage("主人请先连接网络", ChatMessage.Type.INCOMING,
                    new Date()));
            mAdapter.notifyDataSetChanged();
            showToat("网络未连接");
        }
        HttpManager.getInstance().get(toMsg, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.e("网络真心不给力");
                mHandler.sendEmptyMessage(0xAAA);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtil.e("接收到的消息是 ==== " + string);
                Gson gson = new Gson();
                BaseResult baseResult = gson.fromJson(string, BaseResult.class);
                BaseResult bean = DataUtils.jsonToBean(gson, string, baseResult);
                Message message = Message.obtain();
                message.obj = bean;
                message.what = 0xBBB;
                mHandler.sendMessage(message);
            }
        });
    }

    /**
     * MyToast
     *
     * @param msg
     */
    private void showToat(String msg) {
        MyToast.makeText(MainActivity.this, msg, MyToast.LENGTH_SHORT).show();
    }

    /**
     * 点击事件处理
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_setting_base:// 显示和隐藏菜单
                if (isShow) {// 正在显示
                    hideMenu();
                    isShow = false;
                } else {// 隐藏状态
                    showMenu();
                    isShow = true;
                }
                break;
            case R.id.img_setting_logout://登出
                logout();
                break;
            case R.id.img_setting_setting://设置
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
            case R.id.img_setting_chat://聊天记录
                startActivity(new Intent(MainActivity.this, ChatActivity.class));
                break;
            case R.id.img_setting_xunlian://训练
                startActivity(new Intent(MainActivity.this, StudyActivity.class));
                break;
            case R.id.img_setting_user://用户中心
                startActivity(new Intent(MainActivity.this, UserActivity.class));
                break;
            case R.id.img_setting_cancle://cancel
                if (isShow) {// 正在显示
                    hideMenu();
                    isShow = false;
                }
                break;
        }

    }

    /**
     * 注销操作
     */
    private void logout() {
        SharedPreferenceUtil.putBoolean(ContentUtil.TO_LOGIN, true, this);
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        MainActivity.this.finish();
    }

    /**
     * 显示菜单
     */
    private void showMenu() {
        for (int i = 1; i < imgList.size(); i++) {
            // 计算偏移量
            float y = (float) (r * Math.cos(Math.toRadians(18.0 * (i - 1))));
            float x = (float) (r * Math.sin(Math.toRadians(18.0 * (i - 1))));
            // 设置动画
            ObjectAnimator animatorY = ObjectAnimator.ofFloat(imgList.get(i), "translationY", 0f, y);
            ObjectAnimator animatorX = ObjectAnimator.ofFloat(imgList.get(i), "translationX", 0f, -x);
            // 开始延时
            animatorY.setStartDelay(100);
            animatorX.setStartDelay(100);
            // 设置动画集
            AnimatorSet set = new AnimatorSet();
            // 两个动画同时执行
            set.playTogether(animatorY, animatorX);
            // 延时
            set.setDuration(300);
            // 开始
            set.start();
        }
    }

    /**
     * 隐藏菜单
     */
    private void hideMenu() {
        for (int i = 1; i < imgList.size(); i++) {
            float y = (float) (r * Math.cos(Math.toRadians(18.0 * (i - 1))));
            float x = (float) (r * Math.sin(Math.toRadians(18.0 * (i - 1))));
            ObjectAnimator animatorY = ObjectAnimator.ofFloat(imgList.get(i), "translationY", y, 0f);
            ObjectAnimator animatorX = ObjectAnimator.ofFloat(imgList.get(i), "translationX", -x, 0f);
            animatorY.setStartDelay(100);
            animatorX.setStartDelay(100);
            AnimatorSet set = new AnimatorSet();
            set.playTogether(animatorY, animatorX);
            set.setDuration(300);
            set.start();
        }

    }

    /**
     * 读取用户的训练内容
     *
     * @param toMsg
     * @return
     */
    private StudyResult getLocal(String toMsg) {
        StudyResult studyResult = null;
        List<StudyResult> all = studyDao.findAll();
        String userName = SharedPreferenceUtil.getString(ContentUtil.USERNAME, null, this);
        for (StudyResult result : all) {
            if (result.getUser().equals(userName)) {
                if (result.getQuestion().equals(toMsg)) {
                    studyResult = new StudyResult();
                    studyResult.setQuestion(result.getQuestion());
                    studyResult.setAnswer(result.getAnswer());
                }
            }
        }
        return studyResult;
    }

    /**
     * 无论是否在朗读，都要在应用退出的时候关闭朗读
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ttsUtil.destroy();
    }
}
