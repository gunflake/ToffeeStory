<template>
  <div class="inputBox">
    <div class="text-base pb-2 text-left">
      {{ title }}
      <a v-show="visible" class="no-underline border-b border-blue text-blue-700 font-semibold" style="cursor: pointer" @click="goFindPassword">
        Forgot?
      </a>
    </div>
    <input
      v-bind:type="type"
      class="block border border-gray-700 w-full p-2 rounded mb-4"
      v-bind:id="id"
      v-bind:name="name"
      v-model="value"
      @change="sendValue"
      v-on:keyup.enter="submit"
    />
  </div>
</template>

<script>
  export default {
    name: 'InputBox',
    props: {
      title: String,
      type: String,
      id: String,
      name: String,
      visible: Boolean,
      url: String
    },
    data () {
      return {
        value: ''
      }
    },
    methods: {
      sendValue () {
        this.$emit('sendVal', this.value)
      },
      submit () {
        this.$emit('submit')
      },
      goFindPassword () {
        this.$router.push(this.url)
      }
    }
  }
</script>
