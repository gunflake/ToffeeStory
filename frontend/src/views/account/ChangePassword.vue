<template>
  <div>
    <h3 class="mb-4 text-xl text-black font-bold">Change Password</h3>
    <!-- 입력 폼 -->
    <div class="w-full">
      <!-- 비밀번호 입력 -->
      <div class="input-with-error mb-4">
        <div class="text-base text-left mb-2">Current Password</div>
        <input @keydown="checkCurrentPwd" @keyup="checkCurrentPwd" @keypress="checkCurrentPwd" type="text" name="currentPwd" v-model="account.accountPwd" class="block border border-gray-700 w-full p-2 rounded"/>
        <div class="form-error-inline">Current Password is invalid</div>
      </div>
      <div class="input-with-error mb-4">
        <div class="text-base mb-2 text-left">New Password</div>
        <input type="text" name="newPwd" v-model="account.accountNewPwd" class="block border border-gray-700 w-full p-2 rounded"/>
        <div class="form-error-inline">New Password is invalid (minimum is 6 characters, ...)</div>
      </div>
      <div class="input-with-error mb-4">
        <div class="text-base mb-2 text-left">Password Confirm</div>
        <input @keydown="confirmPwd" @keyup="confirmPwd" @keypress="confirmPwd" type="text" name="confirmPwd" v-model="account.accountConfirmPwd" class="block border border-gray-700 w-full p-2 rounded"/>
        <div class="form-error-inline">Password confirmation doesn't match New Password</div>
      </div>
      <!-- submit 버튼 -->
      <button @click="changePwd" class="w-full bg-black text-white rounded py-2 mt-3">Change Password</button>
    </div>
  </div>
</template>
<style>
  .form-error-inline {
    margin-top: 6px;
    padding: 3px 9px;
    font-size: 12px;
    border-radius: 4px;
    background-color: #f9ded8;
  }
</style>
<script>
  import api from '@/backend-api'
  import axios from 'axios'
  import { mapActions } from 'vuex'

  export default {
    name: 'editProfile',
    components: {
    },
    data () {
      return {
        account: {
          accountNo: '',
          accountPwd: '',
          accountNewPwd: '',
          accountConfirmPwd: '',
          email: ''
        },
        errors: []
      }
    },
    beforeCreate () {
      let token = localStorage.getItem('token')

      if (token == null) { return }

      let config = {
        headers: {
          'Authorization': 'Bearer ' + token
        }
      }

      api.getAccount(config).then(response => {
        if (response.status === 200) {
          let account = response.data
          this.account.accountNo = account.accountNo
          this.account.email = account.email
        }
      })
        .catch(error => {
          this.errors.push(error)
        })
    },
    methods: {
      checkCurrentPwd () {
        let token = localStorage.getItem('token')

        if (token == null) { return }

        let config = {
          headers: {
            'Authorization': 'Bearer ' + token
          }
        }

        axios.post(`/api/account/secured/checkCurrentPassword`, this.account, config)
          .then(response => {
            console.log(response.data)
            if (response.data === this.account.accountNo) {
              console.log('current : ok')
            } else if (response.data === 0) {
              console.log('current : wrong password')
            }
          }).catch(e => {
            console.log(e)
          })
      },
      validPwd () {
        // let re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        // if (re.test(this.account.accountNewPwd)) {
        //   console.log('new : ok')
        // } else {
        //   console.log('new : invalid password')
        // }
      },
      confirmPwd () {
        if (this.account.accountNewPwd === this.account.accountConfirmPwd) {
          console.log('confirm : ok')
        } else {
          console.log('confirm : different password')
        }
      },
      changePwd () {
        console.log('change password')
        let token = localStorage.getItem('token')

        if (token == null) { return }

        let config = {
          headers: {
            'Authorization': 'Bearer ' + token
          }
        }

        axios.post(`/api/account/secured/changePassword`, this.account, config)
          .then(response => {
            if (response.data === this.account.accountNo) {
              // Alert 띄우기, 로그아웃 후 로그인 페이지로 이동
              mapActions(['logoutProcess'])
              this.$router.push('/login')
            }
          }).catch(e => {
            console.log(e)
          })
      }
    }
  }
</script>
