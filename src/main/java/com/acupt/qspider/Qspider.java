package com.acupt.qspider;

import com.acupt.amazing.util.ContextUtil;
import com.acupt.dao.QmsgDAO;
import com.acupt.entity.Qmsg;
import com.acupt.util.DateUtil;
import com.acupt.util.GsonUtil;
import com.scienjus.smartqq.callback.LoginCallback;
import com.scienjus.smartqq.callback.MessageCallback;
import com.scienjus.smartqq.client.SmartQQClient;
import com.scienjus.smartqq.model.Discuss;
import com.scienjus.smartqq.model.DiscussInfo;
import com.scienjus.smartqq.model.DiscussMessage;
import com.scienjus.smartqq.model.DiscussUser;
import com.scienjus.smartqq.model.Friend;
import com.scienjus.smartqq.model.Group;
import com.scienjus.smartqq.model.GroupInfo;
import com.scienjus.smartqq.model.GroupMessage;
import com.scienjus.smartqq.model.GroupUser;
import com.scienjus.smartqq.model.Message;
import com.scienjus.smartqq.model.Result;
import com.scienjus.smartqq.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liujie on 2017/11/10.
 */
public class Qspider {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private QmsgDAO qmsgDAO = ContextUtil.getBean(QmsgDAO.class);

    private SmartQQClient client;
    private Map<Long, Friend> friendFromID = new HashMap<>();            //好友id到好友映射
    private Map<Long, Group> groupFromID = new HashMap<>();              //群id到群映射
    private Map<Long, GroupInfo> groupInfoFromID = new HashMap<>();      //群id到群详情映射
    private Map<Long, Discuss> discussFromID = new HashMap<>();          //讨论组id到讨论组映射
    private Map<Long, DiscussInfo> discussInfoFromID = new HashMap<>();  //讨论组id到讨论组详情映射

    private StateEnum state = StateEnum.NONE;

    public enum StateEnum {NONE, WAITING_QRCODE, LOGGIN_ING, LOGGIN_FAIL, PAUSE, RUNNING}

    public Qspider init() {
        client = new SmartQQClient(new MessageCallback() {
            @Override
            public void onMessage(Message message) {
                if (state != StateEnum.RUNNING) {
                    client.setPollStarted(false);
                    return;
                }
                Friend friend = friendFromID.get(message.getUserId());
                Qmsg qmsg = new Qmsg();
                qmsg.setOwner(client.getUin());
                qmsg.setFromType(1);
                if (friend != null) {
                    qmsg.setFromId(friend.getUserId());
                    qmsg.setFromName(friend.getNickname());
                    qmsg.setUid(friend.getUserId());
                    qmsg.setNick(friend.getNickname());
                    qmsg.setMarkname(friend.getMarkname());
                }
                qmsg.setContent(message.getContent());
                qmsg.setTime(DateUtil.newDate(message.getTime()));
                qmsgDAO.save(qmsg);
                logger.info("[msg]{}", GsonUtil.toJson(qmsg));
            }

            @Override
            public void onGroupMessage(GroupMessage message) {
                if (state != StateEnum.RUNNING) {
                    client.setPollStarted(false);
                    return;
                }
                Group group = groupFromID.get(message.getGroupId());
                Friend friend = friendFromID.get(message.getUserId());
                Qmsg qmsg = new Qmsg();
                qmsg.setOwner(client.getUin());
                qmsg.setFromType(2);
                qmsg.setFromId(group.getId());
                qmsg.setFromName(group.getName());
                if (friend != null) {
                    qmsg.setUid(friend.getUserId());
                    qmsg.setNick(friend.getNickname());
                    qmsg.setMarkname(friend.getMarkname());
                } else {
                    GroupUser groupUser = getGroupUser(message);
                    if (groupUser != null) {
                        qmsg.setUid(groupUser.getUin());
                        qmsg.setNick(groupUser.getNick());
                        qmsg.setMarkname(groupUser.getCard());
                    }
                }
                qmsg.setContent(message.getContent());
                qmsg.setTime(DateUtil.newDate(message.getTime()));
                qmsgDAO.save(qmsg);
                logger.info("[group_msg]{}", GsonUtil.toJson(qmsg));
            }

            @Override
            public void onDiscussMessage(DiscussMessage message) {
                if (state != StateEnum.RUNNING) {
                    client.setPollStarted(false);
                    return;
                }
                Discuss discuss = discussFromID.get(message.getDiscussId());
                Friend friend = friendFromID.get(message.getUserId());
                Qmsg qmsg = new Qmsg();
                qmsg.setOwner(client.getUin());
                qmsg.setFromType(3);
                qmsg.setFromId(discuss.getId());
                qmsg.setFromName(discuss.getName());
                if (friend != null) {
                    qmsg.setUid(friend.getUserId());
                    qmsg.setNick(friend.getNickname());
                    qmsg.setMarkname(friend.getMarkname());
                } else {
                    DiscussUser discussUser = getDiscussUser(message);
                    if (discussUser != null) {
                        qmsg.setUid(discussUser.getUin());
                        qmsg.setNick(discussUser.getNick());
                    }
                }
                qmsg.setContent(message.getContent());
                qmsg.setTime(DateUtil.newDate(message.getTime()));
                qmsgDAO.save(qmsg);
                logger.info("[discu_msg]{}", GsonUtil.toJson(qmsg));
            }
        });
        return this;
    }

    public synchronized void start(LoginCallback loginCallback) {
        if (state != StateEnum.WAITING_QRCODE) {
            return;
        }
        state = StateEnum.LOGGIN_ING;
        //异步完成登录
        new Thread(new Runnable() {
            @Override
            public void run() {
                long t0 = System.currentTimeMillis();
                logger.info("login start {}", t0);
                Result<UserInfo> result = client.login();//等待扫描二维码+登录验证
                logger.info("login {} {} {} {}ms", result.getCode(), result.getMsg(), t0,
                        System.currentTimeMillis() - t0);
                if (!result.isSuccess()) {
                    state = StateEnum.LOGGIN_FAIL;
                    if (loginCallback != null) {
                        loginCallback.onFailed(result);
                    }
                    return;
                }
                state = StateEnum.PAUSE;
                try {
                    List<Friend> friendList = client.getFriendList();                //获取好友列表
                    List<Group> groupList = client.getGroupList();                  //获取群列表
                    List<Discuss> discussList = client.getDiscussList();              //获取讨论组列表
                    for (Friend friend : friendList) {                  //建立好友id到好友映射
                        friendFromID.put(friend.getUserId(), friend);
                    }
                    for (Group group : groupList) {                     //建立群id到群映射
                        groupFromID.put(group.getId(), group);
                    }
                    for (Discuss discuss : discussList) {               //建立讨论组id到讨论组映射
                        discussFromID.put(discuss.getId(), discuss);
                    }
                } catch (Exception e) {
                    state = StateEnum.LOGGIN_FAIL;
                    if (loginCallback != null) {
                        loginCallback.onException(e);
                    }
                    return;
                }
                client.setPollStarted(true);
                state = StateEnum.RUNNING;
                if (loginCallback != null) {
                    loginCallback.onSuccess(result.getData());
                }
            }
        }).start();
    }

    public InputStream getQRCode() throws IOException {
        return new FileInputStream(getQRCodePath());
    }

    public String getQRCodePath() throws IOException {
        if (state == StateEnum.PAUSE || state == StateEnum.RUNNING) {
            throw new IllegalStateException("now is " + state);
        }
        Result<String> result = client.getQRCode();
        if (!result.isSuccess()) {
            throw new IOException(result.getMsg());
        }
        state = StateEnum.WAITING_QRCODE;
        return result.getData();
    }

    public StateEnum getState() {
        return state;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }

    public void close() {
        try {
            client.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private GroupUser getGroupUser(GroupMessage msg) {
        for (GroupUser user : getGroupInfoFromID(msg.getGroupId()).getUsers()) {
            if (user.getUin() == msg.getUserId()) {
                return user;
            }
        }
        return null; //若在群成员列表中查询不到，则为系统消息
    }

    private GroupInfo getGroupInfoFromID(Long id) {
        if (!groupInfoFromID.containsKey(id)) {
            groupInfoFromID.put(id, client.getGroupInfo(groupFromID.get(id).getCode()));
        }
        return groupInfoFromID.get(id);
    }

    private DiscussUser getDiscussUser(DiscussMessage msg) {
        for (DiscussUser user : getDiscussInfoFromID(msg.getDiscussId()).getUsers()) {
            if (user.getUin() == msg.getUserId()) {
                return user;
            }
        }
        return null; //若在讨论组成员列表中查询不到，则为系统消息
        //TODO: 也有可能是新加讨论组的用户
    }

    private DiscussInfo getDiscussInfoFromID(Long id) {
        if (!discussInfoFromID.containsKey(id)) {
            discussInfoFromID.put(id, client.getDiscussInfo(discussFromID.get(id).getId()));
        }
        return discussInfoFromID.get(id);
    }

    public static void main(String[] args) throws Exception {
        Qspider qspider = new Qspider().init();
        System.out.println(qspider.getQRCodePath());
        qspider.start(null);
        while (true) {
            System.out.println(qspider.state);
            Thread.sleep(10000);
        }
    }
}
