package com.toffeestory.backend.alarm;

import com.toffeestory.backend.account.Account;
import com.toffeestory.backend.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;

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


}
