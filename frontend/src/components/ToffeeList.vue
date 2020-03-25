<template>
  <div id="toffeeList">
    <!-- Sort Component -->
    <div id="sortComponent">
      <nav class="bg-grey-light rounded font-sans w-full lg:px-10 xl:px-20 mx-3 my-4">
        <ol class="list-reset flex text-grey-dark">
          <li><a href="#" class="text-blue font-bold" @click="getPosts(0)">NEW</a></li>
          <li><span class="mx-2">|</span></li>
          <li><a href="#" class="text-blue font-bold" @click="getPosts(1)">BEST</a></li>
          <li><span class="mx-2">|</span></li>
          <li><a href="#" class="text-blue font-bold" @click="getPosts(2)">HOT</a></li>
        </ol>
      </nav>
    </div>
    <div class="flex flex-wrap lg:px-10 xl:px-20">
      <div class="w-full md:w-1/2 lg:w-1/3 p-3" v-for="(image,index) of posts" :key="index">
        <div class="w-full h-image object-cover">
          <a @click="setPostNo(image.postNo)"><img class="w-full h-image object-cover" :src="image.src"></a>
        </div>
      </div>
    </div>
    <Post v-bind:postNo="posts.postNo" v-if="showModal" @close="showModal = false"></Post>
    <!--<scroll-loader :loader-method="getPosts" :loader-enable="loadMore" loader-color="rgba(102,102,102,.5)">
    </scroll-loader>-->
    <div class="flex flex-wrap lg:px-10 xl:px-20">
      <div class="w-full md:w-1/2 lg:w-1/3 p-3" v-for="(image,index) of images" :key="index">
          <img class="w-full h-image object-cover" :src="image.urls.small">
      </div>
    </div>
    <scroll-loader :loader-method="getImagesInfo" :loader-enable="loadMore" loader-color="rgba(102,102,102,.5)">
    </scroll-loader>
  </div>
</template>
<script>
  import axios from 'axios'
  import Post from '@/components/Post'
  import api from '@/backend-api'

  export default {
    name: 'ToffeeList',
    components: {
      Post
    },
    data: function () {
      return {
        flag: 0,
        showModal: false,
        loadMore: true,
        page: 1,
        pageSize: 9,
        images: [],
        posts: []
      }
    },
    methods: {
      getPosts (flag) {
        this.flag = flag
        api.getPostList(this.flag).then(response => {
          this.posts = response.data
        })
          .catch(e => {
            console.log(e)
          })
      },
      getImagesInfo () {
        axios.get('https://api.unsplash.com/photos', {
          params: {
            page: this.page++,
            per_page: this.pageSize,
            client_id: 'e874834b096dcd107c232fe4b0bb521158b62e486580c988b0a75cb0b77a2abe'
          }
        })
          .then(res => {
            res.data && res.data.length && (this.images = this.images.concat(res.data))
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
    computed: {
      postNo: function () {
        return this.posts.postNo
      }
    },
    watch: {
      page (n) {
        n > 30 && (this.loadMore = false)
      }
    },
    mounted () {
      this.getPosts(this.flag)
      this.getImagesInfo()
    }
  }
</script>
