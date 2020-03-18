<template>
  <div class="join">
    <div class="bg-grey-lighter flex flex-col">
      <div class="container w-full md:w-2/3 lg:w-1/2 max-w-2xl mx-auto flex-1 flex flex-col items-center px-2">
        <div class="bg-white px-6 pt-8 pb-2 rounded text-black w-full mt-10">
          <img src="../assets/image/logo.png" class="h-24 w-24 mx-auto" @click="goHome()" style="cursor: pointer"/>
          <div class="mt-4 font-bold text-2xl text-center">Create Account</div>
          <div class="text-base mb-6 text-center my-4">
            Already have an account?
            <a class="no-underline border-b border-blue font-bold text-blue-700" @click="goLogin" style="cursor: pointer">
              Login
            </a>
          </div>
          <InputBox title="Full Name" type="text" name="fullName" id="fullName" @sendVal="updateFullName"/>
          <InputBox title="Email" type="email" name="email" id="email" @sendVal="updateEmail"/>
          <InputBox title="User Name" type="text" name="userName" id="userName" @sendVal="updateUserName"/>
          <InputBox title="Password" type="password" name="password" id="password" @sendVal="updatePassword"/>
          <button @click="createAccount()" class="w-full bg-gray-800 hover:bg-blue-700 text-white font-bold py-2 rounded mt-3">
            Join
          </button>
        </div>
        <!-- 회원가입 정책-->
        <div class="text-xs font-semibold">
          By joining, you agree to the Terms and Privacy Policy.
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import InputBox from '@/components/InputBox'
  import { mapActions, mapMutations } from 'vuex'

  export default {
    name: 'login',
    components: {
      InputBox
    },
    data () {
      return {
        info: null,
        user: {
          fullName: '',
          userName: '',
          email: '',
          password: ''
        },
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
      ...mapMutations(['hideHeader', 'showHeader']),
      ...mapActions(['createProcess', 'settingAlertMsg']),
      updateFullName (val) {
        this.user.fullName = val
      },
      updateEmail (val) {
        this.user.email = val
      },
      updateUserName (val) {
        this.user.userName = val
      },
      updatePassword (val) {
        this.user.password = val
      },
      createAccount () {
        this.errors = []
        this.createProcess({ fullName: this.user.fullName, userName: this.user.userName, email: this.user.email, password: this.user.password })
          .then(message => {
            this.alert.message = message
            this.alert.type = 'green'
            this.settingAlertMsg(this.alert)
            this.$router.push('/login')
          })
          .catch(message => {
            this.alert.message = message
            this.alert.type = 'red'
            this.settingAlertMsg(this.alert)
          })
      },
      goLogin () {
        this.$router.push('/login')
      },
      goHome () {
        this.$router.push('/')
      }
    }
  }

</script>
