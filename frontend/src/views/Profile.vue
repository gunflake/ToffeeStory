<template>
  <div>
    <!-- 프로필 -->
    <div id="profile" class="flex justify-center bg-white py-20">
      <img class="h-40 w-40 rounded-full mr-10" src="https://randomuser.me/api/portraits/women/21.jpg">
      <div class="text-left max-w-md">
        <div class="flex items-center pb-3">
          <div class="pr-4">
            <h2 class="font-bold text-3xl">{{ userName }}</h2>
          </div>
          <div class="pr-4">
            <router-link to="/account">
              <button class="text-sm bg-transparent text-gray-700 font-medium hover:text-black py-1 px-4 border border-gray-400 hover:border-black rounded">Edit</button>
            </router-link>
          </div>
          <div class="pr-4">
            <button class="text-sm bg-transparent text-gray-700 font-medium hover:text-black py-1 px-4 border border-gray-400 hover:border-black rounded" @click="logout()">Logout</button>
          </div>
        </div>
        <div class="flex items-center pb-3">
          <div id="instagram" class="pr-4 text-sm text-gray-700 hover:text-black">
            <a v-bind:href="instagramLink + instagram" target="_blank">{{ instagram }}</a>
          </div>
          <div id="twitter" class="pr-4 text-sm text-gray-700 hover:text-black">
            <a v-bind:href="twitterLink + twitter" target="_blank">{{ twitter }}</a>
          </div>
        </div>
        <div class="flex items-center pb-3">
          <div id="bio" class="pr-4 text-sm text-black">{{ bio }}</div>
        </div>
      </div>
    </div>
    <!-- 네비게이션 -->
    <nav class="bg-white px-8 pt-2 shadow-md">
      <div class="-mb-px flex justify-left">
        <a class="no-underline text-teal-dark border-b-2 border-transparent hover:border-black tracking-wide font-bold text-xs py-3 mr-8" href="#">
          My Menu
        </a>
        <a class="no-underline text-grey-dark border-b-2 border-transparent hover:border-black tracking-wide font-bold text-xs py-3 mr-8" href="#">
          Likes
        </a>
        <a class="no-underline text-grey-dark border-b-2 border-transparent hover:border-black tracking-wide font-bold text-xs py-3 mr-8" href="#">
          Bookmarks
        </a>
      </div>
    </nav>
  </div>
</template>

<script>
  import { mapActions } from 'vuex'
  import api from '@/backend-api'

  export default {
    name: 'profile',
    data () {
      return {
        userName: '',
        instagram: '',
        twitter: '',
        bio: '',
        instagramLink: 'https://www.instagram.com/',
        twitterLink: 'https://www.twitter.com/',
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
      ...mapActions(['logoutProcess']),
      logout () {
        this.logoutProcess()
        this.$router.push('/')
      }
    }
  }
</script>
