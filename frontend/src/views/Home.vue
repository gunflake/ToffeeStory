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
    <!-- Search Toffee Tomponent-->
    <!-- Sort Component -->
    <div id="">
      <nav class="bg-grey-light p-3 rounded font-sans w-full m-4">
        <ol class="list-reset flex text-grey-dark">
          <li><a href="#" class="text-blue font-bold">NEW</a></li>
          <li><span class="mx-2">|</span></li>
          <li><a href="#" class="text-blue font-bold">BEST</a></li>
          <li><span class="mx-2">|</span></li>
          <li><a href="#" class="text-blue font-bold">HOT</a></li>
        </ol>
      </nav>
    </div>
    <!-- Toffee List Component-->
    <ToffeeList></ToffeeList>
  </div>
</template>
<script>
  import ToffeeList from '@/components/ToffeeList'
  export default {
    name: 'home',
    components: {
      ToffeeList
    }
  }
</script>
