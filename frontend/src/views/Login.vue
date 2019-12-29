<template>
  <div class="login">
<!--    <h2>{{info}}</h2>-->
    <div class="bg-grey-lighter min-h-screen flex flex-col">
      <div class="container max-w-sm mx-auto flex-1 flex flex-col items-center px-2">
        <div class="bg-white px-6 py-8 rounded text-black w-full">
          <img src="@/assets/image/toffeeStory.png" class="pb-10">
          <InputBox title="Email" type="email" name="email" id="email" @sendVal="updateEmail"/>
          <InputBox title="Password" type="password" name="password" id="password" :visible=visible url="/find/password" @sendVal="updatePassword"/>
          <button @click="callLogin()" class="w-full bg-gray-800 hover:bg-blue-700 text-white font-bold py-2 rounded mt-3">
            Login
          </button>
        </div>
        <!-- 회원가입 페이지로 이동 -->
        <div class="text-base">
          Don't have an account?
          <a class="no-underline border-b border-blue font-bold text-blue-700" href="/join">
            Join
          </a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import InputBox from '@/components/InputBox'
  import { mapGetters, mapActions } from 'vuex'

  export default {
    name: 'login',
    components: {
      InputBox
    },
    data () {
      return {
        loginError: false,
        error: false,
        errors: [],
        info: null,
        visible: true,
        user: {
          email: '',
          password: ''
        }
      }
    },
    computed: {
      ...mapGetters(['isLoggedIn'])
    },
    methods: {
      ...mapActions(['loginProcess']),
      updateEmail (val) {
        this.user.email = val
      },
      updatePassword (val) {
        this.user.password = val
      },
      callLogin () {
        this.errors = []
        this.loginProcess({ email: this.user.email, password: this.user.password })
          .then(() => {
            console.log('login success')
            this.$router.push('/')
          })
          .catch(error => {
            this.loginError = true
            this.errors.push(error)
            this.error = true
          })
      }
    }
  }
</script>
