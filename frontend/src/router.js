import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const editProfile = () => import('@/views/account/EditProfile.vue')
const changePassword = () => import('@/views/account/ChangePassword.vue')
const closeAccount = () => import('@/views/account/CloseAccount.vue')

const routes = [
  {
    path: '/',
    name: 'home',
    component: () => import('@/views/Home.vue')
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
    path: '/@:username',
    name: 'profile',
    component: () => import('@/views/Profile.vue')
  },
  {
    path: '/settings',
    name: 'settings',
    component: () => import('@/views/account/AccountSettings.vue'),
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
