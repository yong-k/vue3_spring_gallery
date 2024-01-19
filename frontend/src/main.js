import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import { createRouter, createWebHistory } from 'vue-router'

import App from './App.vue'
//import router from './router'
import Home from '@/pages/Home.vue'
import Login from '@/pages/Login.vue'

const routes = [
    { path: '/', component: Home },
    //--→ '/'경로로 들어올 때는 component를 Home으로 연결하라는 의미
    { path: '/login', component: Login }
]
const router = createRouter({
    history: createWebHistory(),
    routes
})

const app = createApp(App)

app.use(createPinia())
app.use(router)

app.mount('#app')
