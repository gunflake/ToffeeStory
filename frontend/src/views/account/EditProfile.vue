<template>
  <div>
    <h3 class="mb-4 text-xl text-black font-bold">Edit Profile</h3>
    <!-- 입력 폼 -->
    <div class="w-full">
      <form action="/">
        <div class="flex">
          <!-- 프로필 이미지 -->
          <div class="w-1/3 flex justify-center">
            <div class="block">
              <img class="h-40 w-40 rounded-full mb-4" src="https://randomuser.me/api/portraits/women/21.jpg">
              <a>Change Profile Image</a>
            </div>
          </div>
          <!-- 회원 정보 -->
          <div class="w-2/3 mx-3">
            <div class="inputBox">
              <div class="text-base pb-2 text-left">Full Name</div>
              <input type="text" name="fullName" class="block border border-gray-700 w-full p-2 rounded mb-4" v-model="fullName"/>
            </div>
            <div class="inputBox">
              <div class="text-base pb-2 text-left">Email</div>
              <input type="text" name="email" class="block border border-gray-700 w-full p-2 rounded mb-4" v-model="email"/>
            </div>
            <div class="inputBox">
              <div class="text-base pb-2 text-left">User Name</div>
              <input type="text" name="userName" class="block border border-gray-700 w-full p-2 rounded mb-4" v-model="userName"/>
            </div>
          </div>
        </div>
        <div class="flex">
          <div class="inputBox w-1/2 mx-3">
            <div class="text-base pb-2 text-left">Instagram User Name</div>
            <input type="text" name="instagram" class="block border border-gray-700 w-full p-2 rounded mb-4" v-model="instagram"/>
          </div>
          <div class="inputBox w-1/2 mx-3">
            <div class="text-base pb-2 text-left">Twitter User Name</div>
            <input type="text" name="twitter" class="block border border-gray-700 w-full p-2 rounded mb-4" v-model="twitter"/>
          </div>
        </div>
        <div class="block mx-3">
          <div class="text-base pb-2">Bio</div>
          <textarea class="w-full h-24 resize-none border border-gray-700 rounded" v-model="bio"></textarea>
          <!-- submit 버튼 -->
          <button role="button" class="w-full bg-black text-white rounded py-2 my-3">Update Account</button>
        </div>
      </form>
    </div>
  </div>
</template>
<script>
  import api from '@/backend-api'

  export default {
    components: {
    },
    data () {
      return {
        fullName: '',
        email: '',
        userName: '',
        instagram: '',
        twitter: '',
        bio: '',
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
          this.fullName = account.accountName
          this.email = account.email
          this.userName = account.accountId
          this.instagram = account.instagram
          this.twitter = account.twitter
          this.bio = account.bio
        }
      })
        .catch(error => {
          this.errors.push(error)
        })
    },
    methods: {
      updateAccount () {
      }
    }
  }
</script>
