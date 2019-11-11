<template>
  <div class="about">
    <h1>This is an about page</h1>
    <h2>{{info}}</h2>
  </div>
</template>

<script>

  import axios from 'axios'

  export default {
    data () {
      return {
        info: null
      }
    },
    mounted () {
      axios
        .get('api/hello')
        .then(response => (this.info = response.data))
    }
  }

</script>
