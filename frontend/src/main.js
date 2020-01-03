import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ScrollLoader from 'vue-scroll-loader'
import VueCarousel from 'vue-carousel'
Vue.use(VueCarousel)
Vue.use(ScrollLoader)
Vue.config.productionTip = false

new Vue({
  components: {
    'carousel': VueCarousel.Carousel,
    'slide': VueCarousel.Slide,
    'scroll-loader': ScrollLoader
  },
  router,
  render: h => h(App)
}).$mount('#app')
