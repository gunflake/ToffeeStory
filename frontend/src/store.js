import Vue from 'vue'
import Vuex from 'vuex'
import api from '@/backend-api'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    loginSuccess: false,
    loginError: false,
    userName: null,
    userEmail: null
  },
  getters: {
    isLoggedIn: state => state.loginSuccess,
    hasLoginErrored: state => state.loginError,
    getUserName: state => state.userName,
    getUserEmail: state => state.userEmail
  },
  mutations: {
    login_success (state, payload) {
      state.loginSuccess = true
      state.userName = payload.userName
      state.userEmail = payload.userEmail
    },
    login_error (state, payload) {
      state.loginError = true
      state.userEmail = payload.userEmail
    },
    logout (state) {
      state.loginSuccess = false
      state.userName = null
      state.userEmail = null
    }
  },
  actions: {
    loginProcess ({ commit, dispatch }, { email, password }) {
      return new Promise((resolve, reject) => {
        console.log("Accessing backend with userEmail: '" + email)
        api.loginAccount(email, password)
          .then(response => {
            console.log("Response: '" + response.data + "' with Statuscode " + response.status)

            if (response.status === 200) {
              console.log('Login successful')
              localStorage.setItem('token', response.data)

              dispatch('getMemberInfo')
            }
            resolve(response)
          })
          .catch(error => {
            console.log('Error: ' + error)

            commit('login_error', {
              userEmail: email
            })

            // eslint-disable-next-line prefer-promise-reject-errors
            reject('Invalid credentials!')
          })
      })
    },
    getMemberInfo ({ commit }) {
      let token = localStorage.getItem('token')

      if (token == null) { return }

      let config = {
        headers: {
          'Authorization': 'Bearer ' + token
        }
      }

      console.log(config)

      api.getAccountInfo(config).then(response => {
        if (response.status === 200) {
          let userObj = response.data

          commit('login_success', {
            userName: userObj.username,
            userEmail: userObj.email
          })
        } else {
          localStorage.clear()
        }
      })
      .catch(error => {
        console.log('Error: ' + error)
        localStorage.clear()
      })
    },
    logoutProcess ({ commit }) {
      commit('logout')
      localStorage.clear()
    },
    getMemberToken () {
      let config = {
        headers: {
          'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
      }
      return config
    }
  }
})
