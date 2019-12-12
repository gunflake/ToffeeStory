<template>
  <div id="app">
    <!-- state = 1 로그인, 0 = 로그아웃 -->
    <Header :state="state" userId="hmnam"></Header>
    <router-view/>
  </div>
</template>

<script>
  import Header from '@/layout/Header'
  export default {
    data: function () {
      return {
        state: 0
      }
    },
    components: {
      Header
    }
  }
</script>

<style src="@/assets/css/tailwind.css"></style>
