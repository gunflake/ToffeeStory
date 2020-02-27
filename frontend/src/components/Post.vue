<template>
  <transition name="modal" class="modal items-center justify-center">
    <div class="modal-mask">
      <div class="modal-wrapper">
        <div class="modal-container w-full mx-auto">
          <div>
            <div class="bg-white p-6" style="margin-top:5%;">
              <div class="modal-header">
                <slot name="header">
                  <div class="inline">
                    <div class="inline">
                      <!-- Account Info -->
                      <img class="h-10 w-10 rounded-full mr-10 inline" style="margin:10px;" src="https://randomuser.me/api/portraits/women/21.jpg">
                      <span style="font-size:110%;">{{ accountId }}</span>
                    </div>
                    <div class="inline" style="margin-left:65%;">
                      <a style="cursor: pointer"><i class="fa fa-heart-o fa-2x"></i></a>
                      <span style="margin-left:1%;margin-right: 2%;">{{ post.likeCnt }}</span>
                      <a style="cursor: pointer; margin-left:3%;"><i class="fa fa-bookmark-o fa-2x"></i></a>
                    </div>
                  </div>
                  <button class="modal-default-button" style="float:right;color:gray;" @click="$emit('close')">X</button>
                </slot>
              </div>
              <br>
              <div class="modal-body">
                <slot name="body">
                  <!-- photo -->
                  <div class="thumbnail-wrapper">
                    <div class="thumbnail">
                      <img :src="src" class="w-full h-auto"/>
                    </div>
                  </div>
                  <!-- star -->
                  <div class="content-padding">
                    <star-rating :rating="post.score" :read-only="true" :star-size="40" :show-rating="false" active-color="#003d24"></star-rating>
                  </div>
                  <!-- content -->
                  <div class="content-padding">
                    <p>{{ post.content }}</p>
                  </div>
                  <!-- related post -->
                  <div class="content-padding">
                    Related Photos
                  </div>
                  <div class="images-container" style="width:80%;">
                    <div class="images-item" v-for="(image,index) of images" :key="index">
                      <div class="images-card">
                        <img class="images-card__image" :src="image.urls.small">
                      </div>
                    </div>
                  </div>
                  <scroll-loader :loader-method="getImagesInfo" :loader-enable="loadMore" loader-color="rgba(102,102,102,.5)">
                  </scroll-loader>
                  <!-- related tag -->
                  <div class="content-padding">
                    Related Tags
                  </div>
                  <div class="content-padding" v-for="(tag,index) of post.tags" :key="index" style="padding-top: 0%; padding-bottom: 5%;">
                    <button class="tag-padding rounded px-2 py-1">{{ tag }}</button>
                  </div>
                </slot>
              </div>
              <div class="modal-footer">
                <slot name="footer">
                </slot>
              </div>
            </div>
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

  export default {
    name: 'Post',
    props: ['postNo'],
    components: {
      'star-rating': VueStarRating
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
        tags: []
      }
    },
    methods: {
      getPostInfo (postNo) {
        api.getPostInfo(postNo).then(response => {
          this.post = response.data.post
          this.accountId = response.data.accountId
          this.accountPic = response.data.accountPic
          this.tags = response.data.tags
          this.src = 'http://localhost:8098/api/images/' + response.data.post.postPic
          console.log(response)
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
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, .5);
    display: block !important;
    overflow-y: initial !important;
  }
  .modal-container {
    position: center;
    width: 1000px;
  }
  .modal-body {
    height: 600px;
    overflow-y: auto;
  }
  .thumbnail-wrapper {
    width: 85%;
  }
  .thumbnail {
    position: relative;
    padding-left: 15%;
    overflow: hidden;
  }
  .content-padding {
    padding-top: 3%;
    padding-left: 8%;
    padding-right: 8%;
  }
  .tag-padding {
    margin-right: 1%;
    margin-top: 1%;
    background: #cdd0d4;
  }
</style>
