<template>
  <div id="toffeeList">
    <!-- Sort Component -->
    <div id="sortComponent">
      <nav class="bg-grey-light rounded font-sans w-full lg:px-10 xl:px-20 mx-3 my-4">
        <div class="font-bold text-5xl mb-4">{{ keyword }}</div>
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
  </div>
</template>

<script>
  import Post from '@/components/Post'
  import api from '@/backend-api'

  export default {
    name: 'SearchList',
    components: {
      Post
    },
    data: function () {
      return {
        flag: 0,
        keyword: '',
        showModal: false,
        images: [],
        posts: []
      }
    },
    mounted () {
      this.keyword = this.$route.query.keyword
      this.searchPosts()
    },
    methods: {
      setPostNo (postNo) {
        this.posts.postNo = postNo
        this.showModal = true
      },
      searchPosts () {
        api.searchPostList(this.flag, this.keyword)
          .then(response => {
            this.posts = response.data
          })
          .catch(e => {
            console.log(e)
          })
      },
      getPosts (flag) {
        this.flag = flag
        this.searchPosts()
      }
    }
  }
</script>

<style scoped>

</style>
