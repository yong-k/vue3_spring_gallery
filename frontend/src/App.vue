<script setup>
import Header from './components/Header.vue';
import Footer from './components/Footer.vue';
import { useAccountStore } from './scripts/store';
import axios from 'axios';
import { useRoute } from 'vue-router';
import { watch } from 'vue';

const accountStore = useAccountStore()

const check = () => {
  axios.get("/api/account/check").then(({ data }) => {
    console.log("data: " + data)

    // if (data)
    //   accountStore.setAccount(data)
    // else
    //   accountStore.setAccount(0)
    // 
    // 이거 한 줄로 줄이면, 
    //        ↓↓↓↓ 
    // 
    accountStore.setAccount(data || 0)
  })
}

// 위에서 jwt 사용해서 로그인 처리했으므로,
// 기존에 sessionStorage로 했더니거 지움
/*
const id = sessionStorage.getItem("id")
if (id) 
  accountStore.setAccount(id)
*/

// 현재 브라우저의 url 관련 정보를 가져온다.
const route = useRoute();

// 위 경로가 바뀔 때마다 감시해주는 watch()
//--→ 여기서는 경로가 바뀔 때마다 감시해서 check() 실행한다.
watch(route, () => {
  check()
})
//--→ f12 > Network > request 뜬거에서 check 클릭해서 Preview 칸 보면 된다.
//    그냥 메인에서 실행했을 때는 check 비어있다.
//    로그인한다음에 check 확인해보면, response으로 받은 id 값 확인할 수 있다.
</script>

<template>
  <Header />
  <router-view></router-view>
  <Footer />
</template>

<style scoped>
.bd-placeholder-img {
  font-size: 1.125rem;
  text-anchor: middle;
  -webkit-user-select: none;
  -moz-user-select: none;
  user-select: none;
}

@media (min-width: 768px) {
  .bd-placeholder-img-lg {
    font-size: 3.5rem;
  }
}

.b-example-divider {
  width: 100%;
  height: 3rem;
  background-color: rgba(0, 0, 0, .1);
  border: solid rgba(0, 0, 0, .15);
  border-width: 1px 0;
  box-shadow: inset 0 .5em 1.5em rgba(0, 0, 0, .1), inset 0 .125em .5em rgba(0, 0, 0, .15);
}

.b-example-vr {
  flex-shrink: 0;
  width: 1.5rem;
  height: 100vh;
}

.bi {
  vertical-align: -.125em;
  fill: currentColor;
}

.nav-scroller {
  position: relative;
  z-index: 2;
  height: 2.75rem;
  overflow-y: hidden;
}

.nav-scroller .nav {
  display: flex;
  flex-wrap: nowrap;
  padding-bottom: 1rem;
  margin-top: -1px;
  overflow-x: auto;
  text-align: center;
  white-space: nowrap;
  -webkit-overflow-scrolling: touch;
}

.btn-bd-primary {
  --bd-violet-bg: #712cf9;
  --bd-violet-rgb: 112.520718, 44.062154, 249.437846;

  --bs-btn-font-weight: 600;
  --bs-btn-color: var(--bs-white);
  --bs-btn-bg: var(--bd-violet-bg);
  --bs-btn-border-color: var(--bd-violet-bg);
  --bs-btn-hover-color: var(--bs-white);
  --bs-btn-hover-bg: #6528e0;
  --bs-btn-hover-border-color: #6528e0;
  --bs-btn-focus-shadow-rgb: var(--bd-violet-rgb);
  --bs-btn-active-color: var(--bs-btn-hover-color);
  --bs-btn-active-bg: #5a23c8;
  --bs-btn-active-border-color: #5a23c8;
}

.bd-mode-toggle {
  z-index: 1500;
}

.bd-mode-toggle .dropdown-menu .active .bi {
  display: block !important;
}
</style>
