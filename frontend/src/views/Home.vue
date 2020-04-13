<template>
  <div class="home">
    <!-- Main Image -->
    <div id="mainImg" class="bg-gray-400">
      <carousel v-bind:autoplay="true" v-bind:loop="true" :perPage="1" style="background-color:#ffffff;">
        <slide>
          <img src="@/assets/image/main_img1.jpg" class="object-cover h-48 w-full" style="height: 450px;">
        </slide>
        <slide>
          <img src="@/assets/image/main_img2.jpg" class="object-cover h-48 w-full" style="height: 450px;">
        </slide>
        <slide>
          <img src="@/assets/image/main_img3.jpg" class="object-cover h-48 w-full" style="height: 450px;">
        </slide>
        <slide>
          <img src="@/assets/image/main_img4.jpg" class="object-cover h-48 w-full" style="height: 450px;">
        </slide>
        <slide>
          <img src="@/assets/image/main_img5.jpg" class="object-cover h-48 w-full" style="height: 450px;">
        </slide>
      </carousel>
    </div>
    <!-- Search Toffee Component-->
    <!-- Toffee List Component-->
    <ToffeeList v-bind:sortFlag = true :valueCode = this.valueCode></ToffeeList>
  </div>
</template>
<script>
  import ToffeeList from '@/components/ToffeeList'
  import { mapMutations } from 'vuex'
  import config from '../config'

  export default {
    name: 'home',
    components: {
      ToffeeList
    },
    data () {
      return {
        valueCode: config.PostMethods.ALL
      }
    },
    mounted () {
      this.showHeader()
    },
    methods: {
      ...mapMutations(['showHeader'])
    }
  }
</script>
<style>
  .home {
    overflow: hidden;
  }
</style>
