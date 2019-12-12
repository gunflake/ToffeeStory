<template>
  <div class="join">
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
          <!-- Full Name -->
          <div class="text-base pb-2 text-left">
            Full Name
          </div>
          <label>
            <input type="text" class="block border border-gray-700 w-full p-2 rounded mb-4" v-model="user.fullName" />
          </label>
          <!-- Email -->
          <div class="text-base pb-2 text-left">
            Email
          </div>
          <label>
            <input type="email" class="block border border-gray-700 w-full p-2 rounded mb-4" v-model="user.email" />
          </label>
<!--          <InputBox title="Email" type="email" name="email" id="email" @sendVal="updateEmail"/>-->
          <!-- User Name -->
          <div class="text-base pb-2 text-left">
            User Name
          </div>
          <label>
            <input type="text" class="block border border-gray-700 w-full p-2 rounded mb-4" v-model="user.userName" />
          </label>
          <!-- Password -->
          <div class="text-base pb-2 text-left">
            Password
          </div>
          <label>
            <input type="password" class="block border border-gray-700 w-full p-2 rounded mb-4" v-model="user.password" />
          </label>
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
  // import InputBox from '@/components/InputBox'
  import api from '@/backend-api'
  export default {
    name: 'login',
    components: {
      // InputBox
    },
    data () {
      return {
        info: null,
        user: {
          fullName: '',
          userName: '',
          email: '',
          password: ''
        }
      }
    },
    methods: {
      updateEmail (email) {
        this.user.email = email
      },
      createAccount () {
        api.createAccount(this.user.fullName, this.user.userName, this.user.email, this.user.password).then(response => {
          this.response = response.data
          console.log(response.data)
          // Login 페이지로 이동
          this.$router.push('/login')
        })
          .catch(e => {
            this.errors.push(e)
          }
          )
      }
    }
  }

</script>
