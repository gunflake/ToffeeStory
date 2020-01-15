<template>
  <transition name="modal">
    <div class="modal-mask">
      <div class="modal-container w-full mx-auto">
        <div class="flex">
          <div class="w-1/2 mx-auto mt-8">
            <div class="bg-white p-6">
              <div class="modal-body">
                <div class="flex justify-end">
                  <button class="" @click="$emit('close')">X</button>
                </div>
                <!-- image drag & drop space -->
                <div class="bg-gray-200 h-64 w-full">
                  <div class="text-center vertical-center text-xl">
                    Drop your image here or click here to upload <br>
                    Maximum file size : 5MB
                    <input type="file" @change="onFileSelected">
                    <Button @click="uploadFile">upload</Button>
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
                  <star-rating :rating="1" :star-size="40" :show-rating="false" active-color="#003d24"/>
                </div>
                <!-- comment input space  -->
                <div class="w-full mt-2">
                  <label>
                    <textarea class="w-full shadow-inner py-2 px-3 border-2" placeholder="내용을 입력해주세요." rows="4"/>
                  </label>
                </div>
                <!-- cancel & publish button -->
                <div class="flex w-full mt-2 justify-end">
                  <button @click="$emit('close')" class="ml-4 bg-gray-400 hover:bg-gray-500 text-black font-semibold py-2 px-4 rounded">Cancel</button>
                  <button @click="$emit('close')" class="ml-4 bg-green-500 hover:bg-green-600 text-white font-semibold py-2 px-4 rounded">Upload</button>
                </div>
              </div>
            </div>
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
        selectedFile: null
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
  .modal-container {
    position: center;
    width: 1000px;
  }
  .modal-body {
    height: 100%;
    overflow-y: auto;
  }
</style>
