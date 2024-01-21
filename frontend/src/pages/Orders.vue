<script setup>
import axios from 'axios';
import { reactive } from 'vue';

const state = reactive({
  orders: []
})

axios.get("/api/orders").then(({ data }) => {
  state.orders = [];
  for (let d of data) {
    if (d.items) 
      d.items = JSON.parse(d.items)
    state.orders.push(d)
  }
})
</script>

<template>
  <div class="orders">
    <div class="container">
      <table class="table table-bordered">
        <thead>
          <tr>
            <th>번호</th>
            <th>주문자명</th>
            <th>주소</th>
            <th>결제 수단</th>
            <th>구입 항목</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(order, idx1) in state.orders" :key="idx1">
            <td>{{ state.orders.length - idx1 }}</td>
            <td>{{ order.name }}</td>
            <td>{{ order.address }}</td>
            <td>{{ order.payment }}</td>
            <td>
              <div v-for="(item, idx2) in order.items" :key="idx2">
                {{ item.name }}
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<style scoped>
.table {
  margin-top: 30px;
}

.table > tbody {
  border-top: 1px solid #eee;
}
</style>
