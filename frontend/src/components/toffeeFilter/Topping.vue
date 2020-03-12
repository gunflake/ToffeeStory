<template>
  <div class="mb-3">
    <!--  topping name area  -->
    <div class="mb-3 flex items-center">
      <div class="w-1/3">
        <span class="font-semibold text-sm">
          {{ toppingName }}
        </span>
      </div>
      <!--  subtopping area  -->
      <div v-if="subToppings != null" class="mb-3 flex items-center">
      </div>
      <!--  quantity area:Number Counter  -->
      <div v-if="quantityType == 1" class="w-2/3">
        <number-counter></number-counter>
      </div>
    </div>
    <!--  quantity area"Quantity Code  -->
    <div v-if="quantityType != 1" class="mb-3 flex items-center">
      <radio-button v-on:clickEvent="selectQauntity" v-if="quantityType == 4" quantityCode="1" quantityName="없이" name="quantityRadio"></radio-button>
      <radio-button v-on:clickEvent="selectQauntity" v-if="quantityType == 2 || quantityType == 3 || quantityType == 4" quantityCode="2" quantityName="적게" name="quantityRadio"></radio-button>
      <radio-button v-on:clickEvent="selectQauntity" v-if="quantityType == 2 || quantityType == 3 || quantityType == 4" quantityCode="3" quantityName="보통" name="quantityRadio"></radio-button>
      <radio-button v-on:clickEvent="selectQauntity" v-if="quantityType == 3 || quantityType == 4" quantityCode="4" quantityName="많이" name="quantityRadio"></radio-button>
      <radio-button v-on:clickEvent="selectQauntity" v-if="quantityType == 5" quantityCode="5" quantityName="클래식 시럽 없이" name="quantityRadio"></radio-button>
    </div>
  </div>
</template>
<script>
  import NumberCounter from '@/components/toffeeFilter/NumberCounter'
  import RadioButton from '@/components/toffeeFilter/RadioButton'

  export default {
    name: 'Topping',
    components: { NumberCounter, RadioButton },
    props: {
      topping: [],
      toppingNo: Number,
      toppingName: String,
      toppingPrice: Number,
      quantityType: Number,
      subToppings: [],
      defaultSubToppingNo: Number,
      defaultValue: Number
    },
    data () {
      return {
        quantityCodes: []
      }
    },
    mounted () {
      // quantityCodes 조회 함수
    },
    methods: {
      deleteButton () {
        this.$emit('deleteEvent')
      },
      selectQauntity () {
        let radioButtons = document.querySelectorAll('input[name="quantityRadio"]')
        for (let i = 0; i < radioButtons.length; i++) {
          radioButtons[i].parentElement.classList.remove('bg-gray-500', 'border-transparent', 'text-white')
        }
        document.querySelector('input[name="quantityRadio"]:checked').parentElement.classList.add('bg-gray-500', 'border-transparent', 'text-white')
      }
    }
  }
</script>
