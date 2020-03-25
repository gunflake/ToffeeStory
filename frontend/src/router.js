import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const myMenu = () => import('@/views/profile/MyMenu.vue')
const likes = () => import('@/views/profile/Likes.vue')
const bookmarks = () => import('@/views/profile/Bookmarks.vue')

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
    component: () => import('@/views/Profile.vue'),
    children: [
      { path: '', component: myMenu },
      { path: 'myMenu', component: myMenu },
      { path: 'likes', component: likes },
      { path: 'bookmarks', component: bookmarks }
    ]
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
  },
  {
    path: '/mobile-search',
    name: 'mobileSearch',
    component: () => import('@/views/MobileSearch.vue')
  },
  {
    path: '/search',
    name: 'searchList',
    component: () => import('@/views/SearchList.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
