<template>
  <div class="mb-3">
    <div class="flex items-center">
      <!--  topping name area  -->
      <div class="w-1/3">
        <span class="font-semibold text-sm">
          {{ topping.name }}
        </span>
      </div>
      <!--  subtopping area  -->
      <div v-if="topping.quantityType == 0 && subToppings != null" class="flex items-center">
        <radio-button v-on:clickEvent="selectRadio('subToppingRadio')" v-for="subTopping in subToppings" :key="subTopping.subToppingNo"
                      :value="subTopping.subToppingNo" :title="subTopping.subToppingName" name="subToppingRadio"></radio-button>
      </div>
      <!--  quantity area:Number Counter  -->
      <div v-if="topping.quantityType == 1" class="w-2/3">
        <number-counter></number-counter>
      </div>
      <!--  quantity area"Quantity Code  -->
      <div v-if="topping.quantityType != 1" class="flex items-center">
        <radio-button v-on:clickEvent="selectRadio('quantityRadio')" v-if="topping.quantityType == 4" value="1" title="없이" name="quantityRadio"></radio-button>
        <radio-button v-on:clickEvent="selectRadio('quantityRadio')" v-if="topping.quantityType == 2 || topping.quantityType == 3 || topping.quantityType == 4" value="2" title="적게" name="quantityRadio"></radio-button>
        <radio-button v-on:clickEvent="selectRadio('quantityRadio')" v-if="topping.quantityType == 2 || topping.quantityType == 3 || topping.quantityType == 4" value="3" title="보통" name="quantityRadio"></radio-button>
        <radio-button v-on:clickEvent="selectRadio('quantityRadio')" v-if="topping.quantityType == 3 || topping.quantityType == 4" value="4" title="많이" name="quantityRadio"></radio-button>
        <radio-button v-on:clickEvent="selectRadio('quantityRadio')" v-if="topping.quantityType == 5" value="5" title="클래식 시럽 없이" name="quantityRadio"></radio-button>
      </div>
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
      defaultSubToppingNo: Number,
      defaultValue: Number
    },
    data () {
      return {
        quantityCodes: [],
        subToppings: [ // subTopping ex
          { subToppingNo: 1, toppingNo: 2, subToppingName: '디카페인' },
          { subToppingNo: 2, toppingNo: 2, subToppingName: '1/2디카페인' }
        ]
      }
    },
    mounted () {
      // init default value
    },
    methods: {
      deleteButton () {
        this.$emit('deleteEvent')
      },
      selectRadio (name) {
        let radioButtons = document.querySelectorAll('input[name="' + name + '"]')

        for (let i = 0; i < radioButtons.length; i++) {
          radioButtons[i].parentElement.classList.remove('bg-gray-500', 'border-transparent', 'text-white')
        }

        document.querySelector('input[name="' + name + '"]:checked').parentElement.classList.add('bg-gray-500', 'border-transparent', 'text-white')
      }
    }
  }
</script>
