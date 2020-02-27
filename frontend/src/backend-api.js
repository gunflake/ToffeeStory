import axios from 'axios'

const AXIOS = axios.create({
  baseURL: `/api`,
  timeout: 10000
})

export default {
  joinAccount (fullName, userName, email, password) {
    return AXIOS.post(`/accounts`, {
      'accountName': fullName,
      'accountId': userName,
      'email': email,
      'accountPwd': password
    })
  },
  loginAccount (email, password) {
    return AXIOS.post(`/accounts/login`, {
      'email': email,
      'accountPwd': password
    })
  },
  getAccountInfo (token) {
    return AXIOS.get('/accounts/me', token)
  },
  createPost (data, token) {
    return AXIOS.post(`/posts`, data, token)
  },
  modifyPost (postNo, data, token) {
    return AXIOS.put(`/posts/` + postNo, data, token)
  },
  getPostInfo (postNo, token) {
    return AXIOS.get(`/posts/` + postNo, token)
  },
  getAccount (token) {
    return AXIOS.get('/accounts/secured/getAccount', token)
  }
}
