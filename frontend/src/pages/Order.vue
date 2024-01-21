<script setup>
import axios from 'axios';
import { computed, reactive } from 'vue';
import getNumberFormatted from '@/scripts/lib'

const state = reactive({
  items: [],
  form: {
    name: "",
    address: "",
    payment: "",
    cardNumber: "",
    items: ""
  }
})

const load = () => {
  axios.get("/api/cart/items").then(({ data }) => {
    console.log(data)
    state.items = data
  })
}

const submit = () => {
  // const args = state.form
  //--→ 이 상태로 진행하면 form은 참조값이기 때문에 원본 form의 값도 바뀐다.
  // 안해도 기능에는 문제 없지만, 연결고리를 끊어주기 위해
  // 참조값의 연결고리 끊는 작업 진행
  const args = JSON.parse(JSON.stringify(state.form))
  args.items = JSON.stringify(state.items)

  axios.post("/api/orders", args).then(() => {
    console.log("success")
  })
}

const computedPrice = computed(() => {
  let result = 0
  for (let item of state.items) {
    result += item.price - item.price * item.discountPer / 100
  }
  return result
})

load()
</script>

<template>
  <div class="order">
    <div class="container">
      <main>
        <div class="py-5 text-center">
          <h2>주문하기</h2>
        <p class="lead">Below is an example form built entirely with Bootstrap’s form controls. Each required form group
          has a validation state that can be triggered by attempting to submit the form without completing it.</p>
      </div>
      <div class="row g-5">
        <div class="col-md-5 col-lg-4 order-md-last">
          <h4 class="d-flex justify-content-between align-items-center mb-3">
            <span class="text-primary">구입 목록</span>
            <span class="badge bg-primary rounded-pill">
              {{ state.items.length }}
            </span>
          </h4>
          <ul class="list-group mb-3">
            <li class="list-group-item d-flex justify-content-between lh-sm" v-for="(item, idx) in state.items" :key="idx">
              <div>
                <h6 class="my-0">{{ item.name }}</h6>
              </div>
              <span class="text-body-secondary">
                {{ getNumberFormatted(item.price - item.price * item.discountPer / 100) }}원
              </span>
            </li>
          </ul>
          <h3 class="text-center total-price">
            {{ getNumberFormatted(computedPrice) }}원
          </h3>
        </div>
        <div class="col-md-7 col-lg-8">
          <h4 class="mb-3">주문자 정보</h4>
          <div class="needs-validation" novalidate="">
            <div class="row g-3">
              <div class="col-12">
                <label for="username" class="form-label">이름</label>
                <input type="text" class="form-control" id="username" v-model="state.form.name">
              </div>
              <div class="col-12">
                <label for="address" class="form-label">주소</label>
                <input type="text" class="form-control" id="address" v-model="state.form.address">
              </div>
            </div>
            <hr class="my-4">
            <h4 class="mb-3">결제 수단</h4>
            <div class="my-3">
              <div class="form-check">
                <input id="card" name="paymentMethod" type="radio" class="form-check-input" value="card" v-model="state.form.payment">
                <label class="form-check-label" for="card">신용카드</label>
              </div>
              <div class="form-check">
                <input id="bank" name="paymentMethod" type="radio" class="form-check-input" value="bank" v-model="state.form.payment">
                <label class="form-check-label" for="bank">무통장입금</label>
              </div>
            </div>
            <label for="cc-number" class="form-label">카드 번호</label>
            <input type="text" class="form-control" id="cc-number" v-model="state.form.cardNumber">
            <hr class="my-4">
            <button class="w-100 btn btn-primary btn-lg" @click="submit()">결제하기</button>
          </div>
        </div>
      </div>
    </main>
  </div>
</div></template>

<style scoped>
</style>
