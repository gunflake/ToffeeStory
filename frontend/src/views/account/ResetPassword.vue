<template>
  <div class="bg-grey-lighter flex flex-col">
    <div class="container w-full md:w-2/3 lg:w-1/2 max-w-2xl mx-auto flex-1 flex flex-col items-center px-2">
      <!-- Valid Token -->
      <div v-if="tokenValid" class="bg-white px-6 py-8 rounded text-black w-full mt-10">
        <img src="@/assets/image/logo.png" class="h-24 w-24 mx-auto" />
        <div class="my-4 font-bold text-2xl text-center">Change your password</div>
        <InputBox title="New password" type="password" name="password" id="password" @sendVal="updatePassword"/>
        <button class="w-full bg-gray-800 hover:bg-blue-700 text-white font-bold py-2 rounded mt-3" @click="changePassword">
          Change password
        </button>
      </div>
      <div v-else class="bg-white px-6 py-4 rounded text-black w-full mt-10">
        <div class="my-4 font-bold text-3xl text-center">Bad Token</div>
        <div class="text-base py-2 px-10">
          The password reset link was invalid, possibly because it has already been used.
          Please request a <span class="text-blue-600 cursor-pointer" @click="goResetPassword">new password reset.</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

  import api from '@/backend-api'
  import InputBox from '@/components/InputBox'
  import { mapActions, mapMutations } from 'vuex'

  export default {
    name: 'ResetPassword',
    components: {
      InputBox
    },
    data () {
      return {
        tokenValid: false,
        resetPasswordToken: null,
        newPassword: null,
        alert: {
          message: null,
          type: null
        }
      }
    },
    mounted () {
      this.resetPasswordToken = this.$route.params.token
      api.checkResetPasswordToken(this.resetPasswordToken)
        .then(response => {
          this.hideHeader()
          this.tokenValid = true
        })
        .catch(() => {
          this.showHeader()
          this.tokenValid = false
        })
    },
    methods: {
      ...mapMutations(['hideHeader', 'showHeader']),
      ...mapActions(['settingAlertMsg']),
      updatePassword (val) {
        this.newPassword = val
      },
      changePassword () {
        this.$router.push('/login')
      },
      goResetPassword () {
        // Todo: 토큰값 보내서 비밀번호 리셋하기
        let formData = new FormData()
        formData.append('password', this.newPassword)
        api.resetAccountPassword(this.resetPasswordToken, formData)
          .then(() => {
            // 알람 받기 띄우기
            this.alert.message = '비밀번호가 변경되었습니다.'
            this.alert.type = 'green'
            this.settingAlertMsg(this.alert)
            this.$router.push('/find/password')
          })
          .catch(() => {
            // password 토큰이 유효하지 않는 경우
            this.alert.message = '토큰이 유효시간이 지났습니다. 비밀번호 재설정을 원하신다면 다시 시도해주세요.'
            this.alert.type = 'red'
            this.settingAlertMsg(this.alert)
            this.tokenValid = false
          })
      }
    }
  }
</script>

<style scoped>

</style>
