import Vue from 'vue'
import ScrollLoader from 'vue-scroll-loader'
import axios from 'axios'

Vue.use(ScrollLoader)
console.log('test')
export var toffeeList = {
  name: 'ToffeeList',
  data () {
    return {
      loadMore: true,
      page: 1,
      pageSize: 9,
      images: [],
      masksHide: []
    }
  },
  methods: {
    getImagesInfo () {
      axios.get('https://api.unsplash.com/photos', {
        params: {
          page: this.page++,
          per_page: this.pageSize,
          client_id: 'e874834b096dcd107c232fe4b0bb521158b62e486580c988b0a75cb0b77a2abe'
        }
      })
        .then(res => {
          res.data && res.data.length && (this.images = this.images.concat(res.data))
        })
        .catch(error => {
          console.log(error)
        })
    }
  },
  watch: {
    page (n) {
      n > 30 && (this.loadMore = false)
    }
  },
  mounted () {
    this.getImagesInfo()
  }
}
