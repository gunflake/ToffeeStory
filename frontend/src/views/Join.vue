<template>
  <div class="join">
<!--    <h2>{{info}}</h2>-->
    <div class="bg-grey-lighter min-h-screen flex flex-col">
      <div class="container max-w-sm mx-auto flex-1 flex flex-col items-center px-2">
        <div class="bg-white px-6 pt-8 pb-2 rounded text-black w-full">
          <img src="@/assets/image/toffeeStory.png">
          <div class="text-base mb-6">
            Already have an account?
            <a class="no-underline border-b border-blue font-bold text-blue-700" href="/login">
              Login
            </a>
          </div>
          <form action="/login">
            <InputBox title="Full Name" type="text" name="fullName" id="fullName"/>
            <InputBox title="Email" type="email" name="email" id="email"/>
            <InputBox title="User Name" type="text" name="userName" id="userName"/>
            <InputBox title="Password" type="password" name="password" id="password"/>
            <button class="w-full bg-gray-800 hover:bg-blue-700 text-white font-bold py-2 rounded mt-3">
              Join
            </button>
          </form>
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

  // import axios from 'axios'
  import InputBox from '@/components/InputBox'
  export default {
    name: 'login',
    components: {
      InputBox
    },
    data () {
      return {
        info: null
      }
    }
    // ,
    // mounted () {
    //   axios
    //     .get('api/hello')
    //     .then(response => (this.info = response.data))
    // }
  }

</script>
