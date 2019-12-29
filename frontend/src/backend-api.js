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
  checkAccount (token) {
    return AXIOS.get(`/account/auth`,
      {
        headers: { 'Authorization': 'bearer ' + token }
      })
  }
}
