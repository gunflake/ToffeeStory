<template>
  <div class="mb-4">
    <div class="text-base text-left mb-2">{{ title }}</div>
    <input class="block border border-gray-700 w-full p-2 rounded" :minlength="minlength" :maxlength="maxlength"
           :type="type" :id="id" :class="{ 'border-red-700' : visible, 'bg-gray-200' : disabled }" :value="value" :disabled="disabled"
           @keydown="inputFunction" @keyup="inputFunction" @keypress="inputFunction" @keyup.enter="enterFunction"/>
    <div v-if="visible" class="form-error-inline">{{ errorMsg }}</div>
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
    model: {
      prop: 'value',
      event: 'inputEvent'
    },
    props: {
      type: String,
      title: String,
      id: String,
      minlength: String,
      maxlength: String,
      visible: Boolean,
      errorMsg: String,
      value: String,
      disabled: Boolean
    },
    methods: {
      inputFunction (e) {
        this.$emit('inputEvent', e.target.value)
      },
      enterFunction () {
        this.$emit('enterEvent')
      }
    }
  }
</script>
