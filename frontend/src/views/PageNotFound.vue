<template>
  <div class="bg-grey-lighter flex flex-col">
    <div class="container w-full px-8">
      <div class="bg-white px-6 py-4 rounded text-black w-full mt-16">
        <div class="my-4 font-bold text-3xl md:text-4xl lg:text-5xl">해당 페이지를 찾지 못했습니다.</div>
        <div class="mt-6 md:text-2xl lg:text-3xl">주소가 잘못되었거나 더 이상 제공되지 않는 페이지입니다.</div>
        <div class="md:text-2xl lg:text-3xl cursor-pointer" style="color: #1dc078" @click="goHome">메인페이지로 이동 🏠🏃🏻‍♀</div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'PageNotFound',
    methods: {
      goHome () {
        this.$router.push('/')
      }
    }
  }
</script>

<style scoped>

</style>
