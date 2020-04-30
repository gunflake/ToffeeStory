<template>
  <div>
    <!-- 프로필 -->
    <div id="profile" class="flex justify-center bg-white py-20">
      <img class="h-40 w-40 rounded-full mr-10" style="object-fit: cover" :src="account.src" @error="changeProfileImg">
      <div class="text-left max-w-md">
        <div class="flex items-center pb-3">
          <div class="pr-4">
            <h2 class="font-bold text-3xl">{{ $route.params.username }}</h2>
          </div>
          <div v-if="isMyProfile" class="flex">
            <div class="pr-4">
              <router-link to="/settings">
                <button class="text-sm bg-transparent text-gray-700 font-medium hover:text-black py-1 px-4 border border-gray-400 hover:border-black rounded">Edit</button>
              </router-link>
            </div>
            <div class="pr-4">
              <button class="text-sm bg-transparent text-gray-700 font-medium hover:text-black py-1 px-4 border border-gray-400 hover:border-black rounded" @click="logout()">Logout</button>
            </div>
          </div>
        </div>
        <div class="flex items-center pb-3">
          <a v-if="account.instagram != null && account.instagram != ''" class="flex pr-5 items-center text-sm text-gray-600 hover:text-black fill-current" :href="instagramLink + account.instagram" target="_blank" rel="nofollow">
            <svg class="mr-1" version="1.1" viewBox="0 0 32 32" width="24" height="24" aria-hidden="false">
              <path d="M16 5.34c3.47 0 3.88 0 5.25.08a7.22 7.22 0 0 1 2.42.45 3.89 3.89 0 0 1 1.49 1 3.89 3.89 0 0 1 1 1.49 7.22 7.22 0 0 1 .45 2.42c.06 1.37.08 1.78.08 5.25s0 3.88-.08 5.25a7.22 7.22 0 0 1-.45 2.42 4.3 4.3 0 0 1-2.46 2.46 7.22 7.22 0 0 1-2.42.45c-1.37.06-1.78.08-5.25.08s-3.88 0-5.25-.08a7.22 7.22 0 0 1-2.42-.45 3.89 3.89 0 0 1-1.49-1 3.89 3.89 0 0 1-1-1.49 7.22 7.22 0 0 1-.45-2.42c-.06-1.37-.08-1.78-.08-5.25s0-3.88.08-5.25a7.22 7.22 0 0 1 .45-2.42 3.89 3.89 0 0 1 1-1.49 3.89 3.89 0 0 1 1.49-1 7.22 7.22 0 0 1 2.42-.45c1.37-.06 1.78-.08 5.25-.08m-.03-2.31c-3.53 0-4 0-5.36.08a9.57 9.57 0 0 0-3.16.6 6.66 6.66 0 0 0-3.8 3.8 9.57 9.57 0 0 0-.6 3.16c-.08 1.36-.08 1.83-.08 5.36s0 4 .08 5.36a9.57 9.57 0 0 0 .6 3.16 6.66 6.66 0 0 0 3.8 3.8 9.57 9.57 0 0 0 3.16.6c1.36.08 1.83.08 5.36.08s4 0 5.36-.08a9.57 9.57 0 0 0 3.16-.6 6.66 6.66 0 0 0 3.8-3.8 9.57 9.57 0 0 0 .6-3.16c.08-1.36.08-1.83.08-5.36s0-4-.08-5.36a9.57 9.57 0 0 0-.6-3.16 6.66 6.66 0 0 0-3.8-3.8 9.57 9.57 0 0 0-3.16-.6c-1.36-.08-1.83-.08-5.36-.08zm0 6.32a6.68 6.68 0 1 0 6.68 6.68 6.68 6.68 0 0 0-6.68-6.68zm0 11a4.33 4.33 0 1 1 4.33-4.32 4.33 4.33 0 0 1-4.33 4.33zm6.94-12.82a1.56 1.56 0 1 0 1.56 1.56 1.56 1.56 0 0 0-1.56-1.56z"></path>
            </svg>
            {{ account.instagram }}
          </a>
          <a v-if="account.twitter != null && account.twitter != ''" class="flex pr-5 items-center text-sm text-gray-600 hover:text-black fill-current" :href="twitterLink + account.twitter" target="_blank" rel="nofollow">
            <svg class="mr-1" version="1.1" viewBox="0 0 32 32" width="24" height="24" aria-hidden="false">
              <path d="M30 7.3c-1 .5-2.1.8-3.3.9 1.2-.7 2.1-1.8 2.5-3.2-1.1.7-2.3 1.1-3.6 1.4-1-1.1-2.5-1.8-4.2-1.8-3.2 0-5.7 2.6-5.7 5.7 0 .5.1.9.1 1.3-4.8-.2-9-2.5-11.8-6-.5.9-.8 1.9-.8 3 0 2 1 3.8 2.6 4.8-.9 0-1.8-.3-2.6-.7v.1c0 2.8 2 5.1 4.6 5.6-.5.1-1 .2-1.5.2-.4 0-.7 0-1.1-.1.7 2.3 2.9 3.9 5.4 4-2 1.5-4.4 2.5-7.1 2.5-.5 0-.9 0-1.4-.1 2.5 1.6 5.6 2.6 8.8 2.6 10.6 0 16.3-8.8 16.3-16.3v-.7c1.1-1 2-2 2.8-3.2z"></path>
            </svg>
            {{ account.twitter }}
          </a>
        </div>
        <div class="flex items-center pb-3">
          <div id="bio" class="pr-4 text-sm text-black">{{ account.bio }}</div>
        </div>
      </div>
    </div>
    <!-- 네비게이션 -->
    <nav class="bg-white px-8 pt-2 shadow-md">
      <div class="-mb-px flex justify-left">
        <a class="no-underline text-teal-dark border-b-2 border-transparent hover:border-black tracking-wide font-bold text-xs py-3 mr-8" style="cursor: pointer" @click="setValueCode(myMenu)">
          My Menu
        </a>
        <a class="no-underline text-grey-dark border-b-2 border-transparent hover:border-black tracking-wide font-bold text-xs py-3 mr-8" style="cursor: pointer" @click="setValueCode(like)">
          Likes
        </a>
        <a class="no-underline text-grey-dark border-b-2 border-transparent hover:border-black tracking-wide font-bold text-xs py-3 mr-8" style="cursor: pointer" @click="setValueCode(bookmark)">
          Bookmarks
        </a>
      </div>
    </nav>
    <ToffeeList v-bind:sortFlag = false :valueCode = this.valueCode></ToffeeList>
  </div>
</template>

<script>
  import { mapActions } from 'vuex'
  import axios from 'axios'
  import ToffeeList from '@/components/ToffeeList'
  import config from '../config.js'

  export default {
    name: 'profile',
    components: {
      ToffeeList
    },
    data () {
      return {
        account: [],
        instagramLink: 'https://www.instagram.com/',
        twitterLink: 'https://www.twitter.com/',
        errors: [],
        isMyProfile: true,
        valueCode: config.PostMethods.MYMENU,
        myMenu: config.PostMethods.MYMENU,
        like: config.PostMethods.LIKE,
        bookmark: config.PostMethods.BOOKMARK
      }
    },
    mounted () {
      let username = this.$route.params.username
      axios.get(`/api/accounts/` + username).then(response => {
        if (response.status === 200) {
          this.account = {
            profilePic: response.data.profilePic,
            accountId: response.data.accountId,
            instagram: response.data.instagram,
            twitter: response.data.twitter,
            bio: response.data.bio,
            src: response.data.src
          }
        }
      }).catch(e => {
        console.log(e)
      })
    },
    methods: {
      ...mapActions(['logoutProcess']),
      logout () {
        this.logoutProcess()
        this.$router.push('/')
      },
      changeProfileImg () {
        this.account.src = 'http://localhost:8098/api/images/defaultProfile.png'
      },
      setValueCode (valueCode) {
        this.valueCode = valueCode
      }
    }
  }
</script>
