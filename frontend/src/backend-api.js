import axios from 'axios'

const AXIOS = axios.create({
  baseURL: `/api`,
  timeout: 1000
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
  getAccount (token) {
    return AXIOS.get('/account/secured/getAccount', token)
  }
}
