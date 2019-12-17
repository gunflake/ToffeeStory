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
  loginAccount (userName, password) {
    return AXIOS.post(`/account/login`, {
      'accountId': userName,
      'accountPwd': password
    })
  }
}
