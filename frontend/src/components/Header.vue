<script setup>
import router from '@/scripts/router';
import { useAccountStore } from '@/scripts/store.js'
import axios from 'axios';

const accountStore = useAccountStore()

// https://pinia.vuejs.kr/core-concepts/state.html
// state에 접근: 기본적으로 store 인스턴스로 상태에 접근하여 직접 읽고 쓸 수 있다.
//const testId = accountStore.account.id

const logout = () => {
  // 쿠키가 http-only cookie이기 때문에 클라이언트에서는 지울 수 없다.
  // url 태워서 backend에서 지워줘야 한다.
  axios.post("/api/account/logout").then(() => {
    const accountStore = useAccountStore()
    accountStore.setAccount(0)
    router.push({path: "/"})
  })
}
</script>

<template>
  <header data-bs-theme="dark">
    <div class="collapse text-bg-dark" id="navbarHeader">
      <div class="container">
        <div class="row">
          <div class="col-sm-4 py-4">
            <h4>사이트맵</h4>
            <ul class="list-unstyled">
              <li>
                <router-link to="/" class="text-white">메인 화면</router-link>
              </li>
              <li v-if="accountStore.account.id">
                <router-link to="/orders" class="text-white">주문 내역</router-link>
              </li>
              <li>
                <router-link to="/login" class="text-white" v-if="!accountStore.account.id">로그인</router-link>
                <!-- <router-link to="/" class="text-white" @click.native="logout()" v-else>로그아웃</router-link> -->
                <a class="text-white" @click="logout()" v-else>로그아웃</a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
    <div class="navbar navbar-dark bg-dark shadow-sm">
      <div class="container">
        <router-link to="/" class="navbar-brand d-flex align-items-center">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" aria-hidden="true" class="me-2" viewBox="0 0 24 24"><path d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z"/><circle cx="12" cy="13" r="4"/></svg>
          <strong>Gallery</strong>
        </router-link>
        <router-link to="/cart" class="cart btn" v-if="accountStore.account.id">
          <i class="fa fa-shopping-cart" aria-hidden="true"></i>
        </router-link>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarHeader" aria-controls="navbarHeader" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
      </div>
    </div>
  </header>
</template>

<style scoped>
header .navbar .cart {
  margin-left: auto;
  color: #fff;
}

a.text-white {
  cursor: pointer;
}
</style>
