import Home from '@/pages/Home.vue'
import Login from '@/pages/Login.vue'
import Cart from '@/pages/Cart.vue'
import Order from '@/pages/Order.vue'
import Orders from '@/pages/Orders.vue'
import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    { path: '/', component: Home },
    //--→ '/'경로로 들어올 때는 component를 Home으로 연결하라는 의미
    { path: '/login', component: Login },
    { path: '/cart', component: Cart },
    { path: '/order', component: Order },
    { path: '/orders', component: Orders }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router