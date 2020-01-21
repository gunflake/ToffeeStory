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
          <div class="input-with-error">
            <div class="text-base pb-2 text-left">Full Name</div>
            <input type="text" name="fullName" v-model="account.accountName" class="block border border-gray-700 w-full p-2 rounded mb-4 bg-gray-200" disabled/>
          </div>
          <div class="input-with-error">
            <div class="text-base pb-2 text-left">Email</div>
            <input type="text" name="email" v-model="account.email" class="block border border-gray-700 w-full p-2 rounded mb-4 bg-gray-200" disabled/>
          </div>
          <div class="input-with-error">
            <div class="text-base pb-2 text-left">User Name</div>
            <input type="text" name="userName" v-model="account.accountId" class="block border border-gray-700 w-full p-2 rounded mb-4"/>
          </div>
        </div>
      </div>
      <div class="flex">
        <div class="inputBox w-1/2 mr-6">
          <div class="text-base pb-2 text-left">Instagram User Name</div>
          <input type="text" name="instagram" v-model="account.instagram" class="block border border-gray-700 w-full p-2 rounded mb-4"/>
        </div>
        <div class="inputBox w-1/2">
          <div class="text-base pb-2 text-left">Twitter User Name</div>
          <input type="text" name="twitter" v-model="account.twitter" class="block border border-gray-700 w-full p-2 rounded mb-4"/>
        </div>
      </div>
      <div class="block">
        <div class="text-base pb-2">Bio</div>
        <textarea name="bio" v-model="account.bio" class="w-full h-24 resize-none border border-gray-700 rounded"></textarea>
        <!-- submit 버튼 -->
        <button role="button" @click="updateAccount()" class="w-full bg-black text-white rounded py-2 my-3">Update Account</button>
      </div>
    </div>
  </div>
</template>
<script>
  import api from '@/backend-api'
  import axios from 'axios'

  export default {
    data () {
      return {
        account: {
          accountNo: '',
          accountName: '',
          email: '',
          accountId: '',
          instagram: '',
          twitter: '',
          bio: '',
          accountPwd: ''
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
          this.account.accountName = account.accountName
          this.account.email = account.email
          this.account.accountId = account.accountId
          this.account.instagram = account.instagram
          this.account.twitter = account.twitter
          this.account.bio = account.bio
          this.account.accountPwd = account.accountPwd
          this.account.accountNo = account.accountNo
        }
      })
        .catch(error => {
          this.errors.push(error)
        })
    },
    methods: {
      updateAccount () {
        let token = localStorage.getItem('token')

        if (token == null) { return }

        let config = {
          headers: {
            'Authorization': 'Bearer ' + token
          }
        }

        axios.post(`/api/account/secured/updateAccount`, this.account, config)
          .then(response => {
            if (response.data === this.account.accountNo) {
              // Alert
            }
          }).catch(e => {
            console.log(e)
          })
      }
    }
  }
</script>
