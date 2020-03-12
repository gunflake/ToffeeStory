<template>
  <transition name="modal">
    <div class="modal-mask overflow-auto">
      <div class="w-2/3 mx-auto my-8">
        <div class="bg-white p-8">
          <!-- Close Button -->
          <div class="flex justify-between mb-2">
            <div class="flex">
            </div>
            <div class="flex">
              <img src="../assets/image/close.png" class="w-5 h-5 rounded-full" style="cursor: pointer" @click="$emit('close')"/>
            </div>
          </div>
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
                <span class="flex items-center text-gray-600 font-bold text-base ml-4" @click="modifyPost"
                      style="cursor: pointer">Modify</span>
                <span class="flex items-center text-red-600 font-bold text-base ml-4" @click="deletePost"
                      style="cursor: pointer">Delete</span>
              </div>
            </div>
            <div class="flex">
              <a v-if="likeFlag == 0 || likeFlag == null" @click="modifyInterest(0,0)" class="flex items-center" style="cursor: pointer"><i class="fa fa-heart-o fa-2x"></i></a>
              <a v-else @click="modifyInterest(0,1)" class="flex items-center" style="cursor: pointer"><i class="fa fa-heart fa-2x"
                                                                                style="color:red;"></i></a>
              <span class="ml-2 mr-4 text-2xl text-center">{{ likeCnt }}</span>
              <a v-if="bookmarkFlag == 0 || bookmarkFlag == null" @click="modifyInterest(1,0)" class="flex items-center mr-6" style="cursor: pointer"><i class="fa fa-bookmark-o fa-2x"></i></a>
              <a v-else @click="modifyInterest(1,1)" class="flex items-center mr-6" style="cursor: pointer"><i
                class="fa fa-bookmark fa-2x" style="color:green;"></i></a>
            </div>
          </div>
          <!-- Modify Modal -->
          <ModifyModal v-if="showModal" :postNo="post.postNo" @close="showModal = false" @reload="postNum => getPostInfo(postNum)"></ModifyModal>
          <!-- Photo -->
          <div class="h-auto w-full mt-8">
            <img :src="post.src" class="w-full h-auto"/>
          </div>
          <!-- star -->
          <div class="flex w-full mt-4">
            <star-rating :rating="post.score" :read-only="true" :star-size="40" :show-rating="false"
                         active-color="#003d24"/>
          </div>
          <div class="flex justify-end">
            <div class="block border-b border-gray-700 w-18 text-xl my-2 px-2 text-right outline-none"> {{ post.price }} </div>
            <div class="flex items-center mr-2 border-b border-gray-700 my-2 fa fa-krw fa-lg"></div>
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
          <!-- Related Post -->
          <div class="mt-4 mb-2">
            Related Photos
          </div>
          <div class="flex flex-wrap w-full">
            <div class="w-1/3 p-2" v-for="(image,index) of relatedPost" :key="index">
              <div class="images-card">
                <a @click="getPostInfo(image.postNo)"><img class="h-64 w-full object-cover object-center" :src="image.src"></a>
              </div>
            </div>
          </div>
          <!--<scroll-loader :loader-method="getImagesInfo" :loader-enable="loadMore"
                         loader-color="rgba(102,102,102,.5)">
          </scroll-loader>-->
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
  import api from '@/backend-api'
  import ModifyModal from '@/components/UploadModal'
  import { mapActions, mapGetters } from 'vuex'

  export default {
    name: 'Post',
    props: ['postNo'],
    components: {
      'star-rating': VueStarRating,
      ModifyModal
    },
    data () {
      return {
        showModal: false,
        loadMore: true,
        page: 1,
        pageSize: 9,
        post: [],
        relatedPost: [],
        likeCnt: null,
        likeFlag: 0,
        bookmarkFlag: 0,
        accountId: '',
        accountPic: '',
        accessPossible: false,
        alert: {
          message: null,
          type: null
        }
      }
    },
    computed: {
      ...mapGetters(['isLoggedIn', 'getUserName', 'getToken'])
    },
    methods: {
      ...mapActions(['settingAlertMsg']),
      getPostInfo (postNo) {
        api.getPostInfo(postNo, this.getToken).then(response => {
          this.post = response.data.post
          this.likeCnt = response.data.post.likeCnt
          this.likeFlag = response.data.likeFlag
          this.bookmarkFlag = response.data.bookmarkFlag
          this.accountId = response.data.accountId
          this.accountPic = response.data.accountPic
          if (this.accountId === this.getUserName) this.accessPossible = true
          this.tags = response.data.tags
        })
          .catch(e => {
            console.log(e)
          })
        api.getRelatedPostList(postNo).then(response => {
          this.relatedPost = response.data
        })
          .catch(e => {
            console.log(e)
          })
      },
      modifyInterest (valueCode, useFlag) {
        if (!this.isLoggedIn) {
          this.alert.message = '로그인이 필요합니다.'
          this.alert.type = 'gray'
          this.settingAlertMsg(this.alert)
        } else {
          let formData = new FormData()
          formData.append('valueCode', valueCode)
          formData.append('useFlag', useFlag)

          if (valueCode === 0) {
            this.likeFlag = useFlag === 0 ? 1 : 0
          } else {
            this.bookmarkFlag = useFlag === 0 ? 1 : 0
          }

          api.modifyInterest(this.postNo, formData, this.getToken)
            .then(response => {
              this.likeCnt = response.data.likeCnt
            })
            .catch(e => {
              console.log(e)
            })
        }
      },
      deletePost () {
        api.deletePost(this.post.postNo, this.getToken)
          .then(response => {
            this.alert.message = '글이 삭제되었습니다.'
            this.alert.type = 'green'
            this.settingAlertMsg(this.alert)
            this.$emit('close')
          })
          .catch(() => {
            this.alert.message = '글 삭제에 실패했습니다. 본인 글만 삭제할 수 있습니다.'
            this.alert.type = 'red'
            this.settingAlertMsg(this.alert)
            this.$emit('close')
          })
      },
      modifyPost () {
        if (!this.isLoggedIn) {
          this.alert.message = '글을 등록하기 위해서는 로그인이 필요합니다.'
          this.alert.type = 'gray'
          this.settingAlertMsg(this.alert)
          this.$router.push('/login')
        } else {
          this.showModal = true
        }
      }
    },
    watch: {
      page (n) {
        n > 30 && (this.loadMore = false)
      }
    },
    mounted () {
      this.getPostInfo(this.postNo)
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
