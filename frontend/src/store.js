import Vue from 'vue'
import Vuex from 'vuex'
import api from '@/backend-api'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    loginSuccess: false,
    loginError: false,
    userName: null,
    userEmail: null,
    userToken: null
  },
  getters: {
    isLoggedIn: state => state.loginSuccess,
    hasLoginErrored: state => state.loginError,
    getUserName: state => state.userName,
    getUserEmail: state => state.userEmail,
    getUserToken: state => state.userToken
  },
  mutations: {
    login_success (state, payload) {
      state.loginSuccess = true
      state.userName = payload.userName
      state.userEmail = payload.userEmail
      state.userToken = payload.userToken
    },
    login_error (state, payload) {
      state.loginError = true
      state.userEmail = payload.userEmail
    }
  },
  actions: {
    loginProcess ({ commit }, { email, password }) {
      return new Promise((resolve, reject) => {
        console.log("Accessing backend with userEmail: '" + email)
        api.loginAccount(email, password)
          .then(response => {
            console.log("Response: '" + response.data + "' with Statuscode " + response.status)
            let userObj = response.data

            if (response.status === 200) {
              console.log('Login successful')
              commit('login_success', {
                userName: userObj.username,
                userEmail: userObj.email,
                userToken: userObj.token
              })
            }
            resolve(response)
          })
          .catch(error => {
            console.log('Error: ' + error)
            // place the loginError state into our vuex store
            commit('login_error', {
              userEmail: email
            })
            // eslint-disable-next-line prefer-promise-reject-errors
            reject('Invalid credentials!')
          })
      })
    }
  }
})
