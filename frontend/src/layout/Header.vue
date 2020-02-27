<template>
  <nav class="bg-white mx-4 flex" role="navigation">
    <!-- Logo -->
    <div class="w-1/12">
        <img src="@/assets/image/toffeeStory.png" class="w-36 h-16" @click="goHome()" style="cursor: pointer"/>
    </div>
    <!-- Search -->
    <div class="w-7/12 _1g2pj _3PSbf ml-4">
      <form class="_2jvJJ mV0BM vertical-center" data-test="nav-bar-search-form-form" action="/s">
        <button title="Search Unsplash" class="_2VoZY _3d86A" type="submit" data-test="nav-bar-search-form-button">
          <svg class="_2-tlh _1azRR _1mPD6" version="1.1" viewBox="0 0 32 32" width="32" height="32"
               aria-hidden="false">
            <path
              d="M31 28.64l-7.57-7.57a12.53 12.53 0 1 0-2.36 2.36l7.57 7.57zm-17.5-6a9.17 9.17 0 1 1 6.5-2.64 9.11 9.11 0 0 1-6.5 2.67z"></path>
          </svg>
        </button>
        <div role="combobox" aria-haspopup="listbox" aria-owns="react-autowhatever-SEARCH_FORM_INPUT_nav-bar"
             aria-expanded="false" class="_2ZbDJ">
          <input type="search" autocomplete="off" aria-autocomplete="list"
                 aria-controls="react-autowhatever-SEARCH_FORM_INPUT_nav-bar" class="_3q0cO _3FYu1" name="searchKeyword"
                 placeholder="Search desired topping" required="" data-test="nav-bar-search-form-input"
                 id="SEARCH_FORM_INPUT_nav-bar" title="Search Unsplash" autocapitalize="none" spellcheck="false"
                 value="" v-model="searchTag">
          <div id="react-autowhatever-SEARCH_FORM_INPUT_nav-bar" role="listbox"></div>
        </div>
      </form>
      <!-- Auto Complete -->
      <div v-if="searchTag.length > 0 " class="relative">
        <div class="VR6_Q"></div>
        <div class="drKGC">
          <div class="fuqBx">
            <a class="yCE8d  JvDyy" href="/explore/tags/swt/">
              <span class="Ap253 py-8"># 아메리카노</span>
            </a>
            <a class="yCE8d  JvDyy" href="/explore/tags/swt/">
              <span class="Ap253 py-8"># 흑당 카페라떼</span>
            </a>
            <a class="yCE8d  JvDyy" href="/explore/tags/swt/">
              <span class="Ap253 py-8"># 밀크티</span>
            </a>
          </div>
        </div>
      </div>
    </div>
    <!-- User Information -->
    <div class="w-4/12 flex items-center justify-end">
      <!-- Upload 버튼 -->
      <button id="show-modal" @click="showUpload"
              class="bg-transparent text-gray-600 font-semibold border border-gray-600 hover:bg-white hover:border-black hover:text-black hover:border-transparent rounded py-2 px-4">
        Upload
      </button>
      <!-- Image Upload Modal-->
      <UploadModal v-if="showModal" @close="showModal = false"></UploadModal>
      <!-- Login 상태에 따라 프로필 / 로그인 버튼  -->
      <div v-if="isLoggedIn" class="flex">
        <a href="#" class="ml-4">
          <img src="@/assets/image/bell.png" class="w-10 h-10 rounded-full"/>
        </a>
        <router-link :to="'/@' + getUserName">
          <a href="#" rel="home" class="flex items-center mx-4">
            <img src="@/assets/image/alt_profile.jpg" class="w-10 h-10 rounded-full" />
            <span class="text-base pl-2">{{ getUserName }}</span>
          </a>
        </router-link>
      </div>
      <div v-else>
        <router-link to="/login">
          <button
            class="ml-4 bg-transparent text-gray-600 hover:text-black font-semibold py-2 pl-6 pr-4 border-l border-gray-700 ml-8">
            Login
          </button>
        </router-link>
        <router-link to="/join">
          <button class="ml-4 bg-green-500 hover:bg-green-600 text-white font-semibold py-2 px-4 rounded">
            Join free
          </button>
        </router-link>
      </div>
    </div>
  </nav>
</template>
<script>
  import '@/assets/css/unsplash.css'
  import '@/assets/css/searchComplete.css'
  import UploadModal from '@/components/UploadModal'
  import { mapActions, mapGetters } from 'vuex'

  export default {
    name: 'Header',
    data: function () {
      return {
        showModal: false,
        searchTag: '',
        alert: {
          message: null,
          type: null
        }
      }
    },
    props: {
      state: Number,
      userId: String
    },
    components: {
      UploadModal
    },
    computed: {
      ...mapGetters(['isLoggedIn', 'getUserName'])
    },
    methods: {
      ...mapActions(['settingAlertMsg']),
      goHome () {
        this.$router.push('/')
      },
      showUpload () {
        if (!this.isLoggedIn) {
          this.alert.message = '글을 등록하기 위해서는 로그인이 필요합니다.'
          this.alert.type = 'gray'
          this.settingAlertMsg(this.alert)
          this.$router.push('/login')
        } else {
          this.showModal = true
        }
      }
    }
  }
</script>
