<template>
  <transition name="modal">
    <div class="modal-mask overflow-auto">
      <div class="w-1/2 mx-auto my-8">
        <div class="bg-white p-6">
          <div class="flex justify-end">
            <button class="" @click="$emit('close')">X</button>
          </div>
          <!-- image drag & drop space -->
          <div id="imageBox" class="bg-gray-200 h-auto w-full" v-on:dragover="dragOverHandler($event)" v-on:drop="dropHandler($event)">
            <div class="text-center vertical-center text-xl">
              Drop your image here or click here to upload <br>
              Maximum file size : 5MB
            </div>
          </div>
          <!-- toffing select space -->
          <div class="bg-blue-100 h-64 w-full mt-8">
            <div class="text-center vertical-center text-xl">
              여기는 토핑 선택 칸입니다.(대체 예정)
            </div>
          </div>
          <!-- star select space -->
          <div class="flex w-full mt-2">
            <star-rating v-bind:increment="0.5" :rating="1" :star-size="40" :show-rating="false" active-color="#003d24" @rating-selected="setCurrentRating($event)"/>
          </div>
          <!-- comment input space  -->
          <div class="w-full mt-2">
            <label>
              <textarea v-model="content" class="w-full shadow-inner py-2 px-3 border-2" placeholder="내용을 입력해주세요." rows="4"/>
            </label>
          </div>
          <!-- cancel & publish button -->
          <div class="flex w-full mt-2 justify-end">
            <button @click="$emit('close')"
                    class="ml-4 bg-gray-400 hover:bg-gray-500 text-black font-semibold py-2 px-4 rounded">Cancel
            </button>
            <button @click="createPost"
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
  Vue.use(VueStarRating)
  export default {
    name: 'UploadModal',
    components: {
      'star-rating': VueStarRating
    },
    data () {
      return {
        selectedFile: null,
        rating: 1,
        content: ''
      }
    },
    methods: {
      onFileSelected (event) {
        this.selectedFile = event.target.files[0]
      },
      uploadFile () {
        let token = {
          headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token'),
            'Content-Type': 'multipart/form-data'
          }
        }
        let formData = new FormData()
        formData.append('file', this.selectedFile)
        // console.log(fd)
        api.uploadImage(formData, token)
      },
      setCurrentRating (rating) {
        this.rating = rating
      },
      // Image Upload 기능 함수 Start
      previewImage (data) {
        let reader = new FileReader()
        reader.onload = (function (file) {
          return function (ev) {
            console.log('ev: ')
            console.dir(ev)

            let imgForm = document.createElement('img')
            imgForm.setAttribute('id', 'selectedImage')
            imgForm.setAttribute('src', ev.target.result)
            imgForm.setAttribute('class', 'w-full h-auto')

            let box = document.getElementById('imageBox')
            box.appendChild(imgForm)
          }
        })(data)
        // console.log('dataURL: ' + reader.readAsDataURL(data));
        reader.readAsDataURL(data)
      },
      dragOverHandler (event) {
        event.preventDefault()
      },
      dropHandler (ev) {
        ev.preventDefault()
        console.log('File(s) dropped')

        document.getElementById('imageBox').innerHTML = ''

        if (ev.dataTransfer.items) {
          if (ev.dataTransfer.items[0].kind === 'file') {
            let file = ev.dataTransfer.items[0].getAsFile()
            this.previewImage(file)
          }
        } else {
          for (let i = 0; i < ev.dataTransfer.files.length; i++) {
            console.log(ev.dataTransfer.files[i])
          }
        }
      },
      // Image Upload 기능 함수 End
      createPost () {
        let token = {
          headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token'),
            'Content-Type': 'multipart/form-data'
          }
        }
        let formData = new FormData()
        formData.append('file', this.selectedFile)
        formData.append('content', this.content)
        formData.append('score', this.rating)
        console.log(formData)
        api.createPost(formData, token)
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
