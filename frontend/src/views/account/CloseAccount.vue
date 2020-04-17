<template>
  <div>
    <h3 class="mb-4 text-xl text-black font-bold">Close Account</h3>
    <!-- 입력 폼 -->
    <div class="w-full">
      <div class="text-base mb-4">
        <span class="text-red-500 font-bold">Warning!  </span>
        <span class="text-black">Closing your account is irreversible.</span>
      </div>
      <input-with-error type="password" title="Current Password" id="currentPwd"
                        v-on:inputEvent="checkCurrentPwd" v-on:enterEvent="closeAccount" v-model="account.accountPwd"
                        :errorMsg="currentPwdMsg" :visible="currentPwdMsgVisible"></input-with-error>
      <!-- submit 버튼 -->
      <button @click="closeAccount" class="w-full bg-red-500 text-white rounded py-2 mt-3">Delete Account</button>
    </div>
  </div>
</template>
<script>
  import axios from 'axios'
  import InputWithError from '@/components/InputWithError'
  import { mapActions } from 'vuex'
  import config from '../config.js'

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
        currentPwdMsgVisible: false,
        alert: {
          message: null,
          type: null
        }
      }
    },
    methods: {
      ...mapActions(['settingAlertMsg', 'logoutProcess']),
      checkCurrentPwd (value) {
        this.account.accountPwd = value

        let token = localStorage.getItem('token')
        if (token == null) { return }

        let config = {
          headers: {
            'Authorization': 'Bearer ' + token,
            'Accept': 'application/json',
            'Content-Type': 'application/json'
          }
        }

        let formData = new FormData()
        formData.append('accountPwd', value)

        axios.put(`/api/accounts/secured/checkCurrentPassword`, formData, config).then(response => {
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
          this.alert = {
            message: config.AccSettingsMethods.ENTER_PWD_MSG,
            type: config.AccSettingsMethods.TYPE_ERROR
          }
          this.settingAlertMsg(this.alert)
        } else if (this.currentPwdMsgVisible) {
          this.alert = {
            message: config.AccSettingsMethods.INPUT_CHK_MSG,
            type: config.AccSettingsMethods.TYPE_ERROR
          }
          this.settingAlertMsg(this.alert)
        } else if (confirm(config.AccSettingsMethods.UNDONE_CONFIRM_MSG)) {
          let token = localStorage.getItem('token')
          if (token == null) { return }

          let formData = new FormData()
          formData.append('accountPwd', this.account.accountPwd)

          let config = {
            headers: {
              'Authorization': 'Bearer ' + token
            }
          }

          axios.post(`/api/accounts/secured/deleteAccount`, formData, config).then(response => {
            if (response.status === 200) {
              this.alert = {
                message: response.data,
                type: config.AccSettingsMethods.TYPE_OK
              }
              this.$router.push('/')
              this.settingAlertMsg(this.alert)
              this.logoutProcess()
            }
          }).catch(e => {
            console.log(e)
          })
        }
      }
    }
  }
</script>
