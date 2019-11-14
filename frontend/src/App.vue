<template>
  <div id="app">
    <!-- state = 1 로그인, 0 = 로그아웃 -->
    <Header state="0" userId="hmnam"></Header>
    <router-view/>
  </div>
</template>

<script>
  import './assets/css/tailwind.css'
  import Header from '@/layout/Header'
  export default {
    components: {
      Header
    }
  }
</script>

<style>
</style>
