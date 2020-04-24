<template>
  <div class="inline-flex mr-3 p-2 bg-gray-300 items-center text-gray-900 text-sm leading-none rounded-full select-none">
    <a class="mx-2 text-left flex-auto">{{ productName }}</a>
    <a class="mx-2 text-left flex-auto" role="button" @click="deleteButton">X</a>
  </div>
</template>
<script>
  export default {
    name: 'ButtonWithX',
    props: {
      productNo: Number,
      productName: String
    },
    methods: {
      deleteButton () {
        this.$emit('deleteEvent')
      }
    }
  }
</script>
