<template>
  <div>
    <h3 class="mb-4 text-xl text-black font-bold">Change Password</h3>
    <!-- 입력 폼 -->
    <div class="w-full mx-3">
      <form action="/">
        <!-- 비밀번호 입력 -->
        <InputBox title="Current Password" type="text" name="currentPwd"/>
        <InputBox title="New Password" type="text" name="newPwd"/>
        <InputBox title="Password Confirm" type="text" name="pwdConfirm"/>
        <!-- submit 버튼 -->
        <button class="w-full bg-black text-white rounded py-2 mt-3">Change Password</button>
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
