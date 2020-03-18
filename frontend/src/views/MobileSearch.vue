<template>
  <div class="flex w-full items-center mx-3 pt-2">
    <div class="w-5/6 relative">
          <span>
            <input class="transition-colors duration-100 ease-in-out focus:outline-0 border border-transparent focus:bg-white focus:border-gray-300 placeholder-gray-600 rounded-lg bg-search py-2 pr-4 pl-10 block w-full appearance-none leading-normal ds-input"
                   type="text" placeholder="Search desired topping" autocomplete="off"
                   spellcheck="false" role="combobox" aria-autocomplete="list" aria-expanded="false"
                   aria-label="search input"  dir="auto"
                   v-model="searchTag">
          </span>
      <div class="pointer-events-none absolute inset-y-0 left-0 pl-4 flex items-center">
        <svg class="fill-current pointer-events-none text-gray-600 w-4 h-4" xmlns="http://www.w3.org/2000/svg"
             viewBox="0 0 20 20" >
          <path
            d="M12.9 14.32a8 8 0 1 1 1.41-1.41l5.35 5.33-1.42 1.42-5.33-5.34zM8 14A6 6 0 1 0 8 2a6 6 0 0 0 0 12z"></path>
        </svg>
      </div>
    </div>
    <button
      class="w-1/6 bg-transparent text-gray-600 hover:text-black py-2 px-6 border-gray-700 " @click="goHome">
      Cancel
    </button>
  </div>
</template>
<script>
  import { mapMutations } from 'vuex'

  export default {
    name: 'MobileSearch',
    mounted () {
      this.hideHeader()
    },
    methods: {
      ...mapMutations(['hideHeader', 'showHeader']),
      goHome () {
        this.showHeader()
        this.$router.push('/')
      }
    }
  }
</script>

<style scoped>

</style>
