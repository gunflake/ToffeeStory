<template>
  <nav class="bg-white flex p-2 items-center" role="navigation">
    <!-- Mobile -->
    <div class="flex w-full md:hidden py-2">
      <div class="w-1/4 ">
        <div class="flex justify-center fa fa-home fa-2x" style="cursor: pointer" @click="goHomePage()"></div>
      </div>
      <div class="w-1/4 ">
        <div class="flex justify-center fa fa-search fa-2x" style="cursor: pointer" @click="goMobileSearchPage()"></div>
      </div>
      <div class="w-1/4 ">
        <div class="flex justify-center fa fa-plus-square fa-2x" style="cursor: pointer" @click="showUpload"></div>
      </div>
      <div class="w-1/4 ">
        <div class="flex justify-center fa fa-user-circle fa-2x" style="cursor: pointer" @click="goProfilePage"></div>
      </div>
    </div>
    <!-- Home & Search -->
    <div class="hidden md:flex flex-grow">
      <!-- Logo -->
      <img src="../assets/image/logo.png" class="h-12 w-12" @click="goHomePage()" style="cursor: pointer"/>
      <div class="hidden lg:block ml-6" style="margin-left: 1%;">
        <div class="font-sans pt-1"
             style="white-space: nowrap; font-family: 'Roboto Slab', serif; font-size: larger; color:#1f845b;">TOFFEE
          STORY
        </div>
        <div class="text-sm font-sans" style="white-space: nowrap;font-family: 'Special Elite', cursive; color: gray;">
          Toppings for everyone
        </div>
      </div>
      <!-- Search -->
      <div class="w-full items-center ml-3 pt-1">
        <div class="relative">
          <span>
            <input
              class="transition-colors duration-100 ease-in-out focus:outline-none border border-transparent focus:bg-white focus:border-gray-900 placeholder-gray-600 rounded-lg bg-search py-2 pr-4 pl-10 block w-full appearance-none leading-normal ds-input"
              type="text" placeholder="Search desired topping" autocomplete="off"
              spellcheck="false" role="combobox" aria-autocomplete="list" aria-expanded="false"
              aria-label="search input" dir="auto"
              v-on:keyup.enter="search"
              v-on:keyup="setAutocompleteList"
              v-model="searchTag"
              v-on:focus="autoCompleteView = true"
              v-on:blur="hideAutoComplete">
          </span>
          <div class="pointer-events-none absolute inset-y-0 left-0 pl-4 flex items-center">
            <svg class="fill-current pointer-events-none text-gray-600 w-4 h-4" xmlns="http://www.w3.org/2000/svg"
                 viewBox="0 0 20 20">
              <path
                d="M12.9 14.32a8 8 0 1 1 1.41-1.41l5.35 5.33-1.42 1.42-5.33-5.34zM8 14A6 6 0 1 0 8 2a6 6 0 0 0 0 12z"></path>
            </svg>
          </div>
        </div>
        <!-- Auto Complete -->
        <div v-if="searchTag.length > 0 && autoCompleteView" class="ml-8">
          <div class="bg-white mt-2 rotateSquare"></div>
          <div class="mt-4 fontDoHyeon text-xl" style="z-index: 1; position:absolute;">
            <div class="border-black min-w-64 max-h-64 overflow-scroll bg-white completeBox">
              <div v-for="beverage in matchingList.beverage" :key="beverage">
                <a class="flex p-2 border-b-2 border-gray-300" v-bind:href="'/search?keyword='+beverage">
                  <img src="../assets/image/beverage.png" class="w-10 h-10" alt=""/>
                  <div class="ml-2 my-auto">{{beverage}}</div>
                </a>
              </div>
              <div v-for="topping in matchingList.topping" :key="topping">
                <a class="flex p-2 border-b-2 border-gray-300" v-bind:href="'/search?keyword='+topping">
                  <img src="../assets/image/topping.png" class="w-10 h-10" alt=""/>
                  <div class="ml-2 my-auto">{{topping}}</div>
                </a>
              </div>
              <div v-if="matchingList.beverage.length == 0 && matchingList.topping.length == 0"
                   class="p-2 text-2xl text-center">"{{searchTag}}" 에 관한 정보가 없습니다.
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- User Information -->
    <div class="hidden md:flex ml-4 items-center justify-end">
      <!-- Upload 버튼 -->
      <button id="show-modal" @click="showUpload"
              class="bg-transparent text-gray-600 font-semibold border border-gray-600 hover:bg-white hover:border-black hover:text-black hover:border-transparent rounded py-2 px-4">
        Upload
      </button>
      <!-- Image Upload Modal-->
      <!-- Login 상태에 따라 프로필 / 로그인 버튼  -->
      <div v-if="isLoggedIn" class="flex">
        <!-- 알람 메시지 -->
        <AlarmMessage/>
        <router-link :to="'/@' + getUserName">
          <a href="#" rel="home" class="flex items-center mx-4">
            <img :src="getUserSrc" alt="http://localhost:8098/api/images/defaultProfile.png"
                 class="w-10 h-10 rounded-full"/>
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
    <UploadModal v-if="showModal" @close="showModal = false"></UploadModal>
  </nav>
</template>
<script>
  import '@/assets/css/unsplash.css'
  import '@/assets/css/searchComplete.css'
  import api from '@/backend-api'
  import UploadModal from '@/components/UploadModal'
  import AlarmMessage from '@/components/AlarmMessage'
  import { mapActions, mapGetters, mapMutations } from 'vuex'

  export default {
    name: 'Header',
    data: function () {
      return {
        autoCompleteView: false,
        matchingList: {
          beverage: [],
          topping: []
        },
        showModal: false,
        searchTag: '',
        alert: {
          message: null,
          type: null
        },
        alarmVisible: false
      }
    },
    props: {
      state: Number,
      userId: String
    },
    components: {
      AlarmMessage,
      UploadModal
    },
    computed: {
      ...mapGetters(['isLoggedIn', 'getUserName', 'getUserSrc', 'getAutocompleteList', 'getAlarmMessageList', 'getToken'])
    },
    methods: {
      ...mapActions(['settingAlertMsg']),
      ...mapMutations(['setAlarmMessageList']),
      goHomePage () {
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
      },
      goProfilePage (accountId) {
        if (!this.isLoggedIn) {
          this.alert.message = '프로필을 보기 위해서는 로그인이 필요합니다.'
          this.alert.type = 'gray'
          this.settingAlertMsg(this.alert)
          this.$router.push('/login')
        } else {
          if (accountId == null) {
            this.$router.push('/@' + this.getUserName)
          } else {
            this.$router.push('/@' + accountId)
          }
        }
      },
      goMobileSearchPage () {
        this.$router.push('/mobile-search')
      },
      search () {
        location.href = '/search?keyword=' + this.searchTag
      },
      setAutocompleteList () {
        let list = {
          beverage: [],
          topping: []
        }
        let product = this.getAutocompleteList
        let keyword = this.searchTag

        if (keyword.length < 1) {
          return
        }

        product.beverage.forEach(function (item) {
          if (item.includes(keyword)) {
            list.beverage.push(item)
          }
        })

        product.topping.forEach(function (item) {
          if (item.includes(keyword)) {
            list.topping.push(item)
          }
        })

        this.matchingList = list
      },
      hideAutoComplete () {
        const sleep = (milliseconds) => {
          return new Promise(resolve => setTimeout(resolve, milliseconds))
        }
        sleep(100).then(() => {
          this.autoCompleteView = false
        })
      },
      showAndHideAlarmList () {
        this.alarmVisible = !this.alarmVisible
      },
      readAlarmMessage (alarmNo) {
        console.log(alarmNo)
        api.readAlarmMessage(alarmNo, this.getToken)
          .then((response) => {
            console.log(response)
            this.setAlarmMessageList(response.data)
          })
          .catch(() => {
            // 알람 지우는거 실패 처리 하기
          })
      }
    }
  }
</script>
<style>
  @import url('https://fonts.googleapis.com/css2?family=Do+Hyeon&display=swap');

  .rotateSquare {
    margin-left: 6.5rem;
    position: absolute;
    transform: rotate(45deg);
    width: 16px;
    z-index: 2;
    height: 16px;
    background-color: white;
    border-top: solid 1px;
    border-left: solid 1px;
  }

  .completeBox {
    border: solid 1px;
    box-shadow: 0 0 5px;
  }

  .fontDoHyeon {
    font-family: 'Do Hyeon', sans-serif;
  }
</style>
