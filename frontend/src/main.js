import Vue from 'vue'
import App from './App.vue'
import router from './router'
import VueCarousel from 'vue-carousel'
Vue.use(VueCarousel)
Vue.config.productionTip = false

new Vue({
  components: {
    'carousel': VueCarousel.Carousel,
    'slide': VueCarousel.Slide
  },
  router,
  render: h => h(App)
}).$mount('#app')
