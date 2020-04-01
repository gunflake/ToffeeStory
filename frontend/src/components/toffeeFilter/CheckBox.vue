<template>
  <label @click="clickFunction" class="bg-transparent hover:bg-gray-500 text-black text-sm hover:text-white py-1 px-4 border border-black-500 hover:border-transparent rounded inline-flex items-center mr-3" style="cursor: pointer">
    <input type="checkbox" class="hidden" :name="name" :value="value" :checked="isDefault"> {{ title }}
  </label>
</template>
<script>
  export default {
    name: 'CheckBox',
    model: {
      event: 'clickEvent'
    },
    props: {
      value: Number,
      title: String,
      name: String,
      isDefault: Boolean
    },
    methods: {
      clickFunction () {
        this.$emit('clickEvent')
      }
    }
  }
</script>
