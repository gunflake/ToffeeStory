import axios from 'axios'
import Post from '@/components/Post'
import api from '@/backend-api'

export default {
  name: 'ToffeeList',
  components: {
    Post
  },
  data: function () {
    return {
      showModal: false,
      loadMore: true,
      page: 1,
      pageSize: 9,
      images: [],
      posts: []
    }
  },
  methods: {
    getPosts () {
      api.getPostList().then(response => {
        console.log(response.data)
        this.posts = response.data
      })
        .catch(e => {
          console.log(e)
        })
    },
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
  computed: {
    postNo: function () {
      return this.posts.postNo
    }
  },
  watch: {
    page (n) {
      n > 30 && (this.loadMore = false)
    }
  },
  mounted () {
    this.getPosts()
    this.getImagesInfo()
  }
}
