import Vue from 'vue'
import Vuex from 'vuex'
import api from '@/backend-api'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    loginSuccess: false,
    loginError: false,
    userName: null,
    userToken: null
  },
  getters: {
    isLoggedIn: state => state.loginSuccess,
    hasLoginErrored: state => state.loginError,
    getUserName: state => state.userName,
    getUserToken: state => state.userToken
  },
  mutations: {
    login_success (state, payload) {
      state.loginSuccess = true
      state.userName = payload.userName
      state.userToken = payload.userToken
    },
    login_error (state, payload) {
      state.loginError = true
      state.userName = payload.userName
    }
  },
  actions: {
    loginProcess ({ commit }, { user, password }) {
      return new Promise((resolve, reject) => {
        console.log("Accessing backend with user: '" + user)
        api.loginAccount(user, password)
          .then(response => {
            console.log("Response: '" + response.data + "' with Statuscode " + response.status)
            let userObj = response.data

            if (response.status === 200) {
              console.log('Login successful')
              commit('login_success', {
                userName: userObj.username,
                userToken: userObj.token
              })
            }
            resolve(response)
          })
          .catch(error => {
            console.log('Error: ' + error)
            // place the loginError state into our vuex store
            commit('login_error', {
              userName: user
            })
            // eslint-disable-next-line prefer-promise-reject-errors
            reject('Invalid credentials!')
          })
      })
    }
  }
})
