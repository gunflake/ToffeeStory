<template>
  <div>
    <h3 class="mb-4 text-xl text-black font-bold">Close Account</h3>
    <!-- 입력 폼 -->
    <div class="w-full">
      <div class="text-base mb-4">
        <span class="text-red-500 font-bold">Warning!  </span>
        <span class="text-black">Closing your account is irreversible.</span>
      </div>
      <form action="/">
        <!-- 비밀번호 입력 -->
        <input-with-error type="password" title="Current Password" id="currentPwd" v-on:inputVal="checkCurrentPwd"
                          :errorMsg="currentPwdMsg" :visible="currentPwdMsgVisible" :value="account.accountPwd"></input-with-error>
        <!-- submit 버튼 -->
        <button @click="closeAccount" class="w-full bg-red-500 text-white rounded py-2 mt-3">Delete Account</button>
      </form>
    </div>
  </div>
</template>
<script>
  import axios from 'axios'
  import { mapActions } from 'vuex'
  import InputWithError from '@/components/InputWithError'

  export default {
    name: 'closeAccount',
    components: {
      InputWithError
    },
    data () {
      return {
        account: {
          accountPwd: ''
        },
        errors: [],
        currentPwdMsg: '',
        currentPwdMsgVisible: false
      }
    },
    methods: {
      ...mapActions(['logoutProcess']),
      checkCurrentPwd (value) {
        this.account.accountPwd = value

        let token = localStorage.getItem('token')
        if (token == null) { return }

        let config = {
          headers: {
            'Authorization': 'Bearer ' + token
          }
        }

        let formData = new FormData()
        formData.append('accountPwd', value)

        axios.post(`/api/account/secured/checkCurrentPassword`, formData, config)
          .then(response => {
            if (response.data.responseCode === 0) {
              this.currentPwdMsgVisible = false
            } else {
              this.currentPwdMsg = response.data.responseMsg
              this.currentPwdMsgVisible = true
            }
          }).catch(e => {
          console.log(e)
        })
      },
      closeAccount () {
        if (this.account.accountPwd === '') {
          alert('Please enter all the information')
        } else if (this.currentPwdMsgVisible) {
          alert('Please check your input again')
        } else if (confirm('Are you sure? This action cannot be undone.')) {
          let token = localStorage.getItem('token')
          if (token == null) {
            return
          }

          let formData = new FormData()
          formData.append('accountPwd', this.account.accountPwd)

          let config = {
            headers: {
              'Authorization': 'Bearer ' + token
            }
          }

          axios.put(`/api/account/secured/closeAccount`, formData, config)
            .then(response => {
              if (response.data.responseCode === 0) {
                alert('Success')
                this.logoutProcess()
                this.$router.push('/')
              } else {
                alert('Fail')
              }
            }).catch(e => {
            console.log(e)
          })
        }
      }
    }
  }
</script>
