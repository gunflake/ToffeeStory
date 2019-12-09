<template>
  <div id="app">
    <!-- FindPassword Alert -->
    <FindPwdAlert :state="alertState"></FindPwdAlert>
    <!-- state = 1 로그인, 0 = 로그아웃 -->
    <Header :state="state" userId="hmnam"></Header>
    <router-view/>
  </div>
</template>

<script>
  import Header from '@/layout/Header'
  import FindPwdAlert from '@/components/FindPwdAlert'
  export default {
    data: function () {
      return {
        alertState: 1,
        state: 0
      }
    },
    components: {
      Header,
      FindPwdAlert
    }
  }
</script>

<style src="@/assets/css/tailwind.css"></style>
