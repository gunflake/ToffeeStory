<template>
  <div class="login">
<!--    <h2>{{info}}</h2>-->
    <div class="bg-grey-lighter min-h-screen flex flex-col">
      <div class="container max-w-sm mx-auto flex-1 flex flex-col items-center px-2">
        <div class="bg-white px-6 py-8 rounded text-black w-full">
          <img src="@/assets/image/toffeeStory.png" class="pb-10">
          <form method="POST">
            <InputBox title="Email" type="email" name="email" id="email"/>
            <InputBox title="Password" type="password" name="password" id="password" :visible=visible url="/find/password" />
            <button class="w-full bg-gray-800 hover:bg-blue-700 text-white font-bold py-2 rounded mt-3">
              Login
            </button>
          </form>

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
  // import axios from 'axios'
  import InputBox from '@/components/InputBox'
  export default {
    name: 'login',
    components: {
      InputBox
    },
    data () {
      return {
        info: null,
        visible: true
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
