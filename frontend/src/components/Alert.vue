<template>
    <div v-if="getAlertState">
        <div class="fixed flex alert-show text-center h-16 items-center w-full z-50" v-bind:class="getColor(getAlertType)">
            <span class="mx-auto text-white text-base">{{ getAlertMessage }}</span>
            <span class="absolute top-0 bottom-0 right-0 p-5">
        <svg class="fill-current h-6 w-6 text-white" role="button" xmlns="http://www.w3.org/2000/svg"
             viewBox="0 0 20 20" @click="hideAlert()">
          <title>Close</title>
          <path
                  d="M14.348 14.849a1.2 1.2 0 0 1-1.697 0L10 11.819l-2.651 3.029a1.2 1.2 0 1 1-1.697-1.697l2.758-3.15-2.759-3.152a1.2 1.2 0 1 1 1.697-1.697L10 8.183l2.651-3.031a1.2 1.2 0 1 1 1.697 1.697l-2.758 3.152 2.758 3.15a1.2 1.2 0 0 1 0 1.698z"/>
        </svg>
  </span>
        </div>
    </div>
    <div v-else></div>
</template>

<script>
  import { mapGetters, mapMutations } from 'vuex'

  export default {
    name: 'Alert',
    computed: {
      ...mapGetters(['getAlertState', 'getAlertMessage', 'getAlertType'])
    },
    methods: {
      ...mapMutations(['alertInit']),
      hideAlert () {
        this.alertInit()
      },
      getColor (type) {
        if (type === 'red') {
          return {
            'bg-red-600': true
          }
        } else if (type === 'green') {
          return {
            'bg-green-700': true
          }
        } else if (type === 'gray') {
          return {
            'bg-alert': true
          }
        }
      }
    }
  }
</script>
