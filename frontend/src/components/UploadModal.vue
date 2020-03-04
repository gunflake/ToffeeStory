<template>
  <transition name="modal">
    <div class="modal-mask overflow-auto">
      <div class="w-2/3 mx-auto my-8">
        <div class="bg-white p-6">
          <div class="flex justify-between mb-4">
            <div class="flex">
            </div>
            <div class="flex">
              <button class="fa fa-times fa-2x ml-2" @click="$emit('close')"></button>
            </div>
          </div>
          <!-- image drag & drop space -->
          <input type="file" id="selected" @change="onFileSelected" class="hidden">
          <label for="selected">
            <div id="imageBox" class="bg-gray-200 h-auto w-full" v-on:dragover="dragOverHandler($event)"
                 v-on:dragleave="dragLeaveHandler($event)" v-on:drop="dropHandler($event)">
              <div class="p-20">
                <div class="flex mb-6">
                  <svg class="flex-1 items-center h-16 w-16 fill-current" xmlns="http://www.w3.org/2000/svg" width="50"
                       height="43" viewBox="0 0 50 43">
                    <path
                      d="M48.4 26.5c-.9 0-1.7.7-1.7 1.7v11.6h-43.3v-11.6c0-.9-.7-1.7-1.7-1.7s-1.7.7-1.7 1.7v13.2c0 .9.7 1.7 1.7 1.7h46.7c.9 0 1.7-.7 1.7-1.7v-13.2c0-1-.7-1.7-1.7-1.7zm-24.5 6.1c.3.3.8.5 1.2.5.4 0 .9-.2 1.2-.5l10-11.6c.7-.7.7-1.7 0-2.4s-1.7-.7-2.4 0l-7.1 8.3v-25.3c0-.9-.7-1.7-1.7-1.7s-1.7.7-1.7 1.7v25.3l-7.1-8.3c-.7-.7-1.7-.7-2.4 0s-.7 1.7 0 2.4l10 11.6z"></path>
                  </svg>
                </div>
                <div class="text-xl text-center">
                  Drop your image here or click here to upload <br>
                  Maximum file size : 5MB
                </div>
              </div>
            </div>
          </label>
          <!-- toffing select space -->
<!--          <div class="bg-blue-100 h-64 w-full mt-8">-->
<!--            <div class="text-center vertical-center text-xl">-->
<!--              여기는 토핑 선택 칸입니다.(대체 예정)-->
<!--            </div>-->
<!--          </div>-->
          <div>
            <toffee-filter :filter-mode="1"></toffee-filter>
          </div>
          <div class="mt-4 flex justify-between">
            <div class="flex">
              <!-- star select space -->
              <div class="flex w-full mt-2">
                <star-rating v-bind:increment="0.5" :rating=post.score :star-size="40" :show-rating="false" active-color="#003d24" @rating-selected="setCurrentRating($event)"/>
              </div>
            </div>
            <div class="flex justify-end">
              <input class="block border-b border-gray-700 w-18 text-xl my-2 px-2 text-right outline-none" v-model="post.price" @keypress="numberValidation"/>
              <div class="flex items-center mr-2 border-b border-gray-700 my-2 fa fa-krw fa-lg"></div>
            </div>
          </div>
          <!-- comment input space  -->
          <div class="w-full mt-4">
            <label>
              <textarea v-model="post.content" class="w-full shadow-inner py-2 px-3 border-2" placeholder="내용을 입력해주세요." rows="4"/>
            </label>
          </div>
          <!-- cancel & publish button -->
          <div class="flex w-full mt-2 justify-end">
            <button @click="$emit('close')"
                    class="ml-4 bg-gray-400 hover:bg-gray-500 text-black font-semibold py-2 px-4 rounded">Cancel
            </button>
            <button @click="createModifyPost"
                    class="ml-4 bg-green-500 hover:bg-green-600 text-white font-semibold py-2 px-4 rounded">Upload
            </button>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
  import api from '@/backend-api'
  import VueStarRating from 'vue-star-rating'
  import Vue from 'vue'
  import { mapActions, mapGetters } from 'vuex'
  import ToffeeFilter from './toffeeFilter/ToffeeFilter'
  Vue.use(VueStarRating)
  export default {
    name: 'UploadModal',
    components: {
      'toffee-filter': ToffeeFilter,
      'star-rating': VueStarRating
    },
    props: {
      postNo: {
        type: Number,
        default: 0
      }
    },
    computed: {
      ...mapGetters(['getToken'])
    },
    created () {
      if (this.postNo > 0) {
        api.getPostInfo(this.postNo, this.getToken)
          .then(response => {
            this.post = response.data.post
            this.previewModifyImage(this.post.src)
            this.mode = 'modify'
          })
          .catch(error => {
            console.log(error)
          })
      }
    },
    data () {
      return {
        selectedFile: null,
        content: '',
        price: 0,
        src: null,
        mode: 'create',
        post: {
          score: 1,
          price: 0
        },
        alert: {
          message: null,
          type: null
        }
      }
    },
    methods: {
      ...mapActions(['settingAlertMsg']),
      setCurrentRating (rating) {
        this.post.score = rating
      },
      // Image Upload 기능 함수 Start
      onFileSelected (event) {
        this.selectedFile = event.target.files[0]
        this.previewImage(this.selectedFile)
      },
      previewImage (data) {
        let reader = new FileReader()
        reader.onload = (function (file) {
          return function (ev) {
            let imgForm = document.createElement('img')
            imgForm.setAttribute('id', 'selectedImage')
            imgForm.setAttribute('src', ev.target.result)
            imgForm.setAttribute('class', 'w-full h-auto')

            let box = document.getElementById('imageBox')
            box.innerHTML = ''
            box.appendChild(imgForm)
          }
        })(data)
        reader.readAsDataURL(data)
      },
      previewModifyImage (src) {
        let imgForm = document.createElement('img')
        imgForm.setAttribute('id', 'selectedImage')
        imgForm.setAttribute('src', src)
        imgForm.setAttribute('class', 'w-full h-auto')

        let box = document.getElementById('imageBox')
        box.innerHTML = ''
        box.appendChild(imgForm)
      },
      dragLeaveHandler (event) {
        event.preventDefault()
        let imageBox = document.getElementById('imageBox')
        imageBox.setAttribute('class', 'bg-gray-200 h-auto w-full')
      },
      dragOverHandler (event) {
        event.preventDefault()
        let imageBox = document.getElementById('imageBox')
        imageBox.setAttribute('class', 'bg-gray-400 h-auto w-full')
      },
      dropHandler (ev) {
        ev.preventDefault()

        if (ev.dataTransfer.items) {
          if (ev.dataTransfer.items[0].kind === 'file') {
            let file = ev.dataTransfer.items[0].getAsFile()
            this.selectedFile = file
            this.previewImage(file)
          }
        } else {
          for (let i = 0; i < ev.dataTransfer.files.length; i++) {
            let file = ev.dataTransfer.files[i]
            this.selectedFile = file
            this.previewImage(file)
          }
        }
      },
      numberValidation (event) {
        let val = event.key
        let numExp = /[^0-9]/g
        if (numExp.test(val)) {
          event.preventDefault()
        }
      },
      // Image Upload 기능 함수 End
      createModifyPost () {
        let formData = new FormData()
        formData.append('file', this.selectedFile)
        formData.append('content', this.post.content)
        formData.append('score', this.post.score)
        formData.append('price', this.post.price)

        if (this.mode === 'create') {
          api.createPost(formData, this.getToken)
            .then(response => {
              this.alert.message = '글이 등록되었습니다.'
              this.alert.type = 'green'
              this.settingAlertMsg(this.alert)
              this.$emit('close')
            })
            .catch(() => {
              this.alert.message = '글 등록에 실패했습니다. 작성한 글 내용을 확인해주세요.'
              this.alert.type = 'red'
              this.settingAlertMsg(this.alert)
              this.$emit('close')
            })
        } else if (this.mode === 'modify') {
          api.modifyPost(this.postNo, formData, this.getToken)
            .then(response => {
              this.alert.message = '글이 수정되었습니다.'
              this.alert.type = 'green'
              this.settingAlertMsg(this.alert)
              this.$emit('close')
            })
            .catch(() => {
              this.alert.message = '글 수정에 실패했습니다. 작성한 글 내용을 확인해주세요.'
              this.alert.type = 'red'
              this.settingAlertMsg(this.alert)
              this.$emit('close')
            })
        } else {
          this.alert.message = '글 수정에 실패했습니다. 다시 시도해주세요.'
          this.alert.type = 'red'
          this.settingAlertMsg(this.alert)
          this.$emit('close')
        }
      }
    }
  }

</script>

<style>
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
