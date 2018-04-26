package com.gholbanet.chat.sample;

import android.app.Application;
import android.content.Context;

import com.gholbanet.chat.sample.ui.homepagetab.HomePageTabActivity;
import com.gholbanet.chat.sample.util.ChatRoomNavigator;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.NotificationClickListener;
import com.qiscus.sdk.data.model.QiscusComment;
import com.qiscus.sdk.event.QiscusCommentReceivedEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import com.gholbanet.chat.sample.util.Configuration;
import com.gholbanet.chat.sample.util.RealTimeChatroomHandler;

import io.realm.Realm;

/**
 * Created by omayib on 18/09/17.
 */

public class SampleApp extends Application {
    private RealTimeChatroomHandler chatroomHandler;
    private static SampleApp INSTANCE;

    public static SampleApp getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        Qiscus.init(this, Configuration.QISCUS_APP_ID);
        chatroomHandler = new RealTimeChatroomHandler();

        Qiscus.getChatConfig()
                .setStatusBarColor(com.gholbanet.chat.sample.R.color.colorPrimaryDark)
                .setAppBarColor(com.gholbanet.chat.sample.R.color.colorPrimary)
                .setLeftBubbleColor(com.gholbanet.chat.sample.R.color.emojiSafeYellow)
                .setRightBubbleColor(com.gholbanet.chat.sample.R.color.colorPrimary)
                .setRightBubbleTextColor(com.gholbanet.chat.sample.R.color.qiscus_white)
                .setRightBubbleTimeColor(com.gholbanet.chat.sample.R.color.qiscus_white)
                .setReadIconColor(com.gholbanet.chat.sample.R.color.colorAccent)
                .setEmptyRoomImageResource((com.gholbanet.chat.sample.R.drawable.ic_room_empty))
                .setNotificationBigIcon(com.gholbanet.chat.sample.R.drawable.ic_logo_qiscus)
                .setNotificationClickListener(new NotificationClickListener() {
                    @Override
                    public void onClick(Context context, QiscusComment qiscusComment) {
                        ChatRoomNavigator
                                .openChatQiscusCommentRoom(context, qiscusComment)
                                .withParentClass(HomePageTabActivity.class)
                                .start();
                    }
                })
                .setEnableAddLocation(true)
                .setEmptyRoomTitleColor(com.gholbanet.chat.sample.R.color.orangeIcon)
                .setAccentColor(com.gholbanet.chat.sample.R.color.colorAccent)
                .getDeleteCommentConfig().setEnableDeleteComment(true);

        Realm.init(this);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Subscribe
    public void onReceivedComment(QiscusCommentReceivedEvent event) {
        chatroomHandler.updateChatrooms(event.getQiscusComment());
    }

    public RealTimeChatroomHandler getChatroomHandler() {
        return chatroomHandler;
    }
}
