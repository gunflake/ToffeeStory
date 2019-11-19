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
<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
