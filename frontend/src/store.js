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
    userSrc: null,
    alertState: false,
    alertMessage: null,
    alertType: null,
    token: null,
    headerVisible: true,
    autocompleteList: null
  },
  getters: {
    isLoggedIn: state => state.loginSuccess,
    hasLoginErrored: state => state.loginError,
    getUserName: state => state.userName,
    getUserEmail: state => state.userEmail,
    getAlertState: state => state.alertState,
    getAlertMessage: state => state.alertMessage,
    getAlertType: state => state.alertType,
    getToken: state => state.token,
    getUserSrc: state => state.userSrc,
    getHeaderVisible: state => state.headerVisible,
    getAutocompleteList: state => state.autocompleteList
  },
  mutations: {
    login_success (state, payload) {
      state.loginSuccess = true
      state.userName = payload.userName
      state.userEmail = payload.userEmail
      state.userSrc = payload.userSrc
    },
    login_error (state, payload) {
      state.loginError = true
      state.userEmail = payload.userEmail
    },
    logout (state) {
      state.loginSuccess = false
      state.userName = null
      state.userEmail = null
      state.userSrc = null
    },
    alertInit (state) {
      state.alertMessage = null
      state.alertType = null
      state.alertState = false
    },
    alertSetting (state, payload) {
      state.alertMessage = payload.message
      state.alertType = payload.type
      state.alertState = true
    },
    tokenInit (state) {
      state.token = null
    },
    tokenSetting (state, data) {
      state.token = data
    },
    showHeader (state) {
      state.headerVisible = true
    },
    hideHeader (state) {
      state.headerVisible = false
    },
    setAutocompleteList (state, product) {
      state.autocompleteList = product
    }
  },
  actions: {
    loginProcess ({ commit, dispatch }, { email, password }) {
      return new Promise((resolve, reject) => {
        api.loginAccount(email, password)
          .then(response => {
            if (response.status === 200) {
              localStorage.setItem('token', response.data)
              dispatch('getMemberInfo')
            }
            resolve(response)
          })
          .catch(error => {
            let message = error.response.data.message
            if (message === undefined) { message = '서버에 연결할 수 없습니다. 잠시 후에 다시 시도해주세요.' }
            reject(message)
          })
      })
    },
    createProcess ({ commit, dispatch }, { fullName, userName, email, password }) {
      return new Promise((resolve, reject) => {
        api.joinAccount(fullName, userName, email, password)
          .then(response => {
            let message = '회원가입이 되었습니다.'
            resolve(message)
          })
          .catch(error => {
            let message = error.response.data.message
            if (message === undefined) { message = '서버에 연결할 수 없습니다. 잠시 후에 다시 시도해주세요.' }
            reject(message)
          })
      })
    },
    getMemberInfo ({ commit, dispatch }) {
      let token = localStorage.getItem('token')
      let userName = localStorage.getItem('username')
      let userEmail = localStorage.getItem('userEmail')

      if (userName != null && userEmail != null) {
        commit('login_success', {
          userName: userName,
          userEmail: userEmail
        })
      }

      if (token == null) {
        commit('tokenInit')
        commit('logout')
        return
      }

      let config = {
        headers: {
          'Authorization': 'Bearer ' + token
        }
      }

      api.getAccountInfo(config).then(response => {
        if (response.status === 200) {
          let userObj = response.data
          localStorage.setItem('username', userObj.username)
          localStorage.setItem('userEmail', userObj.email)
          localStorage.setItem('userSrc', userObj.src)
          commit('login_success', {
            userName: userObj.username,
            userEmail: userObj.email,
            userSrc: userObj.src
          })
          commit('tokenSetting', config)
        } else {
          commit('tokenInit')
          commit('logout')
          localStorage.clear()
        }
      })
      .catch(() => {
        localStorage.clear()
        let message = '로그인 기간이 만료되었습니다.'
        let data = {}
        data.message = message
        data.type = 'gray'
        dispatch('settingAlertMsg', data)
        commit('tokenInit')
        commit('logout')
      })
    },
    setAutocompleteList ({ commit }) {
      // API 통신해서 Product 리스트 받아오기
      let product = {
        beverage: ['아메리카노', '카페라떼', '바닐라 라떼', '자몽 에이드', '망고 에이드', '블루베리 에이드'],
        topping: ['바닐라 시럽', '초코 시럽', '헤이즐넛 시럽', '자바칩', '스파클링']
      }

      commit('setAutocompleteList', product)
    },
    settingAlertMsg ({ commit }, { message, type }) {
      commit('alertSetting', {
        message: message,
        type: type
      })
      setTimeout(() => {
        commit('alertInit')
      }, 5000)
    },
    logoutProcess ({ commit }) {
      commit('logout')
      localStorage.clear()
    }
  }
})
