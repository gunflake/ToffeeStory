<template>
  <div class="about">
    <h1>This is an Login page</h1>
    <h2>{{info}}</h2>
    <p class="bg-blue-200 font-extrabold">
      hello
    </p>
  </div>
</template>

<script>
  import '../assets/css/tailwind.css'
  // import axios from 'axios'

  export default {
    data () {
      return {
        info: null
      }
    }

    // ,
    // mounted () {
    //   axios
    //     .get('api/hello')
    //     .then(response => (this.info = response.data))
    // }
  }

</script>
