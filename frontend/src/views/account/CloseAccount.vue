<template>
  <div>
    <h3 class="mb-4 text-xl text-black font-bold">Close Account</h3>
    <!-- 입력 폼 -->
    <div class="w-full">
      <div class="text-base mb-4">
        <span class="text-red-500 font-bold">Warning!  </span>
        <span class="text-black">Closing your account is irreversible.</span>
      </div>
      <form action="/">
        <!-- 비밀번호 입력 -->
        <InputBox title="Current Password" type="text" name="currentPwd"/>
        <!-- submit 버튼 -->
        <button class="w-full bg-red-500 text-white rounded py-2 mt-3">Delete Account</button>
      </form>
    </div>
  </div>
</template>
<script>
  import InputBox from '@/components/InputBox'
  export default {
    components: {
      'InputBox': InputBox
    },
    data () {
      return {
        info: null
      }
    }
  }
</script>
