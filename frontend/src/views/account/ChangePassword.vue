<template>
  <div>
    <h3 class="mb-4 text-xl text-black font-bold">Change Password</h3>
    <!-- 입력 폼 -->
    <div class="w-full">
      <input-with-error type="password" title="Current Password" id="currentPwd"
                        v-on:inputEvent="checkCurrentPwd" v-on:enterEvent="changePwd" v-model="account.accountPwd"
                        :errorMsg="currentPwdMsg" :visible="currentPwdMsgVisible"></input-with-error>
      <input-with-error type="password" title="New Password" id="newPwd"
                        v-on:inputEvent="validPwd" v-on:enterEvent="changePwd" v-model="account.accountNewPwd"
                        :errorMsg="newPwdMsg" :visible="newPwdMsgVisible"></input-with-error>
      <input-with-error type="password" title="Password Confirm" id="confirmPwd"
                        v-on:inputEvent="confirmPwd" v-on:enterEvent="changePwd" v-model="account.accountConfirmPwd"
                        :errorMsg="confirmPwdMsg" :visible="confirmPwdMsgVisible"></input-with-error>
      <!-- submit 버튼 -->
      <button @click="changePwd" class="w-full bg-black text-white rounded py-2 mt-3">Change Password</button>
    </div>
  </div>
</template>
<script>
  import axios from 'axios'
  import InputWithError from '@/components/InputWithError'
  import { mapActions } from 'vuex'

  export default {
    name: 'changePassword',
    components: {
      InputWithError
    },
    data () {
      return {
        account: {
          accountPwd: '',
          accountNewPwd: '',
          accountConfirmPwd: ''
        },
        errors: [],
        currentPwdMsg: '',
        newPwdMsg: '',
        confirmPwdMsg: '',
        currentPwdMsgVisible: false,
        newPwdMsgVisible: false,
        confirmPwdMsgVisible: false,
        alert: {
          message: null,
          type: null
        }
      }
    },
    methods: {
      ...mapActions(['settingAlertMsg']),
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

        axios.post(`/api/accounts/secured/checkCurrentPassword`, formData, config)
          .then(response => {
            if (response.status === 200) {
              this.currentPwdMsgVisible = false
            } if (response.status === 201) {
              this.currentPwdMsg = response.data
              this.currentPwdMsgVisible = true
            }
          }).catch(e => {
            console.log(e)
          })
      },
      validPwd (value) {
        this.account.accountNewPwd = value

        let regex = /(?=.*\d{1,50})(?=.*[~`!@#$%^&*()-+=]{1,50})(?=.*[a-zA-Z]{1,50}).{8,20}$/

        if (regex.test(value)) {
          this.newPwdMsgVisible = false
        } else {
          this.newPwdMsg = 'New Password is invalid (8 - 20 characters, include alphabets, numbers and special characters)' // TODO : 메세지 상수화
          this.newPwdMsgVisible = true
        }
      },
      confirmPwd (value) {
        this.account.accountConfirmPwd = value

        if (value === this.account.accountNewPwd) {
          this.confirmPwdMsgVisible = false
        } else {
          this.confirmPwdMsg = 'Password confirmation doesn\'t match New Password'
          this.confirmPwdMsgVisible = true
        }
      },
      changePwd () {
        if (this.account.accountPwd === '' || this.account.accountNewPwd === '' || this.account.accountConfirmPwd === '') {
          this.alert = {
            message: 'Please enter all the information.',
            type: 'red'
          }
          this.settingAlertMsg(this.alert)
        } else if (this.currentPwdMsgVisible || this.newPwdMsgVisible || this.confirmPwdMsgVisible) {
          this.alert = {
            message: 'Please check your input again.',
            type: 'red'
          }
          this.settingAlertMsg(this.alert)
        } else if (confirm('Are you sure?')) {
          let token = localStorage.getItem('token')
          if (token == null) { return }

          let formData = new FormData()
          formData.append('accountNewPwd', this.account.accountNewPwd)

          let config = {
            headers: {
              'Authorization': 'Bearer ' + token
            }
          }

          axios.patch(`/api/accounts/secured/changePassword`, formData, config).then(response => {
            if (response.status === 200) {
              this.alert = {
                message: response.data,
                type: 'green'
              }
              this.settingAlertMsg(this.alert)
              this.$router.push('/settings')
            }
          }).catch(e => {
            console.log(e)
          })
        }
      }
    }
  }
</script>
