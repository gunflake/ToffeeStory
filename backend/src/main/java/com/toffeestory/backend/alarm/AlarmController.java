package com.toffeestory.backend.alarm;

import com.toffeestory.backend.account.Account;
import com.toffeestory.backend.exception.NotFoundAlarmException;
import com.toffeestory.backend.exception.RestApiError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/api/alarms")
@Slf4j
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmRepository alarmRepository;
    private final AlarmService alarmService;

    @PatchMapping("/message/{seqNo}")
    public ResponseEntity readAlarmMessage(@PathVariable("seqNo") String seqNo, @AuthenticationPrincipal Account account){
        Alarm alarm = alarmRepository.findById(Integer.parseInt(seqNo)).orElseThrow(() -> new NotFoundAlarmException(seqNo));

        log.info("targetaccount = " + alarm.getTargetAccount().getAccountNo());
        log.info("acount = " + account.getAccountNo());

        if(alarm.getTargetAccount().getAccountNo().equals(account.getAccountNo())){
            alarm.setStatus(AlarmStatus.READ);
            alarmRepository.save(alarm);

            return ok(alarmService.getAlarmMessageList(account));
        }else{
            return badRequest().body(new RestApiError(HttpStatus.BAD_REQUEST, "올바르지 않은 요청입니다. 다시 시도해주세요."));
        }
    }
}
