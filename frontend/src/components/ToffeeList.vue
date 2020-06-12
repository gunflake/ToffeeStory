<template>
  <div>
    <!-- Sort Component -->
    <div v-if="sortFlag">
      <div class="sort-list bg-grey-light p-3 rounded font-sans w-full m-4">
        <span>|</span>
        <a v-for="sortkey in Object.keys(sortList)" :key="sortkey" v-on:click="sortOrder = sortkey" v-bind:class="{active: sortOrder === sortkey}"> {{ sortkey }} <span style="color: black;font-weight: normal;">|</span></a>
      </div>
    </div>
    <div class="flex flex-wrap lg:px-10 xl:px-20">
      <!-- Our Database Images -->
      <div class="w-full md:w-1/2 lg:w-1/3 p-3" v-for="(image,index) of orderedList" :key="index">
        <div class="w-full h-image object-cover">
          <a @click="setPostNo(image.postNo)"><img v-lazyload class="w-full h-image object-cover" :data-src="image.src" @load="masks1.push(index)" ></a>
          <div class="w-full h-image object-cover" :data-src="image.compressSrc" v-if="!masks1.includes(index)"></div>
        </div>
      </div>
      <!-- Unsplash Starbucks Images -->
      <div class="w-full md:w-1/2 lg:w-1/3 p-3" v-for="(image,index1) of images" :key="'A' + index1">
        <img class="w-full h-image object-cover" :src="image.urls.small" @load="masks.push(index1)">
        <div class="w-full h-image object-cover" :style="{'background-color':image.color}" v-if="!masks.includes(index1)"></div>
      </div>
    </div>
    <Post v-bind:postNo="posts.postNo" v-if="showModal" @close="showModal = false"></Post>
    <div class="flex flex-wrap lg:px-10 xl:px-20">
    </div>
    <scroll-loader :loader-method="getImagesInfo" :loader-enable="loadMore" loader-color="rgba(102,102,102,.5)">
    </scroll-loader>
  </div>
</template>
<script>
  import axios from 'axios'
  import Post from '@/components/Post'
  import api from '@/backend-api'
  import { mapGetters } from 'vuex'
  import _ from 'lodash'
  import config from '../config'
  import lazyload from '@/assets/js/lazyload'

  export default {
    name: 'ToffeeList',
    props: ['sortFlag', 'valueCode', 'keyword', 'accountId'],
    components: {
      Post
    },
    data: function () {
      return {
        showModal: false,
        loadMore: true,
        page: 1,
        pageSize: 9,
        images: [],
        masks: [],
        masks1: [],
        posts: [],
        sortOrder: 'NEW',
        orderByKey: 'postNo',
        links: null
      }
    },
    directives: {
      lazyload
    },
    computed: {
      ...mapGetters(['getToken', 'isLoggedIn', 'getUserName']),
      sortList: function () {
        return {
          'NEW': 'postNo',
          'BEST': 'likeCnt',
          'HOT': 'score'
        }
      },
      orderedList: function () {
        this.setSortKey(this.sortOrder)
        return _.orderBy(this.posts, this.orderByKey, 'desc')
      }
    },
    methods: {
      getPosts () {
        api.getPostList().then(response => {
          this.posts = response.data
          console.log(response.data)
        })
          .catch(e => {
            console.log(e)
          })
      },
      setSortKey (sortKey) {
        if (sortKey === 'NEW') this.orderByKey = 'postNo'
        else if (sortKey === 'BEST') this.orderByKey = 'likeCnt'
        else this.orderByKey = 'score'
      },
      searchPosts (keyword) {
        api.searchPostList(keyword)
          .then(response => {
            this.posts = response.data
          })
          .catch(e => {
            console.log(e)
          })
      },
      getInterestPost (valueCode, accountId) {
        if (this.isLoggedIn) {
          if (this.getUserName === accountId) {
            api.getInterestPosts(valueCode, this.getToken).then(response => {
              this.posts = response.data
            })
              .catch(e => {
                console.log(e)
              })
          } else {
            api.getAccountPostList(accountId).then(response => {
              this.posts = response.data
            })
              .catch(e => {
                console.log(e)
              })
          }
        } else {
          api.getAccountPostList(accountId).then(response => {
            this.posts = response.data
          })
            .catch(e => {
              console.log(e)
            })
        }
      },
      getImagesInfo () {
        axios.get('https://api.unsplash.com/search/photos/', {
          params: {
            query: 'starbucks',
            page: this.page++,
            per_page: this.pageSize,
            client_id: 'e874834b096dcd107c232fe4b0bb521158b62e486580c988b0a75cb0b77a2abe'
          }
        })
          .then(res => {
            res.data.results && res.data.results.length && (this.images = this.images.concat(res.data.results))
            res.data.length < this.pageSize && (this.loadMore = false)
          })
          .catch(error => {
            console.log(error)
          })
      },
      setPostNo (postNo) {
        this.posts.postNo = postNo
        this.showModal = true
      }
    },
    watch: {
      page (n) {
        n > 30 && (this.loadMore = false)
      },
      valueCode: function (valueCode) {
        this.getInterestPost(valueCode)
      }
    },
    mounted () {
      if (this.valueCode === config.PostMethods.ALL) {
        this.getPosts()
      } else if (this.valueCode === config.PostMethods.SEARCH) {
        this.searchPosts(this.keyword)
      } else {
        this.getInterestPost(this.valueCode, this.accountId)
      }

      this.getImagesInfo()
    }
  }
</script>
