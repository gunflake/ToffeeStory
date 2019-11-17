<template>
  <div>
    <h3 class="mb-4 text-xl text-black font-bold">Edit Profile</h3>
    <!-- 입력 폼 -->
    <div class="w-full">
      <form action="/">
        <div class="flex">
          <!-- 프로필 이미지 -->
          <div class="w-1/3 flex justify-center">
            <div class="block">
              <img class="h-40 w-40 rounded-full mb-4" src="https://randomuser.me/api/portraits/women/21.jpg">
              <a>Change Profile Image</a>
            </div>
          </div>
          <!-- 회원 정보 -->
          <div class="w-2/3 mx-3">
            <InputBox title="Full Name" type="text" name="fullName"/>
            <InputBox title="Email" type="email" name="email"/>
            <InputBox title="Username" type="text" name="userName"/>
          </div>
        </div>
        <div class="flex">
          <InputBox class="w-1/2 mx-3" title="  Instagram username" type="text" name="instagramUserName"/>
          <InputBox class="w-1/2 mx-3" title="Twitter username" type="text" name="twitterUserName"/>
        </div>
        <div class="block mx-3">
          <div class="text-base pb-2">
            Bio
          </div>
          <textarea class="w-full h-24 resize-none border border-gray-700 rounded"></textarea>
          <!-- submit 버튼 -->
          <button class="w-full bg-black text-white rounded py-2 mt-3">Update Account</button>
        </div>
      </form>
    </div>
  </div>
</template>
<script>
  import InputBox from '@/components/InputBox'
  export default {
    name: 'editProfile',
    components: {
      InputBox
    },
    data () {
      return {
        info: null
      }
    }
  }
</script>
