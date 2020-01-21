<template>
  <div id="app">
    <!-- FindPassword Alert -->
    <Alert/>
    <!-- state = 1 로그인, 0 = 로그아웃 -->
    <Header/>
    <router-view/>
  </div>
</template>

<script>
  import Header from '@/layout/Header'
  import Alert from '@/components/Alert'
  export default {
    components: {
      Header,
      Alert
    }
  }
</script>

<style src="@/assets/css/tailwind.css"></style>
