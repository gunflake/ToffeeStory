<template>
  <div id="toffeeList">
    <!-- Sort Component -->
    <div id="sortComponent">
      <nav class="bg-grey-light rounded font-sans w-full lg:px-10 xl:px-20 mx-3 my-4">
        <div class="font-bold text-5xl mb-4">{{ keyword }}</div>
      </nav>
    </div>
    <ToffeeList v-bind:sortFlag = true :valueCode = this.valueCode :keyword = this.keyword></ToffeeList>
  </div>
</template>

<script>
  import ToffeeList from '../components/ToffeeList'
  import config from '../config.js'

  export default {
    name: 'SearchList',
    components: {
      ToffeeList
    },
    data: function () {
      return {
        flag: 0,
        keyword: this.$route.query.keyword,
        valueCode: config.PostMethods.SEARCH
      }
    }
  }
</script>

<style scoped>

</style>
