<template>
  <label :id="name + value" class="bg-transparent text-gray-600 text-sm py-1 px-4 border border-gray-400 hover:border-gray-600 rounded inline-flex items-center mr-3 select-none cursor-pointer">
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
    mounted () {
      if (this.checked) {
        document.getElementById(this.name + this.value).classList.remove('bg-transparent', 'border-gray-400', 'text-gray-600')
        document.getElementById(this.name + this.value).classList.add('bg-gray-500', 'border-transparent', 'text-white')
      }
    },
    methods: {
      clickFunction (e) {
        this.$emit('clickEvent', e.target)
      }
    }
  }
</script>
