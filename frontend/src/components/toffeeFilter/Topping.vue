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
        <radio-button v-for="subTopping in subToppings" :key="subTopping.subToppingNo" v-on:clickEvent="selectRadio('subToppingRadio')" name="subToppingRadio"
                      :title="subTopping.subToppingName" :value="subTopping.subToppingNo" :isDefault="subTopping.subToppingNo === productDefault.subToppingNo ? true : false"></radio-button>
      </div>
      <!--  quantity area : Number Counter  -->
      <div v-else-if="topping.quantityType == 1" class="w-2/3">
        <number-counter :defaultValue="productDefault.value"></number-counter>
      </div>
      <!--  quantity area : Quantity Code  -->
      <div v-else class="flex items-center">
        <div v-for="quantityCode in quantityCodes" :key="quantityCode.quantityCode">
          <radio-button v-if="quantityCode.quantityType.indexOf(topping.quantityType) != -1" v-on:clickEvent="selectRadio('quantityRadio')" name="quantityRadio"
                        :title="quantityCode.quantityName" :value="quantityCode.quantityCode" :checked="quantityCode.quantityCode === productDefault.quantityCode ? true : false"></radio-button>
        </div>
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
      productDefault: []
    },
    data () {
      return {
        quantityCodes: [
          { quantityCode: 1, seqNo: 1, quantityName: '없이', quantityType: '4' },
          { quantityCode: 2, seqNo: 2, quantityName: '적게', quantityType: '2,3,4' },
          { quantityCode: 3, seqNo: 3, quantityName: '보통', quantityType: '2,3,4' },
          { quantityCode: 4, seqNo: 4, quantityName: '많이', quantityType: '3,4' },
          { quantityCode: 5, seqNo: 5, quantityName: '클래식 시럽 없이', quantityType: '5' }
        ],
        subToppings: [ // subTopping ex
          { subToppingNo: 1, toppingNo: 2, subToppingName: '디카페인' },
          { subToppingNo: 2, toppingNo: 2, subToppingName: '1/2디카페인' }
        ]
      }
    },
    mounted () {
      // init default value
      console.log(this.productDefault)
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
