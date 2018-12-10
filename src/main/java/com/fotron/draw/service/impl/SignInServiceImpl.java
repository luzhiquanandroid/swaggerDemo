package com.fotron.draw.service.impl;

import com.fotron.draw.bean.req.UserReq;
import com.fotron.draw.bean.req.task.SignInReq;
import com.fotron.draw.bean.resp.task.SignInResp;
import com.fotron.draw.core.MyErrorCode;
import com.fotron.draw.entity.SignIn;
import com.fotron.draw.entity.SignInLog;
import com.fotron.draw.entity.TaskLog;
import com.fotron.draw.entity.User;
import com.fotron.draw.mapper.SignInLogMapper;
import com.fotron.draw.mapper.SignInMaper;
import com.fotron.draw.mapper.TaskLogMapper;
import com.fotron.draw.mapper.UserMapper;
import com.fotron.draw.service.SignInService;
import com.fotron.draw.utils.Constant;
import com.fotron.draw.utils.DiamondUtils;
import com.fotron.draw.utils.TimeUtils;
import com.fotrontimes.core.exception.BusinessException;
import com.xiaoleilu.hutool.date.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.fotron.draw.utils.Constant.SIGN_DIAMOND.SIGN_FIRST;
import static com.fotron.draw.utils.Constant.SIGN_DIAMOND.SIGN_FIVE;
import static com.fotron.draw.utils.Constant.SIGN_DIAMOND.SIGN_FOUR;
import static com.fotron.draw.utils.Constant.SIGN_DIAMOND.SIGN_SEVEN;
import static com.fotron.draw.utils.Constant.SIGN_DIAMOND.SIGN_SIX;
import static com.fotron.draw.utils.Constant.SIGN_DIAMOND.SIGN_THREE;
import static com.fotron.draw.utils.Constant.SIGN_DIAMOND.SIGN_TWO;
import static com.fotron.draw.utils.Constant.TASKSTATUS.TASK_COMPILED;

/**
 * @author luzhiquan
 * @createTime 2018/12/01 15:41
 * @description
 */
@Service
public class SignInServiceImpl implements SignInService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private SignInMaper signInMaper;

    @Resource
    private SignInLogMapper signInLogMapper;

    @Resource
    private TaskLogMapper taskLogMapper;

    @Resource
    private DiamondUtils diamondUtils;

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "primaryDataSourceTransactionManager")
    public List<SignInResp> signIn(SignInReq signInReq) {
        User user = this.userMapper.selectByUserId(signInReq.getUserId());
        Date nowDate = new Date();
        if (user == null) {
            throw new BusinessException(MyErrorCode.USER_IS_NULL);
        }
        int days;
        SignIn signIn = this.signInMaper.selectSignInByUserId(signInReq.getUserId());
        if (signIn == null) {
            //一次没有签到 初始化天数为1
            SignIn newSignIn = new SignIn();
            newSignIn.setUserId(signInReq.getUserId());
            newSignIn.setCreateTime(nowDate);
            newSignIn.setFirstSignInDate(nowDate);
            newSignIn.setDays(1);
            this.signInMaper.insertSelective(newSignIn);
            //插入一条签到日志
            SignInLog newSignInLog = new SignInLog();
            newSignInLog.setUserId(signInReq.getUserId());
            newSignInLog.setSignInDate(nowDate);
            newSignInLog.setSignInTime(nowDate);
            this.signInLogMapper.insertSelective(newSignInLog);
            //插入一条签到任务记录
            TaskLog taskLog = new TaskLog();
            taskLog.setTaskId(Constant.TASK_SIGN_ID.ID);
            taskLog.setCreateTime(nowDate);
            taskLog.setFinishTime(nowDate);
            taskLog.setStatus(TASK_COMPILED);
            taskLog.setUserId(user.getUserId());
            this.taskLogMapper.insertSelective(taskLog);
            days = newSignIn.getDays();
            //设置修改钻石
            int diamond = getDiamondByDays(days);
            this.diamondUtils.updateDiamond(user, Constant.TEN, Constant.DIAMOND_TYPE.UP, diamond);
        } else {
            //签到逻辑
            Date dateFromDateTime = TimeUtils.getDateFromDateTime(new Date());
            if (signIn.getFirstSignInDate().equals(dateFromDateTime)){
              throw  new BusinessException(MyErrorCode.SIGNED_DAYS_ERROR);
            }
            int currentDays = signIn.getDays();
            if (currentDays < Constant.SIGN_ALL_DAYS.SEVEN) {
                //如果签到天数小于7天 自动添加1
                currentDays++;
            } else if (currentDays == Constant.SIGN_ALL_DAYS.SEVEN) {
                //如果签到天数等于7天 设置为1天
                currentDays = 1;
            }
            signIn.setCreateTime(nowDate);
            signIn.setFirstSignInDate(nowDate);
            signIn.setDays(currentDays);
            this.signInMaper.updateByPrimaryKeySelective(signIn);
            //插入一条签到日志
            SignInLog newSignInLog = new SignInLog();
            newSignInLog.setUserId(signInReq.getUserId());
            newSignInLog.setSignInDate(new Date());
            newSignInLog.setSignInTime(new Date());
            this.signInLogMapper.insertSelective(newSignInLog);
            //插入一条签到任务记录
            TaskLog taskLog = new TaskLog();
            taskLog.setTaskId(Constant.TASK_SIGN_ID.ID);
            taskLog.setCreateTime(nowDate);
            taskLog.setFinishTime(nowDate);
            taskLog.setStatus(TASK_COMPILED);
            taskLog.setUserId(user.getUserId());
            this.taskLogMapper.insertSelective(taskLog);
            //设置修改钻石
            int diamond = getDiamondByDays(currentDays);
            this.diamondUtils.updateDiamond(user, Constant.TEN, Constant.DIAMOND_TYPE.UP, diamond);
            days = signIn.getDays();
        }

        ArrayList<SignInResp> signInResps = new ArrayList<>();
        if (days >= 1 && days <=Constant.SIGN_ALL_DAYS.SEVEN) {
            for (int i = 1; i <= days; i++) {
                SignInResp signInresp = new SignInResp();
                signInresp.setSignIn(true);
                signInresp.setDiamond(getDiamondByDays(i));
                signInresp.setDays(i);
                signInResps.add(signInresp);
            }
            for (int i = days + 1; i <= Constant.SIGN_ALL_DAYS.SEVEN; i++) {
                SignInResp signInresp = new SignInResp();
                signInresp.setSignIn(false);
                signInresp.setDiamond(getDiamondByDays(i));
                signInresp.setDays(i);
                signInResps.add(signInresp);
            }
        }
        return signInResps;

    }

    /**
     * 签到面板集合 注：不在使用了
     *
     * @param userReq
     * @return
     */
    @Override
    public List<SignInResp> signInList(UserReq userReq) {
        User user = this.userMapper.selectByUserId(userReq.getUserId());
        ArrayList<SignInResp> signInResps = new ArrayList<>();
        if (user == null) {
            throw new BusinessException(MyErrorCode.USER_IS_NULL);
        }
        SignIn signIn = this.signInMaper.selectSignInByUserId(userReq.getUserId());
        if (signIn == null) {
            //用户没有签到过,返回给用户一个全新的签到画布
            signInResps = getSignInRespAllFalse();
        } else {
            //用户已经签到过,初始用户签到天数dayss;
            int dayss = 0;
            Date firstSignInDate = signIn.getFirstSignInDate();
            //每轮首次签到时间和当前时间相差的天数
            int days = TimeUtils.differentDaysByMillisecond(firstSignInDate, new Date());
            if (days > 0 && days <= Constant.SIGN_ALL_DAYS.SEVEN) {
                //当前日期 距离第一天签到天数 a(第一天日期) b(当前日期) c(结束日期 周期七天)
                //获取这一轮首次签到和当前日期之间的签到画布
                for (int i = days; i >= 1; i--) {
                    dayss++;
                    SignInLog signInLog = this.signInLogMapper.selectSignLogByDate(DateUtil.offsetDay(
                            TimeUtils.getDateFromDateTime(new Date()), -i));
                    signInResps.add(getSignInResp(signInLog, dayss));
                }
                //获取当前日期距离七天结束之间的日期画布
                for (int i = 0; i < Constant.SIGN_ALL_DAYS.SEVEN - days; i++) {
                    dayss++;
                    SignInLog signInLog = this.signInLogMapper.selectSignLogByDate(DateUtil.offsetDay(
                            TimeUtils.getDateFromDateTime(new Date()), i));
                    signInResps.add(getSignInResp(signInLog, dayss));
                }
            } else if (days > Constant.SIGN_ALL_DAYS.SEVEN) {
                signInResps = getSignInRespAllFalse();
            }
        }
        return signInResps;
    }

    /**
     * 判断是否签到过
     *
     * @param signInLog
     * @param dayss
     * @return
     */
    public SignInResp getSignInResp(SignInLog signInLog, int dayss) {
        int diamond = getDiamondByDays(dayss);
        SignInResp signInresp = new SignInResp();
        if (signInLog != null) {
            //说明已经签到过了
            signInresp.setSignIn(true);
            signInresp.setDiamond(diamond);
            signInresp.setDays(dayss);
        } else {
            //没有签到
            signInresp.setSignIn(false);
            signInresp.setDiamond(diamond);
            signInresp.setDays(dayss);
        }
        return signInresp;
    }

    /**
     * 返回没有签到七天画布
     *
     * @return
     */
    public ArrayList<SignInResp> getSignInRespAllFalse() {
        ArrayList<SignInResp> signInResps = new ArrayList<>();
        for (int i = 1; i <= Constant.SIGN_ALL_DAYS.SEVEN; i++) {
            SignInResp signInresp = new SignInResp();
            signInresp.setDays(i);
            signInresp.setDiamond(10);
            signInresp.setSignIn(false);
            signInResps.add(signInresp);
        }
        return signInResps;
    }

    /**
     * 通过天数判断钻石数量
     */
    public int getDiamondByDays(int days) {
        int diamond;
        switch (days) {
            case 1:
                diamond = SIGN_FIRST;
                break;
            case 2:
                diamond = SIGN_TWO;
                break;
            case 3:
                diamond = SIGN_THREE;
                break;
            case 4:
                diamond = SIGN_FOUR;
                break;
            case 5:
                diamond = SIGN_FIVE;
                break;
            case 6:
                diamond = SIGN_SIX;
                break;
            case 7:
                diamond = SIGN_SEVEN;
                break;
            default:
                diamond = 0;
                break;
        }
        return diamond;
    }
}

