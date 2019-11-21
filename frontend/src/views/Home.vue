<template>
  <div class="home">
    <div id="mainImg" class="bg-gray-400">
      <carousel>
        <img src="@/assets/image/main_img1.jpg" class="object-cover h-48 w-full" style="height: 400px;">
      </carousel>
    </div>
    <!-- search component -->
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
    <!-- Toffee List -->
    <div id="toffeeList">
      <ToffeeList></ToffeeList>
    </div>
  </div>
</template>
<script>
  import ToffeeList from '@/components/ToffeeList'
  export default {
    name: 'home',
    components: { ToffeeList }
  }
</script>
