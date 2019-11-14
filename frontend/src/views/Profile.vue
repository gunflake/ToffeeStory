<template>
  <div>
    <div id="profile" class="flex justify-center bg-white p-6 pb-20">
      <img class="h-40 w-40 rounded-full mr-10" src="https://randomuser.me/api/portraits/women/21.jpg">
      <div class="text-left max-w-md">
        <div class="flex items-center pb-3">
          <div class="pr-4">
            <h2 class="font-bold text-3xl">CoffeeLover</h2>
          </div>
          <div class="pr-4">
            <router-link to="/accountSettings">
              <button class="text-sm bg-transparent text-gray-700 font-medium hover:text-black py-1 px-4 border border-gray-400 hover:border-black rounded">Edit</button>
            </router-link>
          </div>
        </div>
        <div class="flex items-center pb-3">
          <div id="instagram" class="pr-4 text-sm text-gray-700">
            <a href="#">coffee_lover</a>
          </div>
          <div id="twitter" class="pr-4 text-sm text-gray-700">
            <a href="#">coffee_lover</a>
          </div>
        </div>
        <div class="flex items-center pb-3">
          <div id="bio" class="pr-4 text-sm text-black">
            혼자라고 느껴지던 낮과 쓸쓸하게 무너졌던 밤 그런 순간마다 날 일으켜 준 건 날 불러주던 너의 목소리
            움츠러든 마음도 굳어버린 마음도 겨울이 지나면 늘 봄이 오듯이 난 나아갈 수 있어
          </div>
        </div>
      </div>
    </div>
    <nav class="bg-white px-8 pt-2 shadow-md">
      <div class="-mb-px flex justify-left">
        <a class="no-underline text-teal-dark border-b-2 border-teal-dark uppercase tracking-wide font-bold text-xs py-3 mr-8" href="#">
          My Menu
        </a>
        <a class="no-underline text-grey-dark border-b-2 border-transparent uppercase tracking-wide font-bold text-xs py-3 mr-8" href="#">
          Likes
        </a>
        <a class="no-underline text-grey-dark border-b-2 border-transparent uppercase tracking-wide font-bold text-xs py-3 mr-8" href="#">
          Bookmarks
        </a>
      </div>
    </nav>
  </div>
</template>
