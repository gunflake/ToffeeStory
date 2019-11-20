<template>
  <transition name="modal">
    <div class="modal-mask">
      <div class="container w-full h-full mx-auto">
        <div class="flex">
          <div class="w-1/2 mx-auto mt-8">
            <div class="bg-white p-8">
              <!-- image drag & drop space -->
              <div class="bg-gray-200 h-64 w-full">
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
                <span class="star star_left" @mouseover="point = 0.5" v-bind:class="{ on: point >= 0.5}"></span>
                <span class="star star_right" @mouseover="point = 1" v-bind:class="{ on: point >= 1 }"></span>

                <span class="star star_left" @mouseover="point = 1.5" v-bind:class="{ on: point >= 1.5}"></span>
                <span class="star star_right" @mouseover="point = 2" v-bind:class="{ on: point >= 2 }"></span>

                <span class="star star_left" @mouseover="point = 2.5" v-bind:class="{ on: point >= 2.5}"></span>
                <span class="star star_right" @mouseover="point = 3" v-bind:class="{ on: point >= 3 }"></span>

                <span class="star star_left" @mouseover="point = 3.5" v-bind:class="{ on: point >= 3.5}"></span>
                <span class="star star_right" @mouseover="point = 4" v-bind:class="{ on: point >= 4 }"></span>

                <span class="star star_left" @mouseover="point = 4.5" v-bind:class="{ on: point >= 4.5}"></span>
                <span class="star star_right" @mouseover="point = 5" v-bind:class="{ on: point >= 5 }"></span>
              </div>
              <!-- comment input space  -->
              <div class="w-full mt-2">
                <label>
                  <textarea class="w-full shadow-inner py-2 px-3 border-2" placeholder="내용을 입력해주세요." rows="4"></textarea>
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
  </transition>
</template>

<script>
  export default {
    data: function () {
      return {
        point: 0.5
      }
    },
    methods: {
      mouseOver: function () {
        this.active = !this.active
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
    display: table;
  }

  .star {
    display: inline-block;
    width: 30px;
    height: 60px;
    cursor: pointer;
  }

  .star_left {
    background: url(http://gahyun.wooga.kr/main/img/testImg/star.png) no-repeat 0 0;
    background-size: 60px;
  }

  .star_right {
    background: url(http://gahyun.wooga.kr/main/img/testImg/star.png) no-repeat -30px 0;
    background-size: 60px;
  }

  .star.on {
    background-image: url(http://gahyun.wooga.kr/main/img/testImg/star_on.png);
  }
</style>
