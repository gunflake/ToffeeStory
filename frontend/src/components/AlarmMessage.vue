<template>
  <div class="ml-4">
    <div class="relative">
      <img src="@/assets/image/bell.png" class="w-10 h-10 rounded-full cursor-pointer rel" v-on:click="showAndHideAlarmList"/>
      <div v-if="getAlarmMessageList != null && getAlarmMessageList.length > 0" class="alarmCount text-center text-white">{{getAlarmMessageList.length}}</div>
    </div>
    <div v-if="alarmVisible">
      <div class="bg-white mt-2 alarm-rotateSquare"></div>
      <div class="mt-4 fontDoHyeon text-base" style="z-index: 1; position:absolute; right: 2.5rem;">
        <div class="border-black min-w-64 max-h-64 overflow-scroll bg-white completeBox" style="width: 20rem;">
          <div v-for="(alarmMessage, index ) in getAlarmMessageList" :key="index">
            <!-- TODO: 알림 메세지 스타일 및 데이터 넣기 -->
            <div class="flex p-2 border-b-2 border-gray-300">
              <img :src="alarmMessage.image" class="w-10 h-10 my-auto cursor-pointer" alt="" v-on:click="goProfilePage(alarmMessage.accountId)"/>
              <div class="ml-2 my-auto cursor-pointer" v-on:click="readAlarmMessage(alarmMessage.alarmSeqNo)">{{alarmMessage.message}}</div>
              <img src="../assets/image/close.png" class="w-4 h-4 rounded-full" style="cursor: pointer;"/>
            </div>
          </div>
          <div v-if="getAlarmMessageList == null || getAlarmMessageList.length == 0"
               class="p-2 text-2xl text-center">알림 메시지가 없습니다.
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import api from '@/backend-api'
  import { mapGetters, mapMutations } from 'vuex'

  export default {
    name: 'AlarmMessage',
    data: function () {
      return {
        alarmVisible: false
      }
    },
    computed: {
      ...mapGetters(['getAlarmMessageList', 'getToken'])
    },
    methods: {
      ...mapMutations(['setAlarmMessageList']),
      showAndHideAlarmList () {
        this.alarmVisible = !this.alarmVisible
      },
      goProfilePage (accountId) {
        if (!this.isLoggedIn) {
          this.alert.message = '프로필을 보기 위해서는 로그인이 필요합니다.'
          this.alert.type = 'gray'
          this.settingAlertMsg(this.alert)
          this.$router.push('/login')
        } else {
          if (accountId == null) {
            this.$router.push('/@' + this.getUserName)
          } else {
            this.$router.push('/@' + accountId)
          }
        }
      },
      readAlarmMessage (alarmNo) {
        console.log(alarmNo)
        api.readAlarmMessage(alarmNo, this.getToken)
          .then((response) => {
            console.log(response)
            this.setAlarmMessageList(response.data)
          })
          .catch(() => {
            // 알람 지우는거 실패 처리 하기
          })
      }
    }
  }
</script>

<style scoped>

</style>
