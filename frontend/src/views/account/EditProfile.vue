<template>
  <div>
    <h3 class="mb-4 text-xl text-black font-bold">Edit Profile</h3>
    <!-- 입력 폼 -->
    <div class="w-full">
      <div class="flex">
        <!-- 프로필 이미지 -->
        <div class="w-1/3 flex justify-center">
          <div class="block">
            <img class="h-40 w-40 rounded-full mb-4" src="https://randomuser.me/api/portraits/women/21.jpg">
            <a>Change Profile Image</a>
          </div>
        </div>
        <!-- 회원 정보 -->
        <div class="w-2/3">
          <input-with-error type="text" title="Full Name" id="fullName" minlength="2" maxlength="30"
                            v-on:inputEvent="validFullName" v-model="account.accountName"
                            :errorMsg="fullNameMsg" :visible="fullNameMsgVisible"></input-with-error>
          <input-with-error type="text" title="Email" id="email" v-model="account.email" :disabled="true"></input-with-error>
          <input-with-error type="text" title="User Name" id="userName" minlength="2" maxlength="30"
                            v-on:inputEvent="checkUserName" v-model="account.accountId"
                            :errorMsg="userNameMsg" :visible="userNameMsgVisible" ></input-with-error>
        </div>
      </div>
      <div class="flex">
        <div class="w-1/2 mr-6">
          <input-with-error type="text" title="Instagram User Name" id="instagram" maxlength="30"
                            v-model="account.instagram" :errorMsg="instagramMsg" :visible="instagramMsgVisible"></input-with-error>
        </div>
        <div class="w-1/2">
          <input-with-error type="text" title="Twitter User Name" id="twitter" maxlength="30"
                            v-model="account.twitter" :errorMsg="twitterMsg" :visible="twitterMsgVisible"></input-with-error>
        </div>
      </div>
      <div class="block">
        <div class="text-base pb-2">Bio</div>
        <textarea name="bio" v-model="account.bio" class="w-full h-24 resize-none border border-gray-700 rounded" maxlength="255"></textarea>
        <!-- submit 버튼 -->
        <button @click="updateAccount" class="w-full bg-black text-white rounded py-2 my-3">Update Account</button>
      </div>
    </div>
  </div>
</template>
<script>
  import api from '@/backend-api'
  import axios from 'axios'
  import InputWithError from '@/components/InputWithError'
  import { mapActions } from 'vuex'

  export default {
    name: 'editProfile',
    components: {
      InputWithError
    },
    data () {
      return {
        account: [],
        fullNameMsg: '',
        userNameMsg: '',
        instagramMsg: '',
        twitterMsg: '',
        fullNameMsgVisible: false,
        userNameMsgVisible: false,
        instagramMsgVisible: false,
        twitterMsgVisible: false,
        errors: [],
        alert: {
          message: null,
          type: null
        }
      }
    },
    mounted () {
      let token = localStorage.getItem('token')
      if (token == null) { return }

      let config = {
        headers: {
          'Authorization': 'Bearer ' + token,
          'Content-Type': 'multipart/form-data'
        }
      }

      api.getAccount(config).then(response => {
        if (response.status === 200) {
          this.account = response.data
        }
      }).catch(error => {
        this.errors.push(error)
      })
    },
    methods: {
      ...mapActions(['settingAlertMsg']),
      validFullName (value) {
        this.account.accountName = value

        let regex = /^[가-힣a-zA-Z\s]{2,30}$/

        if (regex.test(value)) {
          this.fullNameMsgVisible = false
        } else {
          this.fullNameMsg = 'Full Name is invalid (2 - 30 characters)' // TODO : 메세지 상수화
          this.fullNameMsgVisible = true
        }
      },
      checkUserName (value) {
        this.account.accountId = value

        let regex = /^[가-힣a-zA-Z0-9_]{2,30}$/

        if (!regex.test(value)) {
          this.userNameMsg = 'User Name is invalid (2 - 30 characters)' // TODO : 메세지 상수화
          this.userNameMsgVisible = true
        } else {
          let token = localStorage.getItem('token')
          if (token == null) { return }

          let config = {
            headers: {
              'Authorization': 'Bearer ' + token
            }
          }

          let formData = new FormData()
          formData.append('accountId', value)

          axios.post(`/api/accounts/secured/checkAccountId`, formData, config).then(response => {
            if (response.data.responseCode === 0) {
              this.userNameMsgVisible = false
            } else {
              this.userNameMsg = response.data.responseMsg
              this.userNameMsgVisible = true
            }
          }).catch(e => {
            console.log(e)
          })
        }
      },
      updateAccount () {
        if (this.account.accountName === '' || this.account.accountId === '') {
          this.alert = {
            message: 'Full Name and User Name are required.',
            type: 'red'
          }
          this.settingAlertMsg(this.alert)
        } else if (this.fullNameMsgVisible || this.userNameMsgVisible || this.instagramMsgVisible || this.twitterMsgVisible) {
          this.alert = {
            message: 'Please check your input again.',
            type: 'red'
          }
          this.settingAlertMsg(this.alert)
        } else if (confirm('Are you sure?')) {
          let token = localStorage.getItem('token')
          if (token == null) { return }

          let formData = new FormData()
          formData.append('accountName', this.account.accountName)
          formData.append('accountId', this.account.accountId)
          formData.append('instagram', this.account.instagram)
          formData.append('twitter', this.account.twitter)
          formData.append('bio', this.account.bio)

          let config = {
            headers: {
              'Authorization': 'Bearer ' + token,
              'Content-Type': 'application/json'
            }
          }

          axios.put(`/api/accounts/secured/updateAccount`, formData, config).then(response => {
            if (response.data.responseCode === 0) {
              this.alert = {
                message: 'Your account has been changed successfully!',
                type: 'green'
              }
              this.settingAlertMsg(this.alert)
            }
          }).catch(e => {
            console.log(e)
          })
        }
      }
    }
  }
</script>
