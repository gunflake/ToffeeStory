import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const account = () => import('@/views/account/AccountSettings.vue')
const editProfile = () => import('@/views/account/EditProfile.vue')
const changePassword = () => import('@/views/account/ChangePassword.vue')
const closeAccount = () => import('@/views/account/CloseAccount.vue')

const routes = [
  {
    path: '/',
    name: 'main',
    component: () => import('@/views/Main.vue')
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/Login.vue')
  },
  {
    path: '/join',
    name: 'join',
    component: () => import('@/views/Join.vue')
  },
  {
    path: '/find/password',
    name: 'findPassword',
    component: () => import('@/views/FindPassword.vue')
  },
  {
    path: '/profile',
    name: 'profile',
    component: () => import('@/views/Profile.vue')
  },
  {
    path: '/account',
    name: 'account',
    component: account,
    children: [
      { path: '', component: editProfile },
      { path: 'editProfile', component: editProfile },
      { path: 'changePassword', component: changePassword },
      { path: 'closeAccount', component: closeAccount }
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
