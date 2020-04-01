<template>
  <div class="container">
    <div class="custom-number-input h-10 w-32">
      <div class="flex flex-row h-10 w-full rounded-lg relative bg-transparent mt-1">
        <button @click="decrement" data-action="decrement" class=" bg-gray-300 text-gray-600 hover:text-gray-700 hover:bg-gray-400 h-full w-20 rounded-l cursor-pointer outline-none">
          <span class="m-auto text-2xl font-thin">−</span>
        </button>
        <input :value="defaultValue" @change="checkValue" type="number" class="outline-none focus:outline-none text-center w-full bg-white font-semibold text-md hover:text-black focus:text-black  md:text-basecursor-default flex items-center text-gray-700  outline-none" name="custom-input-number">
        <button @click="increment" data-action="increment" class="bg-gray-300 text-gray-600 hover:text-gray-700 hover:bg-gray-400 h-full w-20 rounded-r cursor-pointer">
          <span class="m-auto text-2xl font-thin">+</span>
        </button>
      </div>
    </div>
  </div>
</template>
<style>
  input[type="number"]::-webkit-inner-spin-button,
  input[type="number"]::-webkit-outer-spin-button {
    -webkit-appearance: none;
    margin: 0;
  }

  .custom-number-input input:focus {
    outline: none !important;
  }

  .custom-number-input button:focus {
    outline: none !important;
  }

  button span {
    line-height: 35px;
  }
</style>
<script>
  export default {
    name: 'NumberCounter',
    props: {
      defaultValue: Number
    },
    methods: {
      decrement (e) {
        const btn = e.target.parentNode.parentElement.querySelector(
          'button[data-action="decrement"]'
        )
        const target = btn.nextElementSibling
        let value = Number(target.value)

        if (value > 0) {
          value--
          target.value = value
        }
      },
      increment (e) {
        const btn = e.target.parentNode.parentElement.querySelector(
          'button[data-action="decrement"]'
        )
        const target = btn.nextElementSibling
        let value = Number(target.value)
        value++
        target.value = value
      },
      checkValue (e) {
        // 0보다 작은 값 입력 시 0으로 초기화
        if (e.target.value < 0) {
          e.target.value = 0
        }
      }
    }
  }
</script>
