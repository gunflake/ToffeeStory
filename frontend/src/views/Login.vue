<template>
  <div class="login">
    <div class="bg-grey-lighter flex flex-col">
      <div class="container w-full md:w-2/3 lg:w-1/2 max-w-2xl mx-auto flex-1 flex flex-col items-center px-2">
        <div class="bg-white px-6 py-8 rounded text-black w-full mt-10">
          <img src="../assets/image/logo.png" class="h-24 w-24 mx-auto" @click="goHome()" style="cursor: pointer"/>
          <div class="mt-4 font-bold text-2xl text-center">Login</div>
          <div class="my-2 text-center">Welcome back.</div>
          <InputBox title="Email" type="email" name="email" id="email" @sendVal="updateEmail"/>
          <InputBox title="Password" type="password" name="password" id="password" :visible=visible url="/find/password" @sendVal="updatePassword"/>
          <button @click="callLogin()" class="w-full bg-gray-800 hover:bg-blue-700 text-white font-bold py-2 rounded mt-3">
            Login
          </button>
        </div>
        <!-- 회원가입 페이지로 이동 -->
        <div class="text-base">
          Don't have an account?
          <a class="no-underline border-b border-blue font-bold text-blue-700" @click="goJoin" style="cursor: pointer">
            Join
          </a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import InputBox from '@/components/InputBox'
  import { mapGetters, mapActions, mapMutations } from 'vuex'

  export default {
    name: 'login',
    components: {
      InputBox
    },
    data () {
      return {
        info: null,
        visible: true,
        user: {
          email: '',
          password: ''
        },
        alert: {
          message: null,
          type: null
        }
      }
    },
    computed: {
      ...mapGetters(['isLoggedIn'])
    },
    mounted () {
      this.hideHeader()
    },
    methods: {
      ...mapMutations(['hideHeader', 'showHeader']),
      ...mapActions(['loginProcess', 'settingAlertMsg']),
      updateEmail (val) {
        this.user.email = val
      },
      updatePassword (val) {
        this.user.password = val
      },
      callLogin () {
        this.loginProcess({ email: this.user.email, password: this.user.password })
          .then(() => {
            this.$router.push('/')
          })
          .catch(message => {
            this.alert.message = message
            this.alert.type = 'red'
            this.settingAlertMsg(this.alert)
          })
      },
      goJoin () {
        this.$router.push('/join')
      },
      goHome () {
        this.$router.push('/')
      }
    }
  }
</script>
