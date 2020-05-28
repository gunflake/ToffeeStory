package com.toffeestory.backend.alarm;

import com.toffeestory.backend.account.Account;
import com.toffeestory.backend.account.AccountRepository;
import com.toffeestory.backend.post.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final AccountRepository accountRepository;

    public Optional<Alarm> enrollLikeAlarm(Account targetAccount, Integer watchaAccountNo, Post targetPost){

        Optional<Alarm> result = alarmRepository.findByTargetAccountAndTargetPostAndWatchaAccountNo(targetAccount, targetPost, watchaAccountNo);

        if(result.isEmpty()){
            Alarm newAlarm = new Alarm();
            newAlarm.setTargetAccount(targetAccount);
            newAlarm.setStatus(AlarmStatus.UNREAD);
            newAlarm.setType(AlarmType.LIKE);
            newAlarm.setWatchaAccountNo(watchaAccountNo);
            newAlarm.setTargetPost(targetPost);
            result = Optional.ofNullable(alarmRepository.save(newAlarm));
        }
        return result;
    }


    public List<AlarmMessage> getAlarmMessageList(Account account) {
        List<Alarm> alarmList = alarmRepository.findAllByTargetAccountAndStatus(account, AlarmStatus.UNREAD);

        List<AlarmMessage> alarmMessageList = new ArrayList<>();

        for(Alarm alarm : alarmList){
            addAlarmMessageList(alarm, alarmMessageList);
        }

        return alarmMessageList;
    }

    private void addAlarmMessageList(Alarm alarm, List<AlarmMessage> alarmMessageList) {
        StringBuilder sb = new StringBuilder();
        AlarmMessage alarmMessage = null;

        if(alarm.getType() == AlarmType.LIKE){
            Account account = accountRepository.findById(alarm.getWatchaAccountNo()).orElse(null);

            if(account != null){
                sb.append(account.getAccountName()).append("님이 회원님의 게시물을 좋아합니다.");
                alarmMessage = new AlarmMessage(account.getSrc(), sb.toString());
            }

        }

        if(alarmMessage != null){
            alarmMessageList.add(alarmMessage);
        }
    }
}
