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
          <InputBox title="Email" type="email" name="email" id="email"/>
          <button class="w-full bg-gray-800 hover:bg-blue-700 text-white font-bold py-2 rounded mt-3" @click="sendEmail">
            Send me reset password instructions
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

  // import axios from 'axios'
  import InputBox from '@/components/InputBox'
  import { mapMutations } from 'vuex'
  export default {
    name: 'login',
    components: {
      InputBox
    },
    data () {
      return {
        info: null
      }
    },
    mounted () {
      this.hideHeader()
    },
    methods: {
      ...mapMutations(['hideHeader']),
      goHomePage () {
        this.$router.push('/')
      },
      sendEmail () {
        this.$router.push('/login')
      },
      goLogin () {
        this.$router.push('/login')
      }
    }
  }

</script>
