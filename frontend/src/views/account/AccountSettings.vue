<template>
  <div class="flex-row md:flex lg:p-20 xl:pr-40">
    <!-- 계정관리 사이드바 -->
    <aside class="sidebar w-full md:w-1/4 bg-white my-2 px-2">
      <ul class="list-reset">
        <h3 class="mb-4 text-xl text-black font-bold">Account settings</h3>
        <li>
          <router-link to="/settings/editProfile">
            <a href="#" class="block p-4 text-base text-black hover:bg-gray-100">Edit Profile</a>
          </router-link>
        </li>
        <li>
          <router-link to="/settings/changePassword">
            <a href="#" class="block p-4 text-base text-black hover:bg-gray-100">Change Password</a>
          </router-link>
        </li>
        <li>
          <router-link to="/settings/closeAccount">
            <a href="#" class="block p-4 text-base text-black hover:bg-gray-100">Close Account</a>
          </router-link>
        </li>
      </ul>
    </aside>
    <!-- 계정관리 메인 -->
    <main class="page w-full md:w-3/4 bg-white my-2 px-2">
      <router-view></router-view>
    </main>
  </div>
</template>
