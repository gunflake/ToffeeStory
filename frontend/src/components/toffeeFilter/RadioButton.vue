<template>
  <label oncontextmenu="return false" ondragstart="return false" onselectstart="return false" class="bg-transparent text-gray-600 text-sm py-1 px-4 border border-gray-400 hover:border-gray-600 rounded inline-flex items-center mr-3 select-none cursor-pointer">
    <input type="radio" class="hidden" :name="name" :value="value" :checked="checked" @click="clickFunction"> {{ title }}
  </label>
</template>
<script>
  export default {
    name: 'RadioButton',
    model: {
      event: 'clickEvent'
    },
    props: {
      value: Number,
      title: String,
      name: String,
      checked: Boolean
    },
    data: function () {
      return {
        isChecked: Boolean
      }
    },
    methods: {
      clickFunction (e) {
        this.$emit('clickEvent', e.target)
      }
    }
  }
</script>
