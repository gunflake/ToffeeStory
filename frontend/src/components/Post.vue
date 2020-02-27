<template>
  <transition name="modal">
    <div class="modal-mask overflow-auto">
      <div class="w-2/3 mx-auto my-8">
        <div class="bg-white p-8">
          <!-- Header Id, Like, Bookmark -->
          <div class="flex justify-between">
            <div class="flex">
              <!-- Account Info -->
              <img class="h-10 w-10 rounded-full inline mr-4"
                   src="https://randomuser.me/api/portraits/women/21.jpg"
                   alt="https://randomuser.me/api/portraits/women/21.jpg">
              <span class="flex items-center text-xl">{{ accountId }}</span>
              <!-- Modify & Delete -->
              <div v-if="accessPossible" class="flex">
                <span class="flex items-center text-gray-600 font-bold text-base ml-4"
                      style="cursor: pointer">Modify</span>
                <span class="flex items-center text-red-600 font-bold text-base ml-4"
                      style="cursor: pointer">Delete</span>
              </div>
            </div>
            <div class="flex">
              <a class="flex items-center" style="cursor: pointer"><i class="fa fa-heart-o fa-2x"></i></a>
              <span class="ml-2 mr-4 text-2xl text-center">{{ post.likeCnt }}</span>
              <a class="flex items-center" style="cursor: pointer"><i class="fa fa-bookmark-o fa-2x"></i></a>
              <span class="ml-2 mr-2 text-2xl text-center">{{ post.likeCnt }}</span>
              <button class="fa fa-times fa-2x ml-2" @click="$emit('close')"></button>
            </div>
          </div>
          <!-- Photo -->
          <div class="h-auto w-full mt-8">
            <img :src="src" class="w-full h-auto"/>
          </div>
          <!-- star -->
          <div class="flex w-full mt-4">
            <star-rating :rating="post.score" :read-only="true" :star-size="40" :show-rating="false"
                         active-color="#003d24"/>
          </div>
          <!-- Content -->
          <div class="mt-4 text-xl">
            <p>{{ post.content }}</p>
          </div>
          <!-- Related Tags -->
          <div class="mt-4">
            Related Tags
          </div>
          <div class="mt-2 flex flex-wrap">
            <div class="mr-3" v-for="(tag,index) of post.tags" :key="index">
              <button class="rounded px-2 py-1" style="background: #cdd0d4">{{ tag }}</button>
            </div>
          </div>
          <!-- Relatet Post -->
          <div class="mt-4 mb-2">
            Related Photos
          </div>
          <div class="flex flex-wrap w-full" style="">
            <div class="w-1/3 p-2" v-for="(image,index) of images" :key="index">
              <img class="h-64 w-full object-cover object-center" :src="image.urls.small">
            </div>
          </div>
          <scroll-loader :loader-method="getImagesInfo" :loader-enable="loadMore"
                         loader-color="rgba(102,102,102,.5)">
          </scroll-loader>
          <div class="modal-footer">
            <slot name="footer">
            </slot>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
  import VueStarRating from 'vue-star-rating'
  import axios from 'axios'
  import api from '@/backend-api'
  import { mapGetters } from 'vuex'

  export default {
    name: 'Post',
    props: ['postNo'],
    components: {
      'star-rating': VueStarRating
    },
    computed: {
      ...mapGetters(['getUserName'])
    },
    data () {
      return {
        showModal: false,
        loadMore: true,
        page: 1,
        pageSize: 9,
        images: [],
        post: [],
        accountId: '',
        accountPic: '',
        src: '',
        accessPossible: false
      }
    },
    methods: {
      getPostInfo (postNo) {
        api.getPostInfo(postNo).then(response => {
          this.post = response.data.post
          this.accountId = response.data.accountId
          this.accountPic = response.data.accountPic
          this.src = 'http://localhost:8098/api/images/' + response.data.post.postPic
          if (this.accountId === this.getUserName) this.accessPossible = true
        })
          .catch(e => {
            console.log(e)
          })
      },
      getRelatedPostList (postNo) {

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
      }
    },
    watch: {
      page (n) {
        n > 30 && (this.loadMore = false)
      }
    },
    mounted () {
      this.getPostInfo(this.postNo)
      this.getImagesInfo()
    }
  }
</script>

<style scoped>
  @import '../assets/css/image-scroll.css';

  .modal-mask {
    position: fixed;
    z-index: 9998;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, .5);
  }
</style>
