<template>
  <div id="app">
    <!-- FindPassword Alert -->
    <Alert/>
    <Header v-show="this.getHeaderVisible"/>
    <router-view/>
  </div>
</template>

<script>
  import Header from '@/layout/Header'
  import Alert from '@/components/Alert'
  import { mapGetters } from 'vuex'
  export default {
    components: {
      Header,
      Alert
    },
    computed: {
      ...mapGetters(['getHeaderVisible'])
    }
  }
</script>

<style src="@/assets/css/tailwind.css"></style>
