<template>
  <div class="flex py-20">
    <!-- 계정관리 사이드바 -->
    <aside class="sidebar w-1/4 bg-white w-64 my-2 mx-6">
      <ul class="list-reset mr-10">
        <h3 class="mb-4 text-xl text-black font-bold">Account settings</h3>
        <li>
          <router-link to="/account/editProfile">
            <a href="#" class="block p-4 text-base text-black hover:bg-gray-100">Edit Profile</a>
          </router-link>
        </li>
        <li>
          <router-link to="/account/changePassword">
            <a href="#" class="block p-4 text-base text-black hover:bg-gray-100">Change Password</a>
          </router-link>
        </li>
        <li>
          <router-link to="/account/closeAccount">
            <a href="#" class="block p-4 text-base text-black hover:bg-gray-100">Close Account</a>
          </router-link>
        </li>
      </ul>
    </aside>
    <!-- 계정관리 메인 -->
    <main class="page w-3/4 bg-white my-2 mr-6">
      <router-view></router-view>
    </main>
  </div>
</template>
