<template>
  <div class="main">
    <h1>This is an Main page</h1>
    <ToffeeFilter class="m-3"></ToffeeFilter>
  </div>
</template>
<script>
  import ToffeeFilter from '@/components/ToffeeFilter.vue'

  export default {
    components: {
      'ToffeeFilter': ToffeeFilter
    },
    data () {
      return {
        info: null
      }
    }
  }
</script>
