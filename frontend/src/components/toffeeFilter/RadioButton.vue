<template>
  <label @click="clickFunction" class="bg-transparent hover:bg-gray-500 text-black text-sm hover:text-white py-1 px-4 border border-black-500 hover:border-transparent rounded inline-flex items-center mr-3">
    <input type="radio" class="hidden" :name="name" :value="quantityCode"> {{ quantityName }}
  </label>
</template>
<script>
  export default {
    name: 'RadioButton',
    model: {
      event: 'clickEvent'
    },
    props: {
      quantityCode: Number,
      quantityName: String,
      name: String
    },
    methods: {
      clickFunction () {
        this.$emit('clickEvent')
      }
    }
  }
</script>
