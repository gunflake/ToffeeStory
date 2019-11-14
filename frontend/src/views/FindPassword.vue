<template>
  <div class="join">
<!--    <h2>{{info}}</h2>-->
    <div class="bg-grey-lighter min-h-screen flex flex-col">
      <div class="container max-w-sm mx-auto flex-1 flex flex-col items-center px-2">
        <div class="bg-white px-6 pt-8 pb-2 rounded text-black w-full">
          <img src="@/assets/image/toffeeStory.png">
          <div class="text-2xl font-semibold text-left my-6">
            Forgot your password?
          </div>
          <form action="/login">
            <InputBox title="Email" type="email" name="email"/>
            <button class="w-full bg-gray-800 hover:bg-blue-700 text-white font-bold py-2 rounded mt-3">
              Send me reset password instructions
            </button>
          </form>
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
