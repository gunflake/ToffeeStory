<template>
  <div>
    <h3 class="mb-4 text-xl text-black font-bold">Change Password</h3>
    <!-- 입력 폼 -->
    <div class="w-full">
      <!-- 비밀번호 입력 -->
      <input-with-error type="password" title="Current Password" id="currentPwd" v-on:inputVal="checkCurrentPwd"
                        :errorMsg="currentPwdMsg" :visible="currentPwdMsgVisible" :value="account.accountPwd"></input-with-error>
      <input-with-error type="password" title="New Password" id="newPwd" v-on:inputVal="validPwd"
                        :errorMsg="newPwdMsg" :visible="newPwdMsgVisible" :value="account.accountNewPwd"></input-with-error>
      <input-with-error type="password" title="Password Confirm" id="confirmPwd" v-on:inputVal="confirmPwd"
                        :errorMsg="confirmPwdMsg" :visible="confirmPwdMsgVisible" :value="account.accountConfirmPwd"></input-with-error>
      <!-- submit 버튼 -->
      <button @click="changePwd" class="w-full bg-black text-white rounded py-2 mt-3">Change Password</button>
    </div>
  </div>
</template>
<script>
  import axios from 'axios'
  import InputWithError from '@/components/InputWithError'

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
        confirmPwdMsgVisible: false
      }
    },
    methods: {
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
          alert('Please enter all the information')
        } else if (this.currentPwdMsgVisible || this.newPwdMsgVisible || this.confirmPwdMsgVisible) {
          alert('Please check your input again')
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

          axios.put(`/api/account/secured/changePassword`, formData, config)
            .then(response => {
              if (response.data.responseCode === 0) {
                alert('Success')
                this.$router.push('/account')
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
