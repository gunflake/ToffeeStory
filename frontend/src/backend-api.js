import axios from 'axios'

const AXIOS = axios.create({
  baseURL: `/api`,
  timeout: 10000
})

export default {
  joinAccount (fullName, userName, email, password) {
    return AXIOS.post(`/account/join`, {
      'accountName': fullName,
      'accountId': userName,
      'email': email,
      'accountPwd': password
    })
  },
  loginAccount (email, password) {
    return AXIOS.post(`/account/login`, {
      'email': email,
      'accountPwd': password
    })
  },
  getAccountInfo (token) {
    return AXIOS.get('/account/auth', token)
  },
  getPostList (flag) {
    return AXIOS.get('/posts/', {
      params: {
        'flag': flag
      }
    })
  },
  getPostInfo (postNo) {
    return AXIOS.get('/posts/' + postNo)
  },
  getRelatedPostList (postNo) {
    return AXIOS.get('posts/' + postNo + '/relatedPost')
  }
}
