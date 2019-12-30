import Vue from 'vue'
import App from './App.vue'
import router from './router'
import VueCarousel from 'vue-carousel'
import store from './store'

Vue.use(VueCarousel)
Vue.config.productionTip = false

new Vue({
  router,
  store,
  beforeCreate () {
    this.$store.dispatch('getMemberInfo')
  },
  render: h => h(App)
}).$mount('#app')
