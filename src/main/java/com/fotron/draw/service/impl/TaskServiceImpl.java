package com.fotron.draw.service.impl;

import com.fotron.draw.bean.req.UserReq;
import com.fotron.draw.bean.req.task.DoTaskReq;
import com.fotron.draw.bean.req.task.FinishTaskReq;
import com.fotron.draw.bean.resp.task.TaskListResp;
import com.fotron.draw.core.MyErrorCode;
import com.fotron.draw.entity.Task;
import com.fotron.draw.entity.TaskLog;
import com.fotron.draw.entity.TranSport;
import com.fotron.draw.entity.User;
import com.fotron.draw.mapper.TaskLogMapper;
import com.fotron.draw.mapper.TaskMapper;
import com.fotron.draw.mapper.TranSportMapper;
import com.fotron.draw.mapper.UserMapper;
import com.fotron.draw.service.TaskService;
import com.fotron.draw.utils.Constant;
import com.fotron.draw.utils.DiamondUtils;
import com.fotron.draw.utils.RedisTool;
import com.fotron.draw.utils.TimeUtils;
import com.fotrontimes.core.exception.BusinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.fotron.draw.utils.Constant.DIAMOND_TYPE.UP;
import static com.fotron.draw.utils.Constant.DIAMOND_UPORDOWN.DIAMOND_UP;
import static com.fotron.draw.utils.Constant.TASKSTATUS.TASK_COMPILED;
import static com.fotron.draw.utils.Constant.TASKSTATUS.TASK_NOT_COMPILED;
import static com.fotron.draw.utils.Constant.TASKTYPE.JOIN_IN;
import static com.fotron.draw.utils.Constant.TASKTYPE.PUBLIC_GAME;
import static com.fotron.draw.utils.Constant.TASKTYPE.SIGN_IN;
import static com.fotron.draw.utils.Constant.TASKTYPE.SMALL_GAME;

/**
 * @author: niuhuan
 * @createDate: 2018/11/30
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
@Service
public class TaskServiceImpl implements TaskService {
    @Resource
    private TaskMapper taskMapper;

    @Resource
    private TaskLogMapper taskLogMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private DiamondUtils diamondUtils;

    @Resource
    private RedisTool redisTool;
    @Resource
    private TranSportMapper tranSportMapper;

    @Override
    public Map allList(UserReq userReq) {
        Map map = new HashMap();
        User user = this.userMapper.selectByUserId(userReq.getUserId());
        if (user == null) {
            throw new BusinessException(MyErrorCode.USER_IS_NULL);
        }
        List<Task> tasks = this.taskMapper.selectAllList();
        List<Task> everyDayTaskList = new ArrayList<>();
        List<TaskListResp> trySmallGameList = new ArrayList<>();
        tasks.forEach(task -> {
            Integer taskType = task.getTaskType();
            //查询任务日志是否存在 这个用户的任务id
            TaskLog taskLog = this.taskLogMapper.selectTaskLog(user.getUserId(), task.getId());
            if (taskLog != null) {
                if (taskType == SIGN_IN && !TimeUtils.getDateFromDateTime(taskLog.getCreateTime()).equals(
                        TimeUtils.getDateFromDateTime(new Date()))) {
                    //每日签到 不是当天日期
                    everyDayTaskList.add(task);
                } else if (taskType == JOIN_IN || taskType == PUBLIC_GAME) {
                    if (taskLog.getStatus() == TASK_NOT_COMPILED) {
                        //有记录  如果是添加小程序  公众号直  状态未完成的
                        everyDayTaskList.add(task);
                    }
                } else if (taskType == SMALL_GAME) {
                    //有记录 但是未完成状态
                    if (taskLog.getStatus() == TASK_NOT_COMPILED) {
                        everyDayTaskList.add(task);
                    }
                }
            } else {
                //没有记录  如果是添加小程序  公众号直接添加
                if (taskType == JOIN_IN || taskType == PUBLIC_GAME || taskType == SIGN_IN) {
                    everyDayTaskList.add(task);
                } else if (taskType == SMALL_GAME) {
                    TranSport tranSport = this.tranSportMapper.selectByTask(task.getId(), Constant.ONE);
                    //如果是小程序直接添加
                    TaskListResp resp = new TaskListResp();
                    BeanUtils.copyProperties(task, resp);
                    if (tranSport != null) {
                        resp.setTransportImg(tranSport.getTransportImg());
                    }
                    trySmallGameList.add(resp);
                }
            }
        });
        map.put("everyDayTaskList", everyDayTaskList);
        map.put("trySmallGameList", trySmallGameList);
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "primaryDataSourceTransactionManager")
    public void doTask(DoTaskReq doTaskReq) {
        User user = this.userMapper.selectByUserId(doTaskReq.getUserId());
        if (user == null) {
            throw new BusinessException(MyErrorCode.USER_IS_NULL);
        }
        Task task = this.taskMapper.selectByTaskId(doTaskReq.getTaskId());
        if (task==null){
            throw new BusinessException(MyErrorCode.TASK_IS_NULL);
        }
        TaskLog newTaskLog = new TaskLog();
        Date nowDate = new Date();
        //如果任务类型为 1，2 进入日志表查询 更新状态 和完成时间
        if (doTaskReq.getTaskType() == JOIN_IN || doTaskReq.getTaskType() == PUBLIC_GAME) {
            TaskLog taskLog = this.taskLogMapper.selectTaskLog(doTaskReq.getUserId(), doTaskReq.getTaskId());
            if (taskLog != null && taskLog.getStatus() == TASK_NOT_COMPILED) {
                throw new BusinessException(MyErrorCode.REPEAT_TO_RECEIVE);
            }
            newTaskLog.setTaskId(doTaskReq.getTaskId());
            newTaskLog.setCreateTime(nowDate);
            newTaskLog.setStatus(TASK_NOT_COMPILED);
            newTaskLog.setUserId(doTaskReq.getUserId());
            //插入日志数据
            this.taskLogMapper.insertSelective(newTaskLog);

        } else if (doTaskReq.getTaskType() == SMALL_GAME) {
            //如果浏览时间小程序
            newTaskLog.setTaskId(doTaskReq.getTaskId());
            newTaskLog.setCreateTime(nowDate);
            newTaskLog.setFinishTime(nowDate);
            newTaskLog.setStatus(TASK_COMPILED);
            newTaskLog.setUserId(doTaskReq.getUserId());
            //插入日志数据
            this.taskLogMapper.insertSelective(newTaskLog);

            this.diamondUtils.updateDiamond(user,Constant.NINE,UP,task.getAwardValue());
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "primaryDataSourceTransactionManager")
    public void finishTask(FinishTaskReq data) {
        //用户校验
        User user = this.userMapper.selectByUserId(data.getUserId());
        if (user == null) {
            throw new BusinessException(MyErrorCode.USER_IS_NULL);
        }
        Task task;
        if (JOIN_IN == data.getTaskType()) {
            //添加小程序
            task = this.taskMapper.selectByTaskId(1);
        } else {
            //关注公众号
            task = this.taskMapper.selectByTaskId(2);
        }
        //任务校验
        Task checkTask = checkTask(task.getId());
        String lockKey = "lock:task:finish" + data.getUserId();
        String requestId = UUID.randomUUID().toString();
        try {
            if (redisTool.tryGetDistributedLock(lockKey, requestId)) {
                //任务待完成日志记录查询
                TaskLog taskLog = this.taskLogMapper.selectTaskLog(data.getUserId(), task.getId());
                if (taskLog == null) {
                    //第一步没有完成
                    throw new BusinessException(MyErrorCode.TASK_FIRST);
                } else if (taskLog.getStatus() == Constant.ONE) {
                    //已经完成了,重复
                    throw new BusinessException(MyErrorCode.TASK_FINISH);
                } else {
                    //已经完成第一步更改状态为已完成
                    taskLog.setStatus(Constant.ONE);
                    taskLog.setFinishTime(new Date());
                    this.taskLogMapper.updateByPrimaryKeySelective(taskLog);
                    //更新用户钻石
                    if (data.getTaskType() == JOIN_IN) {
                        this.diamondUtils.updateDiamond(user, Constant.SEVEN, UP, checkTask.getAwardValue());
                    } else if (data.getTaskType() == PUBLIC_GAME) {
                        this.diamondUtils.updateDiamond(user, Constant.EIGHT, UP, checkTask.getAwardValue());
                    }
                }

            }
        } finally {
            redisTool.releaseDistributedLock(lockKey, requestId);
        }
    }


    /**
     * 任务校验
     *
     * @param taskId
     */
    private Task checkTask(int taskId) {
        Task task = this.taskMapper.selectByTaskId(taskId);
        if (task == null || task.getStatus() == Constant.TWO) {
            throw new BusinessException(MyErrorCode.TASK_UNDERCARRIAGE);
        }
        //不是关注公众号和添加小程序
        if (task.getTaskType() == Constant.THREE || task.getTaskType() == Constant.FOUR) {
            throw new BusinessException(MyErrorCode.TASK_UNDERCARRIAGE);
        }
        return task;
    }


}
