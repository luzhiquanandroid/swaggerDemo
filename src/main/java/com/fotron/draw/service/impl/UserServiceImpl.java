package com.fotron.draw.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.fotron.draw.bean.req.UserReq;
import com.fotron.draw.bean.req.WxLoginReq;
import com.fotron.draw.bean.resp.SessionResp;
import com.fotron.draw.bean.resp.UserInfoResp;
import com.fotron.draw.bean.resp.UserResp;
import com.fotron.draw.bean.resp.WxUserInfoResp;
import com.fotron.draw.core.LoginComponent;
import com.fotron.draw.core.MyErrorCode;
import com.fotron.draw.entity.*;
import com.fotron.draw.mapper.DiamondRecordMapper;
import com.fotron.draw.mapper.InvitationJoinMapper;
import com.fotron.draw.mapper.InvitationMapper;
import com.fotron.draw.mapper.UserMapper;
import com.fotron.draw.service.TaskService;
import com.fotron.draw.service.UserService;
import com.fotron.draw.utils.AesUtil;
import com.fotron.draw.utils.Constant;
import com.fotron.draw.utils.Deciphering;
import com.fotron.draw.utils.DiamondUtils;
import com.fotrontimes.core.exception.BusinessException;
import com.fotrontimes.core.web.ApiRequest;
import com.xiaoleilu.hutool.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 15:32
 * @description
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Resource
    private RestTemplate restTemplate;
    @Value("${user.url}")
    private String userUrl;
    @Resource
    private UserMapper userMapper;

    @Resource
    private LoginComponent loginComponent;
    @Resource
    private InvitationMapper invitationMapper;
    @Resource
    private InvitationJoinMapper invitationJoinMapper;

    @Value("${diamond.diamondUrl}")
    private String diamondUrl;

    @Resource
    private DiamondUtils diamondUtils;

    @Resource
    private DiamondRecordMapper diamondRecordMapper;

    @Resource
    private TaskService taskService;


    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "primaryDataSourceTransactionManager")
    public UserInfoResp save(ApiRequest<WxLoginReq> apiRequest) {
        UserInfoResp resp = new UserInfoResp();
        //解密code
        SessionResp sessionResp = this.loginComponent.getUserInfo(apiRequest.getData().getCode());
        if (sessionResp == null) {
            throw new BusinessException(MyErrorCode.WX_CODE_INVALID);
        }
        User user = this.userMapper.selectByOpenId(sessionResp.getOpenid());
        //用户注册
        UserInfoResp result = this.userRegister(user, apiRequest, sessionResp.getOpenid(), sessionResp.getSessionKey());
        resp.setUserId(result.getUserId());
        resp.setFlag(result.getFlag());
        return resp;
    }

    @Override
    public UserResp getUserInfo(UserReq userReq) {
        User user = this.userMapper.selectByUserId(userReq.getUserId());
        UserResp userResp = new UserResp();
        if (user == null) {
            throw new BusinessException(MyErrorCode.USER_IS_NULL);
        }
        userResp.setAllDiamond(user.getDiamond());
        userResp.setAllMount(user.getTelephoneFare());
        if (!StringUtils.isEmpty(user.getNickname())) {
            userResp.setNickName(user.getNickname());
        }
        if (StringUtils.isEmpty(user.getHeadImage())) {
            userResp.setHeadImage(Constant.HEAD_IMAGE);
        } else {
            userResp.setHeadImage(user.getHeadImage());
        }
        Map map = this.taskService.allList(userReq);
        List<Task> everyDayTaskList = (List<Task>) map.get("everyDayTaskList");
        List<Task> trySmallGameList = (List<Task>) map.get("trySmallGameList");
        userResp.setTaskTotal(everyDayTaskList.size() + trySmallGameList.size());
        userResp.setLevel(user.getLevel());
        //返回用户的等级
        Integer diamondCount = this.diamondRecordMapper.selectDiamondCount(user.getUserId());
        if (diamondCount != null) {
            if (diamondCount >= 200) {
                int count = diamondCount % Constant.LEVEL.LEVEL_COUNT;
                userResp.setDiamondCount(count);
            } else {
                userResp.setDiamondCount(diamondCount);
            }
        }
        return userResp;
    }

    /**
     * 用户第一次进入注册,老用户更新信息并返回
     *
     * @param queryUser
     * @param apiRequest
     */
    private UserInfoResp userRegister(User queryUser, ApiRequest<WxLoginReq> apiRequest, String openId, String sessionKey) {
        UserInfoResp resp = new UserInfoResp();
        log.info("用户登录参数:{}", apiRequest.getData());
        Date now = new Date();
        User userss = null;
        if (queryUser == null) {
            //第一次进入注册
            queryUser = new User();
            queryUser.setUserId(UUID.randomUUID().toString().replace("-", ""));
            queryUser.setOpenId(openId);
            queryUser.setSource(apiRequest.getData().getSource());
            queryUser.setStatus(Constant.YES);
            queryUser.setCreateTime(now);
            queryUser.setUpdateTime(now);
            queryUser.setLastLoginTime(now);
            queryUser.setDiamond(Constant.BASE_LARGESS);
            //封装用户授权信息
            User users = this.setUser(queryUser, apiRequest.getData(), sessionKey, now);
            try {
                //注册
                this.userMapper.insertSelective(users);
                //钻石获取记录
                DiamondRecord diamondRecord = new DiamondRecord();
                diamondRecord.setUpdateTime(now);
                diamondRecord.setCreateTime(now);
                diamondRecord.setType(Constant.YES);
                diamondRecord.setAmount(Constant.BASE_LARGESS);
                diamondRecord.setAction(0);
                diamondRecord.setUserId(users.getUserId());
                this.diamondRecordMapper.insertSelective(diamondRecord);
            } catch (DuplicateKeyException e) {
                log.error("注册用户重复插入:{}", e);
                throw new BusinessException(MyErrorCode.USER_EXIST);
            }
            resp.setUserId(queryUser.getUserId());
            resp.setFlag(true);
        } else {
            //是否是从邀请链接里面登录的
            if (apiRequest.getData().getFriendId() != null && apiRequest.getData().getRefId() != null) {
                InvitationJoin invitationJoin1 = this.invitationJoinMapper.selectByJoinUserIdAndRefId(queryUser.getUserId(), apiRequest.getData().getRefId());
                if (invitationJoin1 != null) {
                    log.error("您已经助力过这个分享了");
                    throw new BusinessException(MyErrorCode.HELP_REPEAT);
                }
                if (apiRequest.getData().getFriendId().equals(queryUser.getUserId())) {
                    log.error("自己不能帮助自己拆开哦！");
                    throw new BusinessException(MyErrorCode.ME_TO_ME);
                }
                log.error("您已经是老用户了");
                throw new BusinessException(MyErrorCode.OLD_USER);
            }
            //封装用户授权信息
            userss = this.setUser(queryUser, apiRequest.getData(), sessionKey, now);
            this.userMapper.updateByPrimaryKeySelective(userss);
            //获取用户注册时间
            Date createTime = queryUser.getCreateTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String longTime = simpleDateFormat.format(createTime);
            String nowTime = simpleDateFormat.format(now);
            resp.setUserId(queryUser.getUserId());
            //判断是否是新用户:注册当天即为新用户
            if (longTime.equals(nowTime)) {
                //登录时间和注册时间一样说明是新用户
                resp.setFlag(true);
            } else {
                //不是一天说明是老用户
                resp.setFlag(false);
            }

        }
        userss.setLastLoginTime(now);
        this.userMapper.updateByPrimaryKeySelective(userss);
        return resp;

    }

    /**
     * 解密用户数据
     *
     * @param data
     * @param sessionResp
     * @return
     */
    private WxUserInfoResp decipheringUserInfo(WxLoginReq data, String sessionResp) {
        if (data.getEncryptedData() != null && data.getIv() != null) {
            //用户授权过了解密用户信息
            JSONObject userInfo = Deciphering.getUserInfo(data.getEncryptedData(), sessionResp, data.getIv());
            log.info("解密后的用户信息:{}", userInfo);
            WxUserInfoResp object = JSONObject.parseObject(JSONObject.toJSONString(userInfo), WxUserInfoResp.class);
            log.info("转换对象后的用户信息:{}", object);
            return object;
        }
        return null;
    }

    /**
     * 封装授权信息
     *
     * @param user
     * @param apiRequest
     * @param now
     */
    private User setUser(User user, WxLoginReq apiRequest, String sessionKey, Date now) {
        //首页邀请
        this.homeInvitation(apiRequest, now, user);
        if (apiRequest.getIv() != null && apiRequest.getEncryptedData() != null) {
            //授权用户
            //从宝箱二维码进来
            this.qrCodeTo(user, apiRequest, now);
            if (user.getAuthorizeFlag() == null || user.getAuthorizeFlag() == 2) {
                user.setAuthorizeFlag(Constant.YES);
                String userInfo = AesUtil.getInstance(sessionKey, apiRequest.getIv()).decrypt(apiRequest.getEncryptedData());
                WxUserInfoResp wxUserInfoResp = JSONObject.parseObject(userInfo, WxUserInfoResp.class);
                if (wxUserInfoResp != null) {
                    user.setCity(wxUserInfoResp.getCity());
                    user.setCountry(wxUserInfoResp.getCountry());
                    user.setHeadImage(wxUserInfoResp.getAvatarUrl());
                    user.setNickname(wxUserInfoResp.getNickName());
                    user.setSex(wxUserInfoResp.getGender());
                }

                //助力邀请面进来的
                if (apiRequest.getFriendId() != null && apiRequest.getRefId() != null) {
                    InvitationJoin invitationJoin1 = this.invitationJoinMapper.selectByJoinUserIdAndRefId(user.getUserId(), apiRequest.getRefId());
                    if (invitationJoin1 != null) {
                        log.error("您已经助力过这个分享了");
                        throw new BusinessException(MyErrorCode.HELP_REPEAT);
                    }
                    if (apiRequest.getFriendId().equals(user.getUserId())) {
                        log.error("自己不能帮助自己拆开哦！");
                        throw new BusinessException(MyErrorCode.ME_TO_ME);
                    }
                    //判断是否邀请失效了
                    Invitation invitation = this.invitationMapper.selectByPrimaryKey(apiRequest.getRefId());
                    if (invitation != null && invitation.getExpireTime().compareTo(now) == Constant.ONE) {
                        if (invitation.getStatus() == Constant.TWO || invitation.getStatus() == Constant.INVITATION_STATUS.HELP_RECEIVED) {
                            log.error("已有人帮助好友拆开了哦");
                            throw new BusinessException(MyErrorCode.IT_DONE);
                        }
                        //存入加入邀请表
                        InvitationJoin invitationJoin = new InvitationJoin();
                        invitationJoin.setCreateTime(now);
                        invitationJoin.setUserId(apiRequest.getFriendId());
                        invitationJoin.setJoinUserId(user.getUserId());
                        invitationJoin.setInvitationId(apiRequest.getRefId());
                        invitationJoin.setAuthorizeFlag(Constant.YES);
                        this.invitationJoinMapper.insertSelective(invitationJoin);

                        //更新邀请进度
                        int count = invitation.getInvitationNum() + Constant.YES;
                        invitation.setInvitationNum(count);
                        if (invitation.getType() == Constant.ONE) {
                            if (count >= Constant.THREE) {
                                //助力红包助力完成
                                invitation.setStatus(Constant.TWO);
                            }
                        } else if (invitation.getType() == Constant.TWO) {
                            if (count >= Constant.SIX) {
                                //助力宝箱助力完成
                                invitation.setStatus(Constant.TWO);
                            }
                        }
                        this.invitationMapper.updateByPrimaryKeySelective(invitation);
                    } else {
                        log.error("助力已经失效");
                        throw new BusinessException(MyErrorCode.ASSISTANCE_FAIL);
                    }

                } else {
                    log.info("邀请id:{}", apiRequest.getRefId());
                    log.info("邀请friendId为空:{},:{}", apiRequest.getFriendId());
                }

            }

        }

        return user;
    }

    /**
     * 首页当天邀请好友逻辑
     * 查询当天是否邀请过,没有邀请的话就可以存入数据
     */
    private void homeInvitation(WxLoginReq req, Date now, User user) {
        if (!StringUtils.isEmpty(req.getFriendHome())) {
            if (!req.getFriendHome().equals(user.getUserId())) {
                //判断当天是否邀请过这个好友
                InvitationJoin queryInvitationJoin = this.invitationJoinMapper.selectByToday(req.getFriendHome(), user.getUserId(), DateUtil.beginOfDay(now), DateUtil.endOfDay(now));
                //是首页邀请链接进来的好友 //存入加入邀请表
                if (queryInvitationJoin != null) {
                    log.info("你的朋友今天已经邀请过你了");
                } else {
                    //增加邀请者钻石10
                    User user1 = this.userMapper.selectByUserId(req.getFriendHome());
                    if (user1 != null) {
                        //判断是否当天是否邀请过了10个好友
                        int count = this.invitationJoinMapper.selectByTenInvitation(user1.getUserId(), DateUtil.beginOfDay(now), DateUtil.endOfDay(now));
                        if (count < 10) {
                            InvitationJoin invitationJoin = new InvitationJoin();
                            invitationJoin.setCreateTime(now);
                            invitationJoin.setUserId(req.getFriendHome());
                            invitationJoin.setInvitationId(0);
                            invitationJoin.setJoinUserId(user.getUserId());
                            invitationJoin.setType(Constant.TWO);
                            invitationJoin.setAuthorizeFlag(Constant.YES);
                            this.invitationJoinMapper.insertSelective(invitationJoin);

                            this.diamondUtils.updateDiamond(user1, Constant.FOUR, Constant.ONE, 10L);
                        } else {
                            log.info("用户当天已经邀请了10个好友了:{}", user1.getUserId());
                        }

                    } else {
                        log.info("没有找到这个邀请者:{}", req.getFriendHome());
                    }

                }
            } else {
                log.info("自己不能邀请自己");
            }

        }

    }

    /**
     * 从宝箱二维码进来
     *
     * @param user
     * @param apiRequest
     * @param now
     */
    private void qrCodeTo(User user, WxLoginReq apiRequest, Date now) {
        //从宝箱二维进来的
        if (apiRequest.getFriendId() == null && apiRequest.getRefId() != null) {
            Invitation invitation1 = this.invitationMapper.selectByPrimaryKey(apiRequest.getRefId());
            if (invitation1 != null) {
                User users = this.userMapper.selectByUserId(invitation1.getUserId());
                if (users != null) {
                    InvitationJoin invitationJoin1 = this.invitationJoinMapper.selectByJoinUserIdAndRefId(user.getUserId(), apiRequest.getRefId());
                    if (invitationJoin1 != null) {
                        log.error("您已经助力过这个分享了");
                        throw new BusinessException(MyErrorCode.HELP_REPEAT);
                    }
                    if (users.getUserId().equals(user.getUserId())) {
                        log.error("自己不能帮助自己拆开哦！");
                        throw new BusinessException(MyErrorCode.ME_TO_ME);
                    }
                    //判断是否邀请失效了
                    Invitation invitation = this.invitationMapper.selectByPrimaryKey(apiRequest.getRefId());
                    if (invitation != null && invitation.getExpireTime().compareTo(now) == Constant.ONE) {
                        if (invitation.getStatus() == Constant.TWO || invitation.getStatus() == Constant.INVITATION_STATUS.HELP_RECEIVED) {
                            log.error("已有人帮助好友拆开了哦");
                            throw new BusinessException(MyErrorCode.IT_DONE);
                        }
                        //存入加入邀请表
                        InvitationJoin invitationJoin = new InvitationJoin();
                        invitationJoin.setCreateTime(now);
                        invitationJoin.setUserId(users.getUserId());
                        invitationJoin.setJoinUserId(user.getUserId());
                        invitationJoin.setInvitationId(apiRequest.getRefId());
                        invitationJoin.setAuthorizeFlag(Constant.YES);
                        this.invitationJoinMapper.insertSelective(invitationJoin);

                        //更新邀请进度
                        int count = invitation.getInvitationNum() + Constant.YES;
                        invitation.setInvitationNum(count);
                        if (invitation.getType() == Constant.TWO) {
                            if (count >= Constant.SIX) {
                                //助力宝箱助力完成
                                invitation.setStatus(Constant.TWO);
                            }
                        }
                        this.invitationMapper.updateByPrimaryKeySelective(invitation);
                    } else {
                        log.error("助力已经失效");
                        throw new BusinessException(MyErrorCode.ASSISTANCE_FAIL);
                    }

                } else {
                    log.info("未找到这个邀请者用户 refId:{}", apiRequest.getRefId());
                }
            }
        } else {
            log.info("邀请id:{}", apiRequest.getRefId());
        }
    }


}