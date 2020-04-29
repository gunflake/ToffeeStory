<template>
  <div>
    <div class="bg-grey-lighter flex flex-col">
      <div class="container w-full md:w-2/3 lg:w-1/2 max-w-2xl mx-auto flex-1 flex flex-col items-center px-2">
        <div class="bg-white px-6 py-8 rounded text-black w-full mt-10">
          <img src="../assets/image/logo.png" class="h-24 w-24 mx-auto" @click="goHomePage()" style="cursor: pointer"/>
          <div class="mt-4 font-bold text-2xl text-center">Forgot your password?</div>
          <div class="text-base mb-6 text-center my-4">
            Did you remember your account?
            <a class="no-underline border-b border-blue font-bold text-blue-700" @click="goLogin" style="cursor: pointer">
              Login
            </a>
          </div>
          <InputBox title="Email" type="email" name="email" id="email" @sendVal="updateEmail"/>
          <button class="w-full bg-gray-800 hover:bg-blue-700 text-white font-bold py-2 rounded mt-3" @click="sendEmail">
            Send me reset password instructions
          </button>
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
    name: 'login',
    components: {
      InputBox
    },
    data () {
      return {
        email: '',
        alert: {
          message: null,
          type: null
        }
      }
    },
    mounted () {
      this.hideHeader()
    },
    methods: {
      ...mapMutations(['hideHeader']),
      ...mapActions(['settingAlertMsg']),
      goHomePage () {
        this.$router.push('/')
      },
      updateEmail (val) {
        this.email = val
      },
      sendEmail () {
        api.sendEmailForResetPassword(this.email)
          .then(() => {
            // 입력하신 이메일 주소로 가입한 회원정보가 존재한다면, 비밀번호 재설정할 수 있는 메일을 몇 분 안에 받을 수 있습니다.
            this.alert.message = '입력하신 이메일 주소로 비밀번호를 재설정할 수 있는 메일을 전송했습니다.'
            this.alert.type = 'green'
            this.settingAlertMsg(this.alert)
            this.$router.push('/login')
          })
          .catch(() => {
            this.alert.message = '입력하신 이메일 주소로 가입한 회원 정보가 없습니다.'
            this.alert.type = 'red'
            this.settingAlertMsg(this.alert)
          })
      },
      goLogin () {
        this.$router.push('/login')
      }
    }
  }

</script>
