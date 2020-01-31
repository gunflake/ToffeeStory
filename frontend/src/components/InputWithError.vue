<template>
  <div class="mb-4">
    <div class="text-base text-left mb-2">{{ title }}</div>
    <input
      v-bind:type="type"
      v-bind:id="id"
      v-model="value"
      @keydown="eventFunction"
      @keyup="eventFunction"
      @keypress="eventFunction"
      v-bind:class="{ 'border-red-700' : visible }"
      class="block border border-gray-700 w-full p-2 rounded"/>
    <div v-show="visible" class="form-error-inline">{{ errorMsg }}</div>
  </div>
</template>
<style>
  .form-error-inline {
    margin-top: 6px;
    padding: 3px 9px;
    font-size: 12px;
    border-radius: 4px;
    background-color: #f9ded8;
  }
  input:focus {
    outline: none;
  }
</style>
<script>
  export default {
    name: 'InputWithError',
    props: {
      type: String,
      title: String,
      id: String,
      visible: Boolean,
      errorMsg: String
    },
    data () {
      return {
        value: ''
      }
    },
    methods: {
      eventFunction () {
        this.$emit('inputVal', this.value)
      }
    }
  }
</script>
