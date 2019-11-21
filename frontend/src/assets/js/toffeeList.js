import Vue from 'vue'
import ScrollLoader from 'vue-scroll-loader'
import axios from 'axios'

Vue.use(ScrollLoader)
console.log('test')
export default {
  name: 'ToffeeList',
  data () {
    return {
      loadMore: true,
      page: 1,
      pageSize: 9,
      images: []
    }
  },
  methods: {
    getImagesInfo () {
      axios.get('https://api.unsplash.com/photos?page=7&per_page=9&client_id=e874834b096dcd107c232fe4b0bb521158b62e486580c988b0a75cb0b77a2abe')
        .then(res => {
          this.images.concat(res.data)

          // Stop scroll-loader
          res.data.length < this.pageSize && (this.loadMore = false)
        })
        .catch(error => {
          console.log(error)
        })
    }
  },
  mounted () {
    this.getImagesInfo()
  }
}
