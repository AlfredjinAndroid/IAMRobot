package com.alfredjin.iamrobot.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.text.TextUtils;

import com.alfredjin.iamrobot.bean.ChatMessage;
import com.alfredjin.iamrobot.bean.StudyResult;
import com.alfredjin.iamrobot.bean.User;
import com.alfredjin.iamrobot.dao.ChatMessageDao;
import com.alfredjin.iamrobot.dao.StudyDao;
import com.alfredjin.iamrobot.dao.UserDao;
import com.alfredjin.iamrobot.receiver.AutoUpdateReceiver;
import com.alfredjin.iamrobot.utils.LogUtil;
import com.alfredjin.iamrobot.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class RobotService extends Service {
    private UserDao userDao;
    private ChatMessageDao chatMessageDao;
    private StudyDao studyDao;

    public RobotService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.e("启动服务");
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateDao();
            }
        }).start();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int tenMinute = 1000 * 60;//1分钟
        long triggerAtTime = SystemClock.elapsedRealtime() + tenMinute;
        Intent i = new Intent(this, AutoUpdateReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 更新数据
     */
    private void updateDao() {
        userDao = new UserDao(this);
        studyDao = new StudyDao(this);
        chatMessageDao = new ChatMessageDao(this);
        if (NetworkUtils.getInstance().isNetworkAvailable(this)
                && NetworkUtils.getInstance().isWifi(this)) {
            uploadData();
            cacheDate();
        } else {
            LogUtil.e("网络连接不稳定。。。");
        }

    }

    /**
     * 更新缓存数据
     */
    private void cacheDate() {
        new BmobQuery<User>().findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    LogUtil.e("更新用户缓存");
                    doCacheUser(list);
                }
            }
        });
        new BmobQuery<ChatMessage>().findObjects(new FindListener<ChatMessage>() {
            @Override
            public void done(List<ChatMessage> list, BmobException e) {
                if (e == null) {
                    LogUtil.e("更新聊天缓存");
                    doCacheChat(list);
                }
            }
        });
        new BmobQuery<StudyResult>().findObjects(new FindListener<StudyResult>() {
            @Override
            public void done(List<StudyResult> list, BmobException e) {
                if (e == null) {
                    LogUtil.e("更新学习缓存");
                    doCacheStudy(list);
                }
            }
        });
    }

    /**
     * 缓存学习记录
     *
     * @param list
     */
    private void doCacheStudy(List<StudyResult> list) {
        List<StudyResult> results = studyDao.findAll();
        List<StudyResult> resultList = new ArrayList<>();
        for (StudyResult studyResult : results) {
            if (studyResult.getState() == 1) {
                resultList.add(studyResult);
            }
        }
        if (resultList != null && list != null && resultList.size() > 0 && list.size() > 0) {
            int len = resultList.size() > list.size() ? list.size() : resultList.size();
            for (int i = 0; i < len; i++) {
                StudyResult localResult = resultList.get(i);
                StudyResult bmobResult = list.get(i);
                if (!bmobResult.getQuestion().equals(localResult.getQuestion())) {
                    studyDao.insert(bmobResult);
                }
            }
            if (list.size() > resultList.size()) {
                for (int i = len; i < list.size(); i++) {
                    StudyResult bmobResult = list.get(i);
                    studyDao.insert(bmobResult);
                }
            }
        }
    }

    /**
     * 缓存聊天记录
     *
     * @param list
     */
    private void doCacheChat(List<ChatMessage> list) {
        List<ChatMessage> all = chatMessageDao.findAll();
        List<ChatMessage> chatMessageList = new ArrayList<>();
        for (ChatMessage chatMessage : all) {
            if (chatMessage.getState() == 1) {
                chatMessageList.add(chatMessage);
            }
        }

        if (chatMessageList != null && chatMessageList.size() > 0
                && list != null && list.size() > 0) {
            int len = chatMessageList.size() > list.size() ? list.size() : chatMessageList.size();
            for (int i = 0; i < len; i++) {
                ChatMessage localResult = chatMessageList.get(i);
                ChatMessage bmobResult = list.get(i);
                if (!bmobResult.getMsg().equals(localResult.getMsg())) {
                    chatMessageDao.insert(bmobResult);
                }
            }
            if (list.size() > len) {
                for (int i = len; i < list.size(); i++) {
                    ChatMessage chatMessage = list.get(i);
                    chatMessageDao.insert(chatMessage);
                }
            }
        }
    }

    /**
     * 缓存用户操作
     *
     * @param list
     */
    private void doCacheUser(List<User> list) {
        //用户不能做缓存操作
    }

    /**
     * 上传本地数据信息
     */
    private void uploadData() {

        uploadUser();
        uploadChat();
        uploadStudy();

    }

    /**
     * 上传学习训练数据
     */
    private void uploadStudy() {
        List<StudyResult> result = studyDao.findToUpload();
        if (result != null && result.size() > 0) {
            for (final StudyResult studyResult : result) {

                studyResult.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            studyResult.setState(1);
                            studyDao.update(studyResult);
                        } else {
                            LogUtil.e("上传学习数据失败:" + e.getMessage());
                        }
                    }
                });
            }
        }
    }

    /**
     * 上传聊天内容
     */
    private void uploadChat() {
        List<ChatMessage> chatMessageList = chatMessageDao.findToUpload();
        if (chatMessageList != null && chatMessageList.size() > 0) {
            for (final ChatMessage chatMessage : chatMessageList) {
                chatMessage.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            LogUtil.i("上传聊天记录成功");
                            chatMessage.setState(1);
                            if (!TextUtils.isEmpty(chatMessage.getName())) {
                                chatMessageDao.update(chatMessage);
                            }
                        } else {
                            LogUtil.w("上传聊天记录失败:" + e.getMessage());
                        }
                    }
                });
            }
        }
    }

    /**
     * 上传用户信息
     */
    private void uploadUser() {
        List<User> userList = userDao.findToUpload();
        if (userList != null && userList.size() > 0) {
            for (final User user : userList) {
                user.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            LogUtil.i("用户数据更新成功");
                            userDao.update(user);
                        } else {
                            LogUtil.w("用户数据更新失败");
                        }
                    }
                });
            }
        }
    }
}
