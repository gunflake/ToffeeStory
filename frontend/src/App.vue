<template>
  <div id="app">
    <!-- FindPassword Alert -->
    <Alert/>
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
